package com.romaintern.vleenterprisenode.dao;

import com.romaintern.vleenterprisenode.entity.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * @author: Liu Qingle
 * @create: 2020-07-02 15:26
 **/
public interface PersonRepository extends CrudRepository<Person, Long> {

    @Transactional
    Set<Person> findALLByNameEquals(String name);

    @Transactional
    Long deletePersonByNameContains(String name);

}