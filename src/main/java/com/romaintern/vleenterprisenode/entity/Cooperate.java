package com.romaintern.vleenterprisenode.entity;

import lombok.Getter;
import lombok.Setter;
import org.neo4j.ogm.annotation.*;

/**
 * @author: Liu Qingle
 * @create: 2020-07-05 20:52
 **/
@RelationshipEntity(type = "cooperate")
public class Cooperate {
    @StartNode @Getter
    Enterprise formerEnterprise;

    @EndNode @Getter
    Enterprise laterEnterprise;

    @Getter @Setter
    Long supplyChainId;
}
