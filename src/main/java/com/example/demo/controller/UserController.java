/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.controller;

import com.example.demo.domain.Role;
import com.example.demo.domain.RoleRepository;
import com.example.demo.domain.User;
import com.example.demo.domain.UserRepository;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;



/**
 *
 * @author Leyeseyi
 */
@Controller
public class UserController {
    
    //Auto wire password encoder
    @Autowired   
    private BCryptPasswordEncoder bcrypt;
            
    @Autowired   
    private RoleRepository roleRepo;
    
    @Autowired  
    private UserRepository userRepo;
    
    public static String uploadDirectory = "src\\main\\resources\\static\\images";
    
    @RequestMapping(value="/register", method=RequestMethod.GET)
    public String registerPage(Model model){
        
        //add user
        
        User user = new User();
        
        //add object
        model.addAttribute("user", user);
        return "register";
        
    }
    
    @RequestMapping(value="/registerUser", method=RequestMethod.POST)
    public String addUser(@ModelAttribute(name="user") @Valid User user, BindingResult errors, Model model, @RequestParam("profileimage") MultipartFile files, @RequestParam("cpassword") String cpassword, @RequestParam("dateOfBirth") String dateOfBirth){
        System.out.println("here");
      //  System.out.println(userRepo.findByEmail("olaleyeseyivictor@gmail.com"));
//        User checkEmail = userRepo.findByEmail(user.getEmail());
//        System.out.println(checkEmail);
//        if(user.getEmail().contentEquals((CharSequence) checkEmail)){
//            model.addAttribute("mailerror", true);
//            return "register";
//        } 
//        
        if(user.getPassword().equals(cpassword)){
            
            //validate user object
            System.out.println("hello");
            if(errors.hasErrors()){
                System.out.println(errors.getAllErrors().toString());
                return "register";
            }
            
        }else{
            System.out.println(errors.getAllErrors().toString());
            System.out.println("hello");
            model.addAttribute("passwordMismatch", true);
            return "register";
        }
        
        System.out.println("no errors here");
        
        //populate user object
        Optional<Role> role = roleRepo.findById(1);
        //System.out.println(role.get());
        user.setPassword(bcrypt.encode(user.getPassword()));
        user.setProfileImage(files.getOriginalFilename());
        user.setRole(roleRepo.getOne(1));
        user.setDateOfBirth(dateOfBirth);
        
        System.out.println(user); 
        System.out.println("hello when printing");
      
        
            //upload image
            Path fileNameAndPath = Paths.get(uploadDirectory, files.getOriginalFilename());
            
            try{
                Files.write(fileNameAndPath, files.getBytes());

            }
            
            catch(IOException ex){
                System.out.println("Error " +ex.getMessage());
            }

        //save file
        System.out.println("hello before save");
        userRepo.save(user);
        System.out.println("hello after save");
        return "redirect:/login";
        
    }
    
}
