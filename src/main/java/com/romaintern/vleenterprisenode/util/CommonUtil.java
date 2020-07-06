package com.romaintern.vleenterprisenode.util;

import com.romaintern.vleenterprisenode.dao.EnterpriseRepository;
import com.romaintern.vleenterprisenode.entity.Enterprise;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * @author: Liu Qingle
 * @create: 2020-07-04 22:36
 **/
public class CommonUtil {

    @Autowired
    private EnterpriseRepository enterpriseRepository;

    public Enterprise findActorsAndSetOrderId(String direction, long needs, Set<Enterprise> actors, Enterprise enterprise){
        Iterator iterator = actors.iterator();
        // try to find the least suppliers
        while (iterator.hasNext() && needs > 0){
            Enterprise e = (Enterprise) iterator.next();
            if(e.getCapability() >= needs){
                e.setCapability(e.getCapability() - needs);
                needs = 0;
                if (direction.equals("forward")) {
                    e.addCooperator(enterprise);
                } else {
                    enterprise.addCooperator(e);
                }

            }else if(e.getCapability() < needs){
                needs -= e.getCapability();
                e.setCapability(0);
                if (direction.equals("forward")) {
                    e.addCooperator(enterprise);
                } else {
                    enterprise.addCooperator(e);
                }
            }
        }
        return enterprise;
    }
        public void addRelationshipOrderId(String direction,Enterprise paramEnterprise, Set<Enterprise> set,String orderId){
            set.stream().forEach(e->{
                if (direction.equals("forward")){
                    System.out.println(paramEnterprise.getRestRootUrl());
                    System.out.println(e.getRestRootUrl() + "  " +orderId);
                    enterpriseRepository.setRelationshipOrderId(e.getRestRootUrl(),paramEnterprise.getRestRootUrl(),orderId);
                }else{
                    enterpriseRepository.setRelationshipOrderId(paramEnterprise.getRestRootUrl(),e.getRestRootUrl(),orderId);
                }
            });
        }

        public boolean sumCapabilityIsEnough(Set<Enterprise> enterprises,long needs){
            Iterator<Enterprise> iterator = enterprises.iterator();
            while (iterator.hasNext()){
                needs -= iterator.next().getCapability();
            }
            return needs<=0;
        }
}
