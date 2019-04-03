package com.baicheng.gatewaydynamicdemo.repository;

import com.baicheng.gatewaydynamicdemo.beans.RouteDefinitionUtilBean;
import com.baicheng.gatewaydynamicdemo.mapper.GwFilterMapper;
import com.baicheng.gatewaydynamicdemo.mapper.GwPredicateMapper;
import com.baicheng.gatewaydynamicdemo.mapper.GwRouteMapper;
import com.baicheng.gatewaydynamicdemo.pos.GwFilterPO;
import com.baicheng.gatewaydynamicdemo.pos.GwPredicatePO;
import com.baicheng.gatewaydynamicdemo.pos.GwRoutePO;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Triple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author baicheng
 * @description
 * @create 2019-04-02 14:20
 */
@Repository
@Slf4j
public class GwRouteBaseRepository {

    @Autowired
    private GwRouteMapper gwRouteMapper;
    @Autowired
    private GwPredicateMapper gwPredicateMapper;
    @Autowired
    private GwFilterMapper gwFilterMapper;
    @Autowired
    private RouteDefinitionUtilBean routeDefinitionUtilBean;

    public List<RouteDefinition> getRouteDefinitions(){
        List<GwRoutePO> gwRoutePOS = gwRouteMapper.selectList(null);
        List<GwPredicatePO> gwPredicatePOS = gwPredicateMapper.selectList(null);
        List<GwFilterPO> gwFilterPOS = gwFilterMapper.selectList(null);

        Map<String, List<GwPredicatePO>> gwPredicateMap = gwPredicatePOS.stream()
                .collect(Collectors.groupingBy(GwPredicatePO::getRouteId));
        Map<String, List<GwFilterPO>> gwFilterMap = gwFilterPOS.stream()
                .collect(Collectors.groupingBy(GwFilterPO::getRouteId));

        return gwRoutePOS.stream()
                .filter(Objects::nonNull)
                .map(item -> routeDefinitionUtilBean.routeDefinitionDecode2(item, gwPredicateMap.get(item.getRouteId()), gwFilterMap.get(item.getRouteId())))
                .collect(Collectors.toList());
    }

    public RouteDefinition getRouteDefinition(String routeId){
        Wrapper<GwRoutePO> routeWrapper = new QueryWrapper<GwRoutePO>()
                .select(GwRoutePO.class, i -> true)
                .eq("route_id", routeId)
                .orderByDesc("id");

        List<GwRoutePO> gwRoutePOS = gwRouteMapper.selectList(routeWrapper);
        if (CollectionUtils.isEmpty(gwRoutePOS)){
            return null;
        }

        Wrapper<GwPredicatePO> predicateWrapper = new QueryWrapper<GwPredicatePO>()
                .select(GwPredicatePO.class, i -> true)
                .eq("route_id", routeId);
        Wrapper<GwFilterPO> filterWrapper = new QueryWrapper<GwFilterPO>()
                .select(GwFilterPO.class, i -> true)
                .eq("route_id", routeId);
        List<GwPredicatePO> gwPredicatePOS = gwPredicateMapper.selectList(predicateWrapper);
        List<GwFilterPO> gwFilterPOS = gwFilterMapper.selectList(filterWrapper);

        return gwRoutePOS.stream()
                .filter(Objects::nonNull)
                .map(item -> routeDefinitionUtilBean.routeDefinitionDecode2(item, gwPredicatePOS, gwFilterPOS))
                .findFirst()
                .orElse(null);
    }

    public Long addRouteDefinition(RouteDefinition routeDefinition){
        if (null == routeDefinition){
            return null;
        }
        Triple<GwRoutePO, List<GwPredicatePO>, List<GwFilterPO>> triple = routeDefinitionUtilBean.routeDefinitionEncode(routeDefinition);
        gwRouteMapper.insert(triple.getLeft());

        triple.getMiddle().stream()
                .filter(Objects::nonNull)
                .forEach(item -> gwPredicateMapper.insert(item));
        triple.getRight().stream()
                .filter(Objects::nonNull)
                .forEach(item -> gwFilterMapper.insert(item));
        return triple.getLeft().getId();
    }

    public Boolean deleteRouteDefinition(String routeId){
        Wrapper<GwRoutePO> routeWrapper = new QueryWrapper<GwRoutePO>()
                .eq("route_id", routeId);

        int delete = gwRouteMapper.delete(routeWrapper);

        if (delete <= 0){
            return false;
        }

        Wrapper<GwPredicatePO> predicateWrapper = new QueryWrapper<GwPredicatePO>()
                .eq("route_id", routeId);
        gwPredicateMapper.delete(predicateWrapper);

        Wrapper<GwFilterPO> filterWrapper = new QueryWrapper<GwFilterPO>()
                .eq("route_id", routeId);
        gwFilterMapper.delete(filterWrapper);

        return true;
    }

}
