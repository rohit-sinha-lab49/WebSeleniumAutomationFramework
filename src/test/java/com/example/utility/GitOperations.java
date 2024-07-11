package com.example.utility;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.SshTransport;
import org.eclipse.jgit.transport.SshSessionFactory;
import org.eclipse.jgit.transport.ssh.jsch.JschConfigSessionFactory;
import org.eclipse.jgit.transport.ssh.jsch.OpenSshConfig;
import com.jcraft.jsch.Session;

import java.io.File;
import java.io.IOException;

public class GitOperations {

    public static void main(String[] args) {
        String localRepoPath = "C:\\Projects\\SeleniumManagerProject";
        String remoteRepoUri = "git@github.com:rohit-sinha-lab49/WebSeleniumAutomationFramework.git";
        String branchName = "featureOne";
        String commitMessage = "UtilFiles folder created";

        try {
            // Setup SSH session factory
            SshSessionFactory.setInstance(new JschConfigSessionFactory () {
                @Override
                protected void configure(OpenSshConfig.Host host, Session session) {
                    // Configure SSH session if necessary
                }
            });

            // Open local repository
            File localRepo = new File(localRepoPath);
            Git git = Git.open(localRepo);

            // Checkout to the branch
            git.checkout().setName(branchName).call();

            // Add changes to staging area
            git.add().addFilepattern(".").call();

            // Commit changes
            git.commit().setMessage(commitMessage).call();

            // Pull latest changes from master
            git.pull()
                    .setTransportConfigCallback(transport -> {
                        if (transport instanceof SshTransport) {
                            SshTransport sshTransport = (SshTransport) transport;
                            sshTransport.setSshSessionFactory(SshSessionFactory.getInstance());
                        }
                    })
                    .setRemote("origin")
                    .setRemoteBranchName("featureOne")
                    .call();

            // Push local changes to master
            git.push()
                    .setTransportConfigCallback(transport -> {
                        if (transport instanceof SshTransport) {
                            SshTransport sshTransport = (SshTransport) transport;
                            sshTransport.setSshSessionFactory(SshSessionFactory.getInstance());
                        }
                    })
                    .call();

            System.out.println("Push successful.");

        } catch (IOException | GitAPIException e) {
            e.printStackTrace();
        }
    }
}

