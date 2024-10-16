package com.basic;

import com.fasterxml.jackson.annotation.JsonProperty;
import in.vectorpro.dropwizard.swagger.SwaggerBundleConfiguration;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.*;


public class AppConfig extends Configuration {

    @Valid
    @NotNull
    private DataSourceFactory database = new DataSourceFactory();

    @JsonProperty("database")
    public DataSourceFactory getDataSourceFactory() {
        return database;
    }

    @JsonProperty("database")
    public void setDataSourceFactory(DataSourceFactory factory) {
        this.database = factory;
    }


    @Getter
    @Setter
    @JsonProperty("swagger")
    private SwaggerBundleConfiguration swagger = new SwaggerBundleConfiguration();
}