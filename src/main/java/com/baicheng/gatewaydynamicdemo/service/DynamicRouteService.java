package com.baicheng.gatewaydynamicdemo.service;

import com.baicheng.gatewaydynamicdemo.repository.GwRouteDefinitionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author baicheng
 * @description
 * @create 2019-04-01 10:55
 */
@Service
@Slf4j
public class DynamicRouteService implements ApplicationEventPublisherAware {

    @Autowired
    private GwRouteDefinitionRepository gwRouteDefinitionRepository;

    private ApplicationEventPublisher publisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

    public Flux<RouteDefinition> getAll(){
        return gwRouteDefinitionRepository.getRouteDefinitions();
    }

    public Mono<RouteDefinition> get(String routeId){
        return gwRouteDefinitionRepository.getRouteDefinition(Mono.just(routeId));
    }

    public Mono<String> save(RouteDefinition definition){
        try {
            gwRouteDefinitionRepository.save(Mono.just(definition)).subscribe();
            this.publisher.publishEvent(new RefreshRoutesEvent(this));
            log.info("[DynamicRouteService update] success, routeDefinition:{}", definition);
            return Mono.just("success");
        } catch (Exception e){
            log.error("[DynamicRouteService update] fail, has deleted route {}, exception:{}", definition, e.toString());
            return Mono.just("fail");
        }
    }

    public Mono<String> delete(String id){
        return this.gwRouteDefinitionRepository.delete(Mono.just(id))
                .then(Mono.defer(() -> Mono.just("success")))
                .doOnSuccess(r -> log.info("[DynamicRouteService delete] success, id:{}", id))
                .onErrorResume(t -> t instanceof NotFoundException, t -> {
                    log.info("[DynamicRouteService delete] fail, id:{}", id);
                    return Mono.just("fail");
                });
    }
}
