/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.domain;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author Leyeseyi
 */
@Entity
public class Todo extends AuditModel implements Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    
  
    @Column(nullable=false)
    private String item;
    
    private int completed;
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date datecreated;
    
    //contructors
    @ManyToOne
    @JoinColumn(name="user_id",nullable=false)
    private User user;

    public Todo() {
    }
    
    public Todo(String item, int completed, User user) {
        this.item = item;
        this.completed = completed;
        this.user = user;
    }

    public Todo(int id, String item, int completed, User user) {
        this.id = id;
        this.item = item;
        this.completed = completed;
        this.user = user;
    }
   
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getCompleted() {
        return completed;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Todo{" + "id=" + id + ", item=" + item + ", completed=" + completed + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.item);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Todo other = (Todo) obj;
        if (!Objects.equals(this.item, other.item)) {
            return false;
        }
        return true;
    }
    
    
}
