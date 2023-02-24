package com.mycompany.mytasklist.tasklist;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author marcin
 */
@Entity
@Table( name = "TASK_LIST" )
public class Task {
    @Id
    @GeneratedValue(generator="inc")
    @GenericGenerator(name="inc", strategy = "increment")
    private Integer id;
    private String name;
    private Boolean done = false;
    
    @SuppressWarnings("unused")
    Task() {
    }
    
    public Task(Integer id, String name, Boolean done) {
        this.id = id;
        this.name = name;
        this.done = done;
    }
    
    public Integer getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public Boolean getDone() {
        return done;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setDone(Boolean done) {
        this.done = done;
    }
}
