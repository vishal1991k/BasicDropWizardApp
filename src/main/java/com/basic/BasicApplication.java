package com.basic;

import com.basic.exception.AppExceptionMapper;
import com.basic.model.StoredDemoRow;
import com.basic.module.BasicAppModule;
import com.basic.module.HibernateModule;
import com.basic.resource.DemoResource;
import com.google.inject.Guice;
import com.google.inject.Injector;
import in.vectorpro.dropwizard.swagger.SwaggerBundle;
import in.vectorpro.dropwizard.swagger.SwaggerBundleConfiguration;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class BasicApplication extends Application<AppConfig> {

    private Injector injector;

    private final HibernateBundle<AppConfig> hibernateBundle =
            new HibernateBundle<AppConfig>(StoredDemoRow.class) {
                @Override
                public DataSourceFactory getDataSourceFactory(AppConfig configuration) {
                    return configuration.getDataSourceFactory();
                }
            };

    public static void main(String[] args) throws Exception {
        new BasicApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<AppConfig> bootstrap) {
        bootstrap.addBundle(hibernateBundle);
        bootstrap.addBundle(new SwaggerBundle<AppConfig>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(AppConfig configuration) {
                return configuration.getSwagger();
            }
        });
    }

    @Override
    public void run(AppConfig configuration, Environment environment) {
        injector = Guice.createInjector(new BasicAppModule(),
                new HibernateModule(hibernateBundle));

        //Add Resource classes here
        final DemoResource demoResource =  injector.getInstance(DemoResource.class);
        environment.jersey().register(demoResource);
        environment.jersey().register(injector.getInstance(AppExceptionMapper.class));
    }
}