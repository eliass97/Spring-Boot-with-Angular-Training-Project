package com.example.demo.model;


import com.example.demo.DemoApplication;
import com.example.demo.exceptions.BadRequestException;
import com.example.demo.exceptions.ConflictException;
import com.example.demo.exceptions.DemoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Version;
import java.sql.Timestamp;

@Entity
public class DemoObject {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemoApplication.class);

    @Version
    @Column(name = "last_update_date")
    private Timestamp lastUpdateDate;

    public DemoObject() {

    }

    public DemoObject(Timestamp lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public void setLastUpdateDate(Timestamp lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Timestamp getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void check(DemoObject other) throws DemoException {
        if (lastUpdateDate == null || other.getLastUpdateDate() == null) {
            LOGGER.error("DemoObject -> versionChecks -> BadRequestException");
            throw new BadRequestException("LastUpdateDate was not provided in the request body");
        }
        if (!lastUpdateDate.equals(other.getLastUpdateDate())) {
            LOGGER.error("DemoObject -> versionChecks -> ConflictException for {}", other);
            throw new ConflictException("Different country versions during update");
        }
    }
}