package com.example.utility;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.SshTransport;
import org.eclipse.jgit.transport.SshSessionFactory;
import org.eclipse.jgit.transport.ssh.jsch.JschConfigSessionFactory;
import org.eclipse.jgit.transport.ssh.jsch.OpenSshConfig;
import com.jcraft.jsch.Session;
import org.eclipse.jgit.util.FS;

import java.io.File;
import java.io.IOException;

public class GitOperations {

    public static void main(String[] args) {

        SshSessionFactory sshSessionFactory = new JschConfigSessionFactory() {
            @Override
            protected void configure(OpenSshConfig.Host hc, Session session) {
                session.setConfig("StrictHostKeyChecking", "no");
            }

            @Override
            protected JSch getJSch(OpenSshConfig.Host hc, FS fs) throws JSchException {
                JSch jsch = super.getJSch(hc, fs);
                jsch.addIdentity(System.getProperty("user.home") + "/.ssh/id_rsa");
                return jsch;
            }
        };

        try (Git git = Git.open(new File("C:\\Projects\\SeleniumManagerProject"))) {
            git.push()
                    .setTransportConfigCallback(transport -> {
                        SshTransport sshTransport = (SshTransport) transport;
                        sshTransport.setSshSessionFactory(sshSessionFactory);
                    })
                    .call();
        } catch (IOException | GitAPIException e) {
            e.printStackTrace();
        }

        String localRepoPath = "C:\\Projects\\SeleniumManagerProject";
        String remoteRepoUri = "git@github.com:rohit-sinha-lab49/WebSeleniumAutomationFramework.git";
        String branchName = "featureOne";
        String commitMessage = "UtilFiles folder created";

        try {
            // Setup SSH session factory
            /*SshSessionFactory.setInstance(new JschConfigSessionFactory () {
                @Override
                protected void configure(OpenSshConfig.Host host, Session session) {
                    // Configure SSH session if necessary
                    session.setConfig("StrictHostKeyChecking", "no");
                }


            });*/

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

