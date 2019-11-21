/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Leyeseyi
 */
public interface TodoRepository extends JpaRepository<Todo, Integer>{
    
    List<Todo> findByUserId(Integer id);
    
    List<Todo> findByUserIdAndCompleted(Integer user, Integer id);
    
     
    
    
//    List<Todo> findByIte(String item);
}
