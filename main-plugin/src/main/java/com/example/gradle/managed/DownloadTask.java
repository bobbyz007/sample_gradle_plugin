package com.example.gradle.managed;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.Nested;
import org.gradle.api.tasks.TaskAction;

public abstract class DownloadTask extends DefaultTask {
    // 实现嵌套访问要求必须是public或protected， abstract以及添加Nested注解
    @Nested
    public abstract Resource getResource(); // Use an abstract getter method annotated with @Nested

    @TaskAction
    void run() {
        // Use the `resource` property
        System.out.println("Downloading https://" + getResource().getHostName().get() + "/" + getResource().getPath().get());
    }
}

