package com.baicheng.gatewaydynamicdemo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author baicheng
 * @description
 * @create 2019-03-28 19:36
 */
@RestController
@RequestMapping("/fallback")
public class SomeController {

    @RequestMapping("/")
    public Mono<String> fallback(){
        return Mono.just("fallback");
    }
}
