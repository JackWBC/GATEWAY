package com.baicheng.gatewaydynamicdemo.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author baicheng
 * @description
 * @create 2019-04-01 15:54
 */
@Slf4j
public class SomeTest {

    private static final String HTTPBIN_URI = "http://localhost:8888";

    @Test
    public void testRouteDefinition(){
        RouteDefinition routeDefinition = new RouteDefinition();
        routeDefinition.setId("get_1");
        routeDefinition.setOrder(0);
        routeDefinition.setUri(UriComponentsBuilder.fromUriString(HTTPBIN_URI).build().toUri());

        FilterDefinition filterDefinition = new FilterDefinition();
        filterDefinition.setName("get_1_filter_1");

    }
}
