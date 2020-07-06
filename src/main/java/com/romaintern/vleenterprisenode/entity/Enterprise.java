package com.romaintern.vleenterprisenode.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * @author: Liu Qingle
 * @create: 2020-07-04 14:59
 **/
@NodeEntity
@Component
@NoArgsConstructor
@ToString
@PropertySource(value = "classpath:enterprise.properties")
public class Enterprise {

    @Id @GeneratedValue @Getter private Long id;

    @Getter @Setter @Value("${enterprise.name}")
    private String name;

    @Setter @Getter @Value("${enterprise.capability}")
    private long capability;

    @Getter @Setter @Value("${enterprise.description}")
    private String description;

    @Getter @Setter
    private String restRootUrl;

    @Getter @Setter @Value("${enterprise.role}")
    private String role;

    @Setter @Getter @Value("${enterprise.formerRole}")
    private String formerRole;

    @Setter @Getter @Value("${enterprise.laterRole}")
    private String laterRole;

    @Relationship(type = "cooperate",direction = Relationship.UNDIRECTED)
    @Getter @ToString.Exclude
    Set<Enterprise> cooperators;


    public void addCooperator(Enterprise enterprise) {
         if (cooperators == null) {
             cooperators = new HashSet<>();
         }
        cooperators.add(enterprise);

    }

}
