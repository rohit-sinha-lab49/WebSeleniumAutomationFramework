package com.example.utility;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;

import java.io.IOException;
import java.util.List;

public class DockerComposeStatusChecker {

    public static boolean isDockerComposeServiceUp (String serviceName) throws IOException {
        // Replace with the correct Docker host URL, especially if running on a specific platform
        String dockerHost = System.getenv("DOCKER_HOST");
        System.out.println("dockerhost : "+dockerHost);
        if (dockerHost == null || dockerHost.isEmpty()) {
            // Fallback to default
            //dockerHost = "unix:///var/run/docker.sock";
            // Or use TCP if that's your setup
             dockerHost = "tcp://localhost:2375";
        }
        DefaultDockerClientConfig.Builder configBuilder = DefaultDockerClientConfig.createDefaultConfigBuilder ()
                .withDockerHost (dockerHost);

        DockerClient dockerClient = DockerClientBuilder.getInstance (configBuilder.build ()).build ();

        try {
            List<Container> containers = dockerClient.listContainersCmd ().withShowAll (true).exec ();
            for (Container container : containers) {
                InspectContainerResponse containerResponse = dockerClient.inspectContainerCmd (container.getId ()).exec ();
                String containerName = containerResponse.getName ();
                System.out.println("Container name ---> "+containerName);
                if (containerName != null && containerName.contains (serviceName)) {
                    String status = container.getStatus ();
                    if (status != null && (status.contains ("Up") || status.contains ("running"))) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace ();
        } finally {
            dockerClient.close ();
        }

        return false;
    }

    public static void main (String[] args) throws IOException {
        String serviceName = "selenium-hub"; // Replace with your Docker Compose service name
        boolean isServiceUp = isDockerComposeServiceUp (serviceName);
        if (isServiceUp) {
            System.out.println ("The service " + serviceName + " is up and running.");
        } else {
            System.out.println ("The service " + serviceName + " is not running.");
        }
    }

}
