package com.baicheng.gatewaydynamicdemo.support;

import com.baicheng.gatewaydynamicdemo.beans.RouteDefinitionUtilBean;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.cloud.gateway.filter.FilterDefinition;

import java.io.IOException;

/**
 * @author baicheng
 * @description
 * @create 2019-04-01 22:31
 */
@JsonComponent
public class FilterDefinitionSerializer extends StdSerializer<FilterDefinition> {
    private static final long serialVersionUID = 990790475562628135L;

    @Autowired
    private RouteDefinitionUtilBean routeDefinitionUtilBean;

    protected FilterDefinitionSerializer() {
        super(FilterDefinition.class);
    }

    @Override
    public void serialize(FilterDefinition filterDefinition, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        String text = routeDefinitionUtilBean.filterDefinitionEncode(filterDefinition);
        jsonGenerator.writeString(text);
    }
}
