package com.romaintern.vleenterprisenode.controller;

import com.romaintern.vleenterprisenode.dao.EnterpriseRepository;
import com.romaintern.vleenterprisenode.entity.Enterprise;
import com.romaintern.vleenterprisenode.service.EnterpriseService;
import com.romaintern.vleenterprisenode.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author: Liu Qingle
 * @create: 2020-07-04 19:18
 **/
@RestController
@RequestMapping("/rest/VLE")
public class VLEController {

    @Autowired
    Enterprise enterprise;

    @Autowired
    private EnterpriseService enterpriseService;

    @Autowired
    private EnterpriseRepository enterpriseRepository;

    @Resource
    private RestTemplate restTemplate;

    @GetMapping(value = "")
    public String test() {
        return "getRest";
    }

    /**
     * @Param: direction:[forward,back,bilateral]
     */
    @GetMapping(value = "buildConnection")
    public String buildConnection(@RequestParam String direction, @RequestParam long needs, @RequestParam String orderId) {
        StringBuffer sb = new StringBuffer();

        Map<String,Object> urlVariables = new HashMap<>();
        urlVariables.put("needs",needs);
        urlVariables.put("orderId",orderId);

        CommonUtil commonUtil = new CommonUtil();
        long needsTmp = needs;
        //start to build former connection
        boolean gotFormer = false, gotLater = false;
        if (direction.equals("forward") || direction.equals("bilateral")) {
            if (enterprise.getFormerRole().equals("/") && direction.equals("forward")) {
                return  "success "+ direction;
            }
            Set<Enterprise> formerActors = enterpriseService.getFormerActors(enterprise.getFormerRole());
            gotFormer = commonUtil.sumCapabilityIsEnough(formerActors, needs);
            if (gotFormer) {
//                enterprise = findMethods.findActorsAndSetOrderId("forward",needs,formerActors,enterprise);
                Iterator iterator1 = formerActors.iterator();
                // try to find the least suppliers
                while (iterator1.hasNext() && needsTmp > 0) {
                    Enterprise e = (Enterprise) iterator1.next();
                    if (e.getCapability() >= needsTmp) {
                        e.setCapability(e.getCapability() - needsTmp);
                        needsTmp = 0;
                        e.addCooperator(enterprise);
                    } else if (e.getCapability() < needsTmp) {
                        needsTmp -= e.getCapability();
                        e.setCapability(0);
                        e.addCooperator(enterprise);
                    }
                }
                enterpriseService.save(enterprise);
                enterpriseService.saveAll(formerActors);
                formerActors.stream().forEach(e -> {
                    enterpriseRepository.setRelationshipOrderId(e.getRestRootUrl(), enterprise.getRestRootUrl(), orderId);
                });

                //get response  of former cooperators
                Set<Enterprise> formerCooperators = enterpriseService.getFormerCooperators(enterprise.getRestRootUrl());
                Iterator fcIterator = formerCooperators.iterator();
                while (fcIterator.hasNext()){
                    Enterprise tmp = (Enterprise)fcIterator.next();
                    String url = tmp.getRestRootUrl()+"/rest/VLE/buildConnection?direction=forward&needs={needs}&orderId={orderId}";
                    sb.append(restTemplate.getForEntity(url,String.class,urlVariables));
                }
                if (direction.equals("forward")) {
                    return sb.toString();
                }
            } else {
                return "failed, because the former actors of " + enterprise.getName() + " cannot satisfy the needs";
            }
        }
        needsTmp = needs;
        //start to build later connection
        if (direction.equals("backward") || direction.equals("bilateral")) {
            if (enterprise.getLaterRole().equals("/") && direction.equals("backward")) {
                return "success "+ direction;
            }
            Set<Enterprise> laterActors = enterpriseService.getLaterActors(enterprise.getLaterRole());
            gotLater = commonUtil.sumCapabilityIsEnough(laterActors, needs);
            if (gotLater) {
//                enterprise = findMethods.findActorsAndSetOrderId("backward", needs, laterActors, enterprise);
                Iterator iterator2 = laterActors.iterator();
                // try to find the least suppliers
                while (iterator2.hasNext() && needsTmp > 0) {
                    Enterprise e = (Enterprise) iterator2.next();
                    if (e.getCapability() >= needsTmp) {
                        e.setCapability(e.getCapability() - needsTmp);
                        needsTmp = 0;
                        enterprise.addCooperator(e);
                    } else if (e.getCapability() < needsTmp) {
                        needsTmp -= e.getCapability();
                        e.setCapability(0);
                        enterprise.addCooperator(e);
                    }
                }
                enterpriseService.save(enterprise);
                enterpriseService.saveAll(laterActors);
                laterActors.stream().forEach(e -> {
                    enterpriseRepository.setRelationshipOrderId(enterprise.getRestRootUrl(), e.getRestRootUrl(), orderId);
                });

                //get response of later cooperators
                Set<Enterprise> laterCooperators = enterpriseService.getLaterCooperators(enterprise.getRestRootUrl());
                Iterator fcIterator = laterCooperators.iterator();
                while (fcIterator.hasNext()){
                    Enterprise tmp = (Enterprise)fcIterator.next();
                    String url = tmp.getRestRootUrl()+"/rest/VLE/buildConnection?direction=backward&needs={needs}&orderId={orderId}";
                    sb.append(restTemplate.getForEntity(url,String.class,urlVariables));
                }
                if (direction.equals("backward")) {
                    return sb.toString();
                }
            } else {
                return "failed, because the later actors of " + enterprise.getName() + " cannot satisfy the needs";
            }
        }

        return sb.toString();
    }

}
