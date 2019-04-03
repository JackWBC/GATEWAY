package com.baicheng.gatewaydynamicdemo.controller;

import com.baicheng.gatewaydynamicdemo.service.DynamicRouteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author baicheng
 * @description
 * @create 2019-04-01 11:17
 */
@RestController
@RequestMapping("/route")
@Slf4j
public class DynamicRouteController {

    @Autowired
    private DynamicRouteService dynamicRouteService;

    @GetMapping("/")
    public Flux<RouteDefinition> getAllRouteDefinition(){
        return dynamicRouteService.getAll();
    }

    @GetMapping("/{id}")
    public Mono<RouteDefinition> getRouteDefinition(@PathVariable("id") String id){
        return dynamicRouteService.get(id);
    }

    @PostMapping(path = "/add")
    public Mono<String> addRouteDefinition(@RequestBody RouteDefinition definition){
        try {
            return dynamicRouteService.save(definition);
        } catch (Exception e){
            log.error("[DynamicRouteController add] error, GatewayRouteDefinition:{}", definition);
            return Mono.just("fail");
        }
    }

    @GetMapping("/delete/{id}")
    public Mono<String> deleteRouteDefinition(@PathVariable("id") String id){
        return dynamicRouteService.delete(id);
    }
}
