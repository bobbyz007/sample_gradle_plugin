package com.example.gradle.collection;

import org.gradle.api.provider.Property;

abstract public class ServerEnvironment {
    private final String name;

    @javax.inject.Inject
    public ServerEnvironment(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    // 没有setter，抽象getter对应的属性，称为“managed properties”，对于这类属性gradle会自动生成对应的实现
    // 可变属性：Property， 只读：Provider，且不用定义为抽象
    abstract public Property<String> getUrl();
}