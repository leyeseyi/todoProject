/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.domain;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Leyeseyi
 */
public interface UserRepository extends JpaRepository<User, Integer> {
    
    /*
    * To implement search querries for seaching for emails
    */
   
    User findByEmail(String email);
}
