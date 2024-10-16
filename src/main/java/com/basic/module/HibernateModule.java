package com.basic.module;

import com.basic.AppConfig;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import io.dropwizard.hibernate.HibernateBundle;
import org.hibernate.SessionFactory;

public class HibernateModule extends AbstractModule {

    private final HibernateBundle<AppConfig> hibernateBundle;

    public HibernateModule(HibernateBundle<AppConfig> hibernateBundle) {
        this.hibernateBundle = hibernateBundle;
    }

    @Override
    protected void configure() {
        // Additional bindings can be configured here if needed
    }

    @Provides
    @Singleton
    public SessionFactory provideSessionFactory() {
        return hibernateBundle.getSessionFactory();
    }
}