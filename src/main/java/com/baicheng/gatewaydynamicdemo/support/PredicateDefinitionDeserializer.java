package com.baicheng.gatewaydynamicdemo.support;

import com.baicheng.gatewaydynamicdemo.beans.RouteDefinitionUtilBean;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;

import java.io.IOException;

/**
 * @author baicheng
 * @description
 * @create 2019-04-01 22:29
 */
@JsonComponent
public class PredicateDefinitionDeserializer extends StdDeserializer<PredicateDefinition> {
    private static final long serialVersionUID = 4044098713779814932L;

    @Autowired
    private RouteDefinitionUtilBean routeDefinitionUtilBean;

    protected PredicateDefinitionDeserializer() {
        super(PredicateDefinition.class);
    }

    @Override
    public PredicateDefinition deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        return routeDefinitionUtilBean.predicateDefinitionDecode(jsonParser.getText());
    }
}
