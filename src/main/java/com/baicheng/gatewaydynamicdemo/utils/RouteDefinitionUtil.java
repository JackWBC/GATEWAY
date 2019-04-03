package com.baicheng.gatewaydynamicdemo.utils;

import com.baicheng.gatewaydynamicdemo.pos.GwFilterPO;
import com.baicheng.gatewaydynamicdemo.pos.GwPredicatePO;
import com.baicheng.gatewaydynamicdemo.pos.GwRoutePO;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.support.NameUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author baicheng
 * @description
 * @create 2019-04-02 11:16
 */
public interface RouteDefinitionUtil {

    default String predicateDefinitionEncode(PredicateDefinition predicateDefinition){
        Optional<Boolean> optional = predicateDefinition.getArgs().keySet().stream()
                .findFirst().map(item -> StringUtils.isNotEmpty(item) && StringUtils.startsWith(item, NameUtils.GENERATED_NAME_PREFIX));
        if (!optional.isPresent()){
            throw new RuntimeException("[RouteDefinitionUtil predicateDefinitionEncode] parameter error, predicateDefinition = " + predicateDefinition);
        }
        if (optional.get()){
            return predicateDefinition.getName() + "=" + predicateDefinition.getArgs().values()
                    .stream().collect(Collectors.joining(","));
        }
        return predicateDefinition.getName() + "=" + predicateDefinition.getArgs().entrySet()
                .stream().map(item -> item.getKey().trim() + "~" + item.getValue().trim())
                .collect(Collectors.joining(","));
    }

    default PredicateDefinition predicateDefinitionDecode(String text){
        boolean hasWave = StringUtils.contains(text, "~");
        if (hasWave){
            Pair<String, LinkedHashMap<String, String>> pair = waveDefinitionDecode(text);
            String name = pair.getLeft();
            LinkedHashMap<String, String> args = pair.getRight();
            if (CollectionUtils.isEmpty(args)){
                throw new RuntimeException("[RouteDefinitionUtil predicateDefinitionDecode] parameter error, text = " + text);
            }
            PredicateDefinition predicateDefinition = new PredicateDefinition();
            predicateDefinition.setName(name);
            predicateDefinition.setArgs(args);
            return predicateDefinition;
        }
        return new PredicateDefinition(text);
    }

    default String filterDefinitionEncode(FilterDefinition filterDefinition){
        if (filterDefinition == null){
            return null;
        }
        Optional<Boolean> optional = filterDefinition.getArgs().keySet().stream()
                .findFirst().map(item -> StringUtils.isNotEmpty(item) && StringUtils.startsWith(item, NameUtils.GENERATED_NAME_PREFIX));
        if (!optional.isPresent()){
            return filterDefinition.getName();
        }
        if (optional.get()){
            return filterDefinition.getName() + "=" + filterDefinition.getArgs().values()
                    .stream().collect(Collectors.joining(","));
        }
        return filterDefinition.getName() + "=" + filterDefinition.getArgs().entrySet()
                .stream().map(item -> item.getKey().trim() + "~" + item.getValue().trim())
                .collect(Collectors.joining(","));
    }

    default FilterDefinition filterDefinitionDecode(String text){
        boolean hasWave = StringUtils.contains(text, "~");
        if (hasWave){
            Pair<String, LinkedHashMap<String, String>> pair = waveDefinitionDecode(text);
            FilterDefinition filterDefinition = new FilterDefinition();
            filterDefinition.setName(pair.getLeft());
            filterDefinition.setArgs(pair.getRight());
            return filterDefinition;
        }
        return new FilterDefinition(text);
    }

    default Pair<String, LinkedHashMap<String, String>> waveDefinitionDecode(String text){
        List<String> data = Arrays.stream(StringUtils.split(text, "="))
                .filter(StringUtils::isNotEmpty)
                .map(String::trim)
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(data) || data.size() > 2){
            throw new RuntimeException("[RouteDefinitionUtil colonDefinitionDecode] parameter error, text = " + text);
        }
        String name = data.get(0);
        if (data.size() == 1){
            return Pair.of(name, new LinkedHashMap<>());
        }

        LinkedHashMap<String, String> args = new LinkedHashMap<>();
        Arrays.stream(StringUtils.split(data.get(1), ","))
                .filter(StringUtils::isNotEmpty)
                .filter(item -> StringUtils.contains(item, "~"))
                .map(String::trim)
                .forEach(item -> {
                    Pair<String, String> pair = waveTextDecode(item);
                    if (pair != null){
                        args.put(pair.getLeft(), pair.getRight());
                    }
                });

        return Pair.of(name, args);
    }

    default Pair<String, String> waveTextDecode(String text){
        List<String> data = Arrays.stream(StringUtils.split(text, "~"))
                .filter(StringUtils::isNotEmpty)
                .map(String::trim)
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(data) || data.size() != 2){
            return null;
        }
        return Pair.of(data.get(0), data.get(1));
    }

//    ---------------------------------------------------------------------------------------------------------

    default RouteDefinition routeDefinitionDecode(GwRoutePO gwRoutePO, List<PredicateDefinition> predicates, List<FilterDefinition> filters){
        RouteDefinition routeDefinition = new RouteDefinition();
        routeDefinition.setId(gwRoutePO.getRouteId());
        routeDefinition.setUri(UriComponentsBuilder.fromUriString(gwRoutePO.getRouteUri()).build().toUri());
        routeDefinition.setOrder(gwRoutePO.getRouteOrder());
        routeDefinition.setPredicates(CollectionUtils.isEmpty(predicates) ? new ArrayList<>() : predicates);
        routeDefinition.setFilters(CollectionUtils.isEmpty(filters) ? new ArrayList<>() : filters);
        return routeDefinition;
    }

    default RouteDefinition routeDefinitionDecode2(GwRoutePO gwRoutePO, List<GwPredicatePO> gwPredicatePOS, List<GwFilterPO> gwFilterPOS){
        List<PredicateDefinition> predicates = CollectionUtils.isEmpty(gwPredicatePOS) ? new ArrayList<>() :
                gwPredicatePOS.stream().map(item -> predicateDefinitionDecode(item.getPredicateText())).collect(Collectors.toList());
        List<FilterDefinition> filters = CollectionUtils.isEmpty(gwFilterPOS) ? new ArrayList<>() :
                gwFilterPOS.stream().map(item -> filterDefinitionDecode(item.getFilterText())).collect(Collectors.toList());
        return routeDefinitionDecode(gwRoutePO, predicates, filters);
    }

    default Triple<GwRoutePO, List<GwPredicatePO>, List<GwFilterPO>> routeDefinitionEncode(RouteDefinition routeDefinition){
        String routeId = routeDefinition.getId();

        GwRoutePO gwRoutePO = GwRoutePO.builder()
                .routeId(routeId)
                .routeOrder(routeDefinition.getOrder())
                .routeUri(routeDefinition.getUri().toString())
                .build();

        List<GwPredicatePO> gwPredicatePOS = new ArrayList<>();
        if (!CollectionUtils.isEmpty(routeDefinition.getPredicates())){
            gwPredicatePOS = routeDefinition.getPredicates()
                    .stream()
                    .filter(Objects::nonNull)
                    .map(item -> predicateDefinitionEncode(item, routeId))
                    .collect(Collectors.toList());
        }
        List<GwFilterPO> gwFilterPOS = new ArrayList<>();
        if (!CollectionUtils.isEmpty(routeDefinition.getFilters())){
            gwFilterPOS = routeDefinition.getFilters()
                    .stream()
                    .filter(Objects::nonNull)
                    .map(item -> filterDefinitionEncode(item, routeId))
                    .collect(Collectors.toList());
        }
        return Triple.of(gwRoutePO, gwPredicatePOS, gwFilterPOS);
    }

    default GwPredicatePO predicateDefinitionEncode(PredicateDefinition predicateDefinition, String routeId){
        return GwPredicatePO.builder()
                .routeId(routeId)
                .predicateName(predicateDefinition.getName())
                .predicateText(predicateDefinitionEncode(predicateDefinition))
                .build();
    }

    default GwFilterPO filterDefinitionEncode(FilterDefinition filterDefinition, String routeId){
        return GwFilterPO.builder()
                .routeId(routeId)
                .filterName(filterDefinition.getName())
                .filterText(filterDefinitionEncode(filterDefinition))
                .build();
    }
}
