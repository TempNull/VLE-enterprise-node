package com.romaintern.vleenterprisenode.entity;

/**
 * @author: Liu Qingle
 * @create: 2020-07-02 15:21
 **/


import java.util.HashSet;
import java.util.Set;

import lombok.*;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
@NoArgsConstructor
@ToString
public class Person {

    @Id @GeneratedValue @Getter private Long id;

    @Getter
    private String name;

    @Getter
    private String information;


    /**
     * Neo4j doesn't REALLY have bi-directional relationships. It just means when querying
     * to ignore the direction of the relationship.
     * https://dzone.com/articles/modelling-data-neo4j
     */
    @Relationship(type = "TEAMMATE", direction = Relationship.UNDIRECTED)
    public Set<Person> teammates;

    public Person(String name) {
        this.name = name;
    }

    public void worksWith(Person person) {
        if (teammates == null) {
            teammates = new HashSet<>();
        }
        teammates.add(person);
    }

}