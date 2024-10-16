package com.basic.module;

import com.basic.resource.DemoResource;
import com.google.inject.AbstractModule;

public class BasicAppModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(DemoResource.class).asEagerSingleton();
    }
}