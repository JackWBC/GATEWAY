package com.baicheng.gatewaydynamicdemo.support;

import com.baicheng.gatewaydynamicdemo.beans.RouteDefinitionUtilBean;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;

import java.io.IOException;

/**
 * @author baicheng
 * @description
 * @create 2019-04-01 21:54
 */
@JsonComponent
public class PredicateDefinitionSerializer extends StdSerializer<PredicateDefinition> {
    private static final long serialVersionUID = 8741190476231225080L;

    @Autowired
    private RouteDefinitionUtilBean routeDefinitionUtilBean;

    protected PredicateDefinitionSerializer() {
        super(PredicateDefinition.class);
    }

    @Override
    public void serialize(PredicateDefinition predicateDefinition, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        String text = routeDefinitionUtilBean.predicateDefinitionEncode(predicateDefinition);
        jsonGenerator.writeString(text);
    }
}
