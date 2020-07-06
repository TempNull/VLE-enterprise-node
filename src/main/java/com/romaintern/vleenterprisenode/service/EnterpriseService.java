package com.romaintern.vleenterprisenode.service;

import com.romaintern.vleenterprisenode.dao.EnterpriseRepository;
import com.romaintern.vleenterprisenode.entity.Enterprise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author: Liu Qingle
 * @create: 2020-07-02 22:48
 **/
@Service
public class EnterpriseService {
    @Autowired
    private EnterpriseRepository enterpriseRepository;

    public void deleteAll(){
        enterpriseRepository.deleteAll();
    }

    public void save(Enterprise enterprise){
       enterpriseRepository.save(enterprise);
    }

    public Set<Enterprise> getFormerActors(String formerRole) {
        return enterpriseRepository.findEnterprisesByRoleEqualsOrderByCapability(formerRole);
    }

    public Set<Enterprise> getLaterActors(String laterRole) {
        return enterpriseRepository.findEnterprisesByRoleEqualsOrderByCapability(laterRole);
    }

    public Set<Enterprise> getLaterCooperators(String myUrl){
        return enterpriseRepository.getLaterCooperators(myUrl);
    }

    public Set<Enterprise> getFormerCooperators(String myUrl){
        return enterpriseRepository.getFormerCooperators(myUrl);
    }

    public void saveAll(Set<Enterprise> enterprises){
        enterpriseRepository.saveAll(enterprises);
    }

    public Long deleteOlderVersionByUrl(String url){
       return enterpriseRepository.deleteEnterpriseByRestRootUrlEquals(url);
    }

    public Enterprise setRelationshipOrderId(String myUrl,String itsUrl,String orderId){
        return enterpriseRepository.setRelationshipOrderId(myUrl,itsUrl,orderId);
    }

}
