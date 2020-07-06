package com.romaintern.vleenterprisenode.controller;

import com.romaintern.vleenterprisenode.dao.EnterpriseRepository;
import com.romaintern.vleenterprisenode.entity.Person;
import com.romaintern.vleenterprisenode.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: Liu Qingle
 * @create: 2020-07-02 13:56
 **/
@RestController
public class Index {
    @Autowired
    private EnterpriseRepository enterpriseRepository;








    @Autowired
    private PersonService personService;

    @GetMapping(value = "/index")
    public String receiveMessage(){
        return "index";
    }

    @PostMapping(value = "/add")
    public Person addPerson(@RequestBody Person person){
        return personService.insertPerson(person);
    }

    @GetMapping(value = "/delete")
    public Long deletePersonByNameContains(String name){
        return personService.deletePersonByNameContains(name);
    }
    @GetMapping(value = "/deleteAll")
    public void deleteAll(){
        personService.deleteAll();
        return;
    }

}
