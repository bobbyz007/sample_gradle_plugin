package com.example.gradle.collection;

import com.example.gradle.DataProcessing;
import com.example.gradle.ServerEnvironment;
import org.gradle.api.NamedDomainObjectContainer;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.Property;
import org.gradle.wrapper.Download;

import java.net.URI;

public class SubPlugin implements Plugin<Project> {
    @Override
    public void apply(final Project project) {
        ObjectFactory objects = project.getObjects();

        /** NamedDomainObjectContainer<Resource> resourceContainer =
                objects.domainObjectContainer(Resource.class, name -> objects.newInstance(Resource.class, name)); */
        // 暴露extension，客户端可配置
        DownloadExtension extension = project.getExtensions().create("download", DownloadExtension.class);


        // 此处all方法指定的action只是注册，会延迟到解析完构建脚本再执行
        extension.getResources().all(resource -> {
            String name = resource.getName();
            Property<URI> uri = resource.getUri();
            Property<String> userName = resource.getUserName();

            String capitalizedName = name.substring(0, 1).toUpperCase() + name.substring(1);
            String taskName = "dlTo" + capitalizedName;
            project.getTasks().register(taskName, task -> {
                task.doLast(t -> {
                    System.out.println(name + ": from " + uri.get() + " by " + userName.get());
                });
            });
        });
    }
}
