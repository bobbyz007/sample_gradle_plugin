package com.example.gradle.managed;

import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;

// managed type defined as interface
public interface Resource {
    @Input
    Property<String> getHostName();

    @Input
    Property<String> getPath();
}
