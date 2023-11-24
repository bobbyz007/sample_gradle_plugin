package com.example.gradle.collection;

import org.gradle.api.NamedDomainObjectContainer;

public interface DownloadExtension {
    NamedDomainObjectContainer<Resource> getResources();
}
