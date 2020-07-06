package com.romaintern.vleenterprisenode.service;

import com.romaintern.vleenterprisenode.dao.PersonRepository;
import com.romaintern.vleenterprisenode.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: Liu Qingle
 * @create: 2020-07-02 22:48
 **/
@Service
public class PersonService {
    @Autowired
    private PersonRepository personRepository;

    public Person insertPerson(Person person){
        return personRepository.save(person);
    }

    public Long deletePersonByNameContains(String name){
        return personRepository.deletePersonByNameContains(name);
    }

    public void deleteAll(){
        personRepository.deleteAll();
    }
}
