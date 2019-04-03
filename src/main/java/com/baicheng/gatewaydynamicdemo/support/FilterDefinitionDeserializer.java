package com.baicheng.gatewaydynamicdemo.support;

import com.baicheng.gatewaydynamicdemo.beans.RouteDefinitionUtilBean;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.cloud.gateway.filter.FilterDefinition;

import java.io.IOException;

/**
 * @author baicheng
 * @description
 * @create 2019-04-01 22:34
 */
@JsonComponent
public class FilterDefinitionDeserializer extends StdDeserializer<FilterDefinition> {
    private static final long serialVersionUID = 7848100834487490724L;

    @Autowired
    private RouteDefinitionUtilBean routeDefinitionUtilBean;

    protected FilterDefinitionDeserializer() {
        super(FilterDefinition.class);
    }

    @Override
    public FilterDefinition deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        return routeDefinitionUtilBean.filterDefinitionDecode(jsonParser.getText());
    }
}
