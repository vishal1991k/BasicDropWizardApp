package com.basic.dao;

import com.basic.model.StoredDemoRow;
import org.hibernate.SessionFactory;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DemoDao extends BaseDAO<StoredDemoRow> {
    @Inject
    protected DemoDao(final SessionFactory sessionFactory) {
        super(StoredDemoRow.class, sessionFactory);
    }
}
