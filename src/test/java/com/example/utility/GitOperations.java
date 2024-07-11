package com.example.utility;

import com.jcraft.jsch.JSchException;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.transport.SshSessionFactory;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.eclipse.jgit.transport.ssh.jsch.JschConfigSessionFactory;
import org.eclipse.jgit.transport.ssh.jsch.OpenSshConfig;
import org.eclipse.jgit.util.FS;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GitOperations {
    public static void main(String[] args) {
        // Set up the custom SSH session factory
        SshSessionFactory.setInstance(new JschConfigSessionFactory () {
            @Override
            protected void configure(OpenSshConfig.Host host, Session session) {
                // Use the default configuration
            }

            @Override
            protected JSch createDefaultJSch(FS fs) throws JSchException {
                JSch jsch = super.createDefaultJSch(fs);
                jsch.addIdentity(System.getProperty("user.home") + "/.ssh/id_rsa");
                return jsch;
            }
        });

        try {
            // Clone the repository (if not already cloned)
            // Git.cloneRepository()
            // .setURI("git@github.com:username/repository.git")
            // .setDirectory(new File("/path/to/repo"))
            // .call();

            File repoDir = new File("C:/Projects/SeleniumManagerProject");

            File lockFile = new File(repoDir, ".git/index.lock");
            if (lockFile.exists()) {
                Files.delete(Paths.get(lockFile.getAbsolutePath()));
                System.out.println("Deleted existing index.lock file.");
            }

            // Open the existing repository
            Git git = Git.open(repoDir);

            // Add all files to the staging area
            git.add().addFilepattern(".").call();

            // Commit the changes
            git.commit().setMessage("Your commit message").call();

            // Push the changes to the remote repository
            git.push().call();

        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
            e.printStackTrace();
        } catch (GitAPIException e) {
            System.err.println("GitAPIException: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
