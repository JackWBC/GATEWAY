package com.baicheng.gatewaydynamicdemo.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Stream;

/**
 * @author baicheng
 * @description
 * @create 2019-04-02 11:53
 */
@Repository
@Slf4j
public class GwRouteDefinitionRepository implements RouteDefinitionRepository {

    @Autowired
    private GwRouteBaseRepository gwRouteBaseRepository;

    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        List<RouteDefinition> routeDefinitions = gwRouteBaseRepository.getRouteDefinitions();
        Stream<RouteDefinition> stream = routeDefinitions.stream();
        return Flux.fromStream(stream);
    }

    public Mono<RouteDefinition> getRouteDefinition(Mono<String> routeId){
        return routeId.map(item -> gwRouteBaseRepository.getRouteDefinition(item));
    }

    @Override
    public Mono<Void> save(Mono<RouteDefinition> route) {
         return route.map(item -> {
                    gwRouteBaseRepository.deleteRouteDefinition(item.getId());
                    return gwRouteBaseRepository.addRouteDefinition(item);
                })
                .then();
    }

    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        return routeId.map(item -> gwRouteBaseRepository.deleteRouteDefinition(item))
                .then();
    }
}
