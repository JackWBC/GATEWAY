package com.baicheng.gatewaydynamicdemo.config;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author baicheng
 * @description
 * @create 2019-04-02 15:26
 */
@Configuration
@MapperScan(basePackages = "com.baicheng.gatewaydynamicdemo.mapper")
public class MyBatisPlusConfig {

    @Bean
    public ISqlInjector sqlInjector(){
        return new LogicSqlInjector();
    }
}
