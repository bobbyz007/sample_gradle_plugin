package com.example.gradle.service;

import org.gradle.api.DefaultTask;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.services.ServiceReference;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;

import java.net.URI;

public abstract class BuildServiceDepTask extends DefaultTask {
    // This property provides access to the service instance
    @ServiceReference("web")
    abstract Property<WebServer> getServer();

    @TaskAction
    public void download() {
        // Use the server to download a file
        WebServer server = getServer().get();
        URI uri = server.getUri().resolve("somefile.zip");
        System.out.println(String.format("[%d]Downloading %s", server.callTimes(), uri));
    }
}
