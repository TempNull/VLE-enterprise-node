package com.romaintern.vleenterprisenode.service;

import com.romaintern.vleenterprisenode.dao.EnterpriseRepository;
import com.romaintern.vleenterprisenode.entity.Enterprise;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.net.InetAddress;

/**
 * @author: Liu Qingle
 * @create: 2020-07-04 15:19
 * @function : register in ecosystem
 **/
@Component
@Slf4j
public class RegisterService implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(RegisterService.class);

    @Autowired
    private EnterpriseService enterpriseService;
    @Autowired
    private EnterpriseRepository enterpriseRepository;


    @Autowired
    private Enterprise enterprise;

    @Autowired
    Environment environment;

    @Override
    public void run(String... args) throws Exception {
//        enterpriseService.deleteAll();
        InetAddress inetAddress = InetAddress.getLocalHost();
        String url = inetAddress.getHostAddress() + ":" + environment.getProperty("local.server.port");
        enterprise.setRestRootUrl(url);
        System.out.println(enterprise.toString());
        enterpriseRepository.deleteAllRelationship();
        enterpriseService.deleteOlderVersionByUrl(url);
        enterpriseService.save(enterprise);
        logger.info("register this enterprise successfully");

        enterpriseRepository.createRelationship(url,"5");
        enterpriseRepository.setRelationshipOrderId(url,"5","123");
        enterpriseRepository.deleteRelationshipWithOrderId(url,"5","123");


//        Set<Enterprise> es = enterpriseRepository.findEnterprisesByRoleEqualsOrderByCapability("ha");
//        es.stream().forEach(e-> System.out.println(e.toString()));
    }
}
