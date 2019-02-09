/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.volpini.vgi.domain;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@MappedSuperclass
public abstract class AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "long_id", updatable = false, nullable = false)
    protected Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_UPDATE_TIMESTAMP", nullable = false)
    private Date lastUpdateTimestamp;

    @Override
    public String toString() {
        return String.valueOf(id);
    }

    @PreUpdate
    @PrePersist
    protected void onPreUpdate() {
        lastUpdateTimestamp = new Date();
    }

    public Long getId() {
        return id;
    }
    
    public void setId (Long id) {
    	this.id=id;
    }

    public Date getLastUpdateTimestamp() {
        return lastUpdateTimestamp;
    }
    
   
}
