package com.baicheng.gatewaydynamicdemo.test.repository;

import com.baicheng.gatewaydynamicdemo.GatewayDynamicDemoApplicationTests;
import com.baicheng.gatewaydynamicdemo.beans.RouteDefinitionUtilBean;
import com.baicheng.gatewaydynamicdemo.pos.GwFilterPO;
import com.baicheng.gatewaydynamicdemo.pos.GwPredicatePO;
import com.baicheng.gatewaydynamicdemo.pos.GwRoutePO;
import com.baicheng.gatewaydynamicdemo.repository.GwRouteBaseRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;

import java.util.Arrays;
import java.util.LinkedHashMap;

/**
 * @author baicheng
 * @description
 * @create 2019-04-02 16:05
 */
@Slf4j
public class RouteBaseRepositoryTest extends GatewayDynamicDemoApplicationTests {

    @Autowired
    private RouteDefinitionUtilBean routeDefinitionUtilBean;
    @Autowired
    private GwRouteBaseRepository gwRouteBaseRepository;

    @Test
    public void testDeleteRouteDefinition(){
        Boolean headers_route = gwRouteBaseRepository.deleteRouteDefinition("headers_route");

        log.info("delete headers_route :{}", headers_route);
    }

    @Test
    public void testAddRouteDefinition(){

        LinkedHashMap<String, String> predicateArgs = new LinkedHashMap<>();
        predicateArgs.put("_genkey_0", "/get");

        PredicateDefinition predicateDefinition = new PredicateDefinition();
        predicateDefinition.setName("Path");
        predicateDefinition.setArgs(predicateArgs);

//        GwPredicatePO gwPredicatePO = GwPredicatePO.builder()
//                .predicateName("Path")
//                .predicateText(predicateDefinition)
//                .build();

        LinkedHashMap<String, String> filterArgs = new LinkedHashMap<>();
        filterArgs.put("_genkey_0", "Hello");
        filterArgs.put("_genkey_1", "World");

        FilterDefinition filterDefinition = new FilterDefinition();
        filterDefinition.setName("AddRequestHeader");
        filterDefinition.setArgs(filterArgs);

//        GwFilterPO gwFilterPO = GwFilterPO.builder()
//                .filterName("AddRequestHeader")
//                .filterText(filterDefinition)
//                .build();

        GwRoutePO gwRoutePO = GwRoutePO.builder()
                .routeId("get_route")
                .routeOrder(0)
                .routeUri("http://localhost:8888")
                .build();

        RouteDefinition routeDefinition = routeDefinitionUtilBean.routeDefinitionDecode(
                gwRoutePO, Arrays.asList(predicateDefinition), Arrays.asList(filterDefinition));

        Long aLong = gwRouteBaseRepository.addRouteDefinition(routeDefinition);
        log.info("addRouteDefinition {}", aLong);

        RouteDefinition getRoute = gwRouteBaseRepository.getRouteDefinition("get_route");
        log.info("get_route:{}", getRoute);

    }
}
