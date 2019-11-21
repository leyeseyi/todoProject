/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.controller;

import com.example.demo.domain.Todo;
import com.example.demo.domain.User;
import com.example.demo.domain.UserRepository;
import java.security.Principal;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.example.demo.domain.TodoRepository;
import java.util.List;

/**
 *
 * @author Leyeseyi
 */
@Controller
public class TodosController {
    
    @Autowired
    private TodoRepository todosRepo ;
    
    @Autowired
    private UserRepository userRepo ;
    
    @RequestMapping(value="/todo", method=RequestMethod.GET)
    public String getTodospage(Principal principal, Model model){
        
        //principal helps to get details of the current user    
        //getting user profile
        User user = userRepo.findByEmail(principal.getName());
       
        //counting completed todos
        int completedCount = todosRepo.findByUserIdAndCompleted(user.getId(),1).size();
                
        System.out.println(user.getId());

        System.out.println(todosRepo.findByUserId(user.getId()).size());
        int count = todosRepo.findByUserId(user.getId()).size() ;
        
        //counting uncompleted todos
        int uncompletedCount = count - completedCount;
        Todo entryList = new Todo();
        
        model.addAttribute("entryList", entryList);
        model.addAttribute("profileImage", user);
        model.addAttribute("count", count);
        model.addAttribute("completedCount", completedCount);
        model.addAttribute("uncompletedCount", uncompletedCount);
        
        List userTodos = todosRepo.findByUserId(user.getId());
        System.out.println(userTodos);
        
        if(userTodos.isEmpty())
            model.addAttribute("emptyList", true);
        else{
            model.addAttribute("listNotEmpty", true);
            model.addAttribute("list", todosRepo.findByUserId(user.getId()));
        }
        
        return "todos";
    }
    
    
    @RequestMapping(value="/create", method=RequestMethod.POST) //OR
    //@PostMapping(value="/create")
    public String createTodoRecord(@ModelAttribute(name="entryList") @Valid Todo entryList, BindingResult bindingResult, Model model, Principal principal){
        
        
        //check successful save
        boolean check = false;
        //assert
        System.out.println("Todo: " +entryList);
        
        //get the current user
        User user = userRepo.findByEmail(principal.getName());
        
        entryList.setUser(user);
        
        
        //check if form has errors
        if(bindingResult.hasErrors())
            return "todos";
        
        //submit to db
        try{
            todosRepo.save(entryList);
            check = true;
            
            if(check){
                model.addAttribute("check", check);
                model.addAttribute("list", todosRepo.findAll());
                model.addAttribute("ProfileImage", user);
            }
            return "redirect:/todo";
        }
        catch(Exception e){
            return "todos";
        }
        //return "todos";
    }
    
    @RequestMapping(value="/delete{id}", method=RequestMethod.GET)
    public String deleteEntry (@PathVariable(name="id") Integer id, Model model){
        
       //delete record
       
       todosRepo.deleteById(id);
      
       //retrieve list;
       model.addAttribute("list", todosRepo.findAll()); 
       
       return "redirect:/todo"; //display todos page
        
    }
    
    @RequestMapping(value="/edit{id}", method=RequestMethod.GET)
    public String editRecord (@PathVariable(name="id") Integer id, Model model){
        //declare varaible message
        String message ="Update Todo Record ";
        
        //retrieve record
        Optional<Todo> tod = todosRepo.findById(id);
        
        //check for emptiness
        if(tod.isPresent()){
            
            model.addAttribute("entryList", tod.get());
            message += tod.get().getId();
            
        
        }
        
        model.addAttribute("check", "false");         //add attribute to check
        
        model.addAttribute("message", message);
        
        
       return "edit_todos"; //display edit page
        
    }
    
    @RequestMapping(value="/update", method=RequestMethod.POST)
    public String updateRecord (@ModelAttribute(name="entryList") Todo entryList, BindingResult bindingResult, Model model){
        
        //this also performs the update.
           System.out.println("entryList: " + entryList);
           
           boolean check;
           
           if(bindingResult.hasErrors())
            return "todo";
        
        //submit to db
        try{
            todosRepo.save(entryList);
            check = true;
            
            if(check)
                model.addAttribute("check", check);
        }
        catch(Exception e){
            return "todo";
        }
        return "redirect:/todos";
    }
    
    @RequestMapping(value="/completed{id}", method=RequestMethod.GET)
    public String completed (@PathVariable(name="id") Integer id, Model model){
        
        //retrieve record
        Optional<Todo> tod = todosRepo.findById(id);
        
        //check for emptiness
        if(tod.isPresent()){
            if(tod.get().getCompleted()==0){
                tod.get().setCompleted(1);
                long completed_count = todosRepo.count();
                model.addAttribute("completed_count", completed_count);
            }
            else{ 
                tod.get().setCompleted(0);
            }
                      
        
        }
        
        todosRepo.save(tod.get());
        
        
        
        
       return "redirect:/todo"; //display todos page
        
    }
    
    @RequestMapping(value="/login", method=RequestMethod.GET)
    public String login(){
        
        return "login";
    }
    
    @RequestMapping(value="/logout", method=RequestMethod.GET)
    public String logout(){
        
        return "login";
    }
}
