package com.baicheng.gatewaydynamicdemo.test;

import com.baicheng.gatewaydynamicdemo.GatewayDynamicDemoApplicationTests;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;

/**
 * @author baicheng
 * @description
 * @create 2019-04-01 16:03
 */
@Slf4j
public class RouteDefinitionLocatorTest extends GatewayDynamicDemoApplicationTests {

    @Autowired
    private RouteDefinitionLocator routeDefinitionLocator;

    @Test
    public void testFindAllRoutes(){
        routeDefinitionLocator.getRouteDefinitions()
                .toStream()
                .forEach(item -> {
                    log.info("routeDefinition: {}", item);
                });
    }
}
