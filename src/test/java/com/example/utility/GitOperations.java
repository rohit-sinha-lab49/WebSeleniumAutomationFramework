package com.example.utility;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;
import java.io.IOException;

public class GitOperations {

    public static void main(String[] args) {
        String localRepoPath = "C:\\Projects\\SeleniumManagerProject";
        String remoteRepoUri = "https://github.com/rohit-sinha-lab49/WebSeleniumAutomationFramework.git";
        String branchName = "featureOne";
        String username = "rohit-sinha-lab49";
        String password = "Sensex$#12345";

        try {
            // Open local repository
            File localRepo = new File(localRepoPath);
            Git git = Git.open(localRepo);

            // Checkout to the branch
            git.checkout().setName(branchName).call();

            // Add changes to staging area
            git.add().addFilepattern(".").call();

            // Commit changes
            git.commit().setMessage("Your commit message").call();

            // Pull latest changes from current_branch
            PullResult pullResult = git.pull()
                    .setRemote("origin")
                    .setRemoteBranchName("featureOne")
                    .setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, password))
                    .call();

            if (pullResult.isSuccessful()) {
                System.out.println("Pull successful.");
            } else {
                System.out.println("Pull failed: " + pullResult.toString());
                return;
            }

            // Push local changes to master
            git.push()
                    .setRemote("origin")
                    .setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, password))
                    .call();

            System.out.println("Push successful.");

        } catch (IOException | GitAPIException e) {
            e.printStackTrace();
        }
    }
}

