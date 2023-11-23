package com.example.gradle;

import org.gradle.api.NamedDomainObjectContainer;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.model.ObjectFactory;

public class MainPlugin implements Plugin<Project> {
    @Override
    public void apply(final Project project) {
        ObjectFactory objects = project.getObjects();

        NamedDomainObjectContainer<ServerEnvironment> serverEnvironmentContainer =
                objects.domainObjectContainer(ServerEnvironment.class, name -> objects.newInstance(ServerEnvironment.class, name));
        project.getExtensions().add("environments", serverEnvironmentContainer);

        // 此处all方法指定的action只是注册，会延迟到解析完构建脚本再执行
        serverEnvironmentContainer.all(serverEnvironment -> {
            String env = serverEnvironment.getName();
            String capitalizedServerEnv = env.substring(0, 1).toUpperCase() + env.substring(1);
            String taskName = "deployTo" + capitalizedServerEnv;
            // project.getTasks().register(taskName, Deploy.class, task -> task.getUrl().set(serverEnvironment.getUrl()));
            project.getTasks().register(taskName, task -> {
                task.doLast(t -> {
                    System.out.println(serverEnvironment.getName() + ": " + serverEnvironment.getUrl().get());
                });
            });
        });
    }
}
