package com.basic.service;

import com.basic.dao.DemoDao;
import com.basic.model.StoredDemoRow;
import com.google.inject.Inject;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@AllArgsConstructor(onConstructor = @__(@Inject))
@Slf4j
public class DemoService {
    private final DemoDao demoDao;

    public StoredDemoRow getRow(final String id) {
        StoredDemoRow row = null;
        try {
            row = demoDao.get("id",id);
        } catch (Exception e){
            log.error("Caught issue while getting row {}", id);
        }
        return row;
    }

    public boolean saveRow(final StoredDemoRow row) {
        try {
            return Objects.nonNull(demoDao.save(row));
        } catch (Exception e){
            log.error("Caught issue while saving row {}", row.getId() , e);
        }
        return false;
    }
}
