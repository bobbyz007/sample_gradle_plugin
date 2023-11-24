package com.example.gradle.collection;

import org.gradle.api.provider.Property;

import java.net.URI;

public interface Resource {
    // Type must have a read-only 'name' property
    String getName();

    Property<URI> getUri();

    Property<String> getUserName();
}