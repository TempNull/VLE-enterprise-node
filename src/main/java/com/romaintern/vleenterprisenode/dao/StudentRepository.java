package com.romaintern.vleenterprisenode.dao;

import com.romaintern.vleenterprisenode.entity.Person;
import org.springframework.data.neo4j.repository.Neo4jRepository;

/**
 * @author: Liu Qingle
 * @create: 2020-07-02 15:26
 **/
public interface StudentRepository extends Neo4jRepository<Person, Long> {

    Person findByName(String name);

}