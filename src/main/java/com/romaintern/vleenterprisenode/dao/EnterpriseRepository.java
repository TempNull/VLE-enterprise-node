package com.romaintern.vleenterprisenode.dao;

import com.romaintern.vleenterprisenode.entity.Enterprise;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

public interface EnterpriseRepository extends Neo4jRepository<Enterprise, Long> {

    @Transactional
    Set<Enterprise> findAllByRoleEqualsOrderByCapability(@Param("role") String role);

    @Transactional
    @Query("match (e:Enterprise{restRootUrl: $myUrl})-[c:cooperate]->(s:Enterprise{restRootUrl: $itsUrl}) set c.orderId= $orderId return e")
    Enterprise setRelationshipOrderId(@Param("myUrl")String myUrl,
                                      @Param("itsUrl") String itsUrl,
                                      @Param("orderId") String orderId);

    @Transactional
    @Query("match (e:Enterprise{restRootUrl: $myUrl})-[c:cooperate{orderId: $orderId}]->(s:Enterprise{restRootUrl: $itsUrl})  detach delete c")
    Enterprise deleteRelationshipWithOrderId(@Param("myUrl")String myUrl,
                                             @Param("itsUrl") String itsUrl,
                                             @Param("orderId") String orderId);

    @Transactional
    @Query("match (e:Enterprise)-[c:cooperate]->(s:Enterprise)  detach delete c")
    Enterprise deleteAllRelationship();

    @Transactional
    @Query("match (e:Enterprise{restRootUrl: $myUrl}),(s:Enterprise{restRootUrl: $itsUrl}) create (e)-[c:cooperate]->(s)")
    void createRelationship(@Param("myUrl")String myUrl,
                            @Param("itsUrl") String itsUrl);



    @Transactional
    Long deleteEnterpriseByRestRootUrlEquals(String url);

    @Transactional
    @Query("match (e:Enterprise) where e.role=$role return e order by e.capability desc" )
    Set<Enterprise> findEnterprisesByRoleEqualsOrderByCapability(@Param("role") String role);



}
