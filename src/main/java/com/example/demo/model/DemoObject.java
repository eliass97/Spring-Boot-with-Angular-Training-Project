package com.example.demo.model;

import com.example.demo.exceptions.BadRequestException;
import com.example.demo.exceptions.ConflictException;
import com.example.demo.exceptions.DemoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.sql.Timestamp;

@MappedSuperclass
public abstract class DemoObject {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemoObject.class);

    @Version
    @Column(name = "last_update_date")
    private Timestamp lastUpdateDate;

    public void setLastUpdateDate(Timestamp lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Timestamp getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void check(Timestamp otherLastUpdateDate) throws DemoException {
        if (otherLastUpdateDate == null) {
            LOGGER.error("DemoObject -> versionChecks -> BadRequestException");
            throw new BadRequestException("LastUpdateDate was not provided in the request body");
        }
        if (!lastUpdateDate.equals(otherLastUpdateDate)) {
            LOGGER.error("DemoObject -> versionChecks -> ConflictException");
            throw new ConflictException("Different country versions during update");
        }
    }
}