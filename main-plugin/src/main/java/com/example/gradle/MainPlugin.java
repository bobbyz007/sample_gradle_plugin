package com.example.gradle;

import com.example.gradle.collection.ServerEnvironment;
import com.example.gradle.dependency.DataProcessing;
import org.gradle.api.NamedDomainObjectContainer;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
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

        // 提供一个配置：改写插件提供的默认依赖
        Configuration dataFiles = project.getConfigurations().create("dataFiles", c -> {
            c.setVisible(false);
            c.setCanBeConsumed(false);
            c.setCanBeResolved(true);
            c.setDescription("The data artifacts to be processed for this plugin.");
            c.defaultDependencies(d -> d.add(project.getDependencies().create("com.taobao.arthas:arthas-common:3.7.0")));
        });

        project.getTasks().withType(DataProcessing.class).configureEach(
                dataProcessing -> dataProcessing.getDataFiles().from(dataFiles));
    }
}
