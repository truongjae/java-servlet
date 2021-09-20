package com.nguyengiatruong.orm.repository.builder;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class QueryFactory {
    public static <T> Query<T> equal(String column,Object value){
        return new QueryImpl<>(column,Expression::equal,value);
    }
    public static <T> Query<T> notEqual(String column, Object value){
        return new QueryImpl<>(column,Expression::notEqual,value);
    }
    public static <T> Query<T> gt(String column,Object value){
        return new QueryImpl<>(column,Expression::gt,value);
    }
    public static <T> Query<T> gte(String column,Object value){
        return new QueryImpl<>(column,Expression::gte,value);
    }
    public static <T> Query<T> lt(String column,Object value){
        return new QueryImpl<>(column,Expression::lt,value);
    }
    public static <T> Query<T> lte(String column,Object value){
        return new QueryImpl<>(column,Expression::lte,value);
    }
    public static <T> Query<T> like(String column,Object value){
        return new QueryImpl<>(column,Expression::like,value);
    }
    public static <T> Query<T> isNull(String column){
        return new QueryImpl<>(column,Expression::isNULL);
    }
    public static <T> Query<T> isNotNull(String column){
        return new QueryImpl<>(column,Expression::isNOTNULL);
    }
    public static <T> Query<T> and(Query<T> query, Query<T> query1){
        assert query != null;
        assert query1 != null;
        StringBuilder conditionBuilder = new StringBuilder(query.condition());
        conditionBuilder.append(Expression.and()).append(query1.condition());
        List<Object> objects = List.of(query.value(),query1.value());
        return new QueryImpl<>(conditionBuilder.toString(),objects);
    }
    public static <T> Query<T> or (Query<T> query, Query<T> query1){
        assert query != null;
        assert query1 != null;
        StringBuilder conditionBuilder = new StringBuilder(query.condition());
        conditionBuilder.append(Expression.or()).append(query1.condition());
        List<Object> objects = List.of(query.value(),query1.value());
        return new QueryImpl<>(conditionBuilder.toString(),objects);
    }
    public static <T> Query<T> and(List<Query<T>> queries){
        List<Query<T>> rs = queries.stream().filter(Objects::nonNull).collect(Collectors.toList());
        if(rs.size()==1){
            return new QueryImpl<>(rs.get(0).condition(),rs.get(0).value());
        }
        String condition = rs.stream().map(Query::condition).collect(Collectors.joining(" AND "));
        List<Object> objects = rs.stream().map(Query::value).collect(Collectors.toList());
        return new QueryImpl<>(condition,objects);
    }
    public static <T> Query<T> or(List<Query<T>> queries){
        List<Query<T>> rs = queries.stream().filter(Objects::nonNull).collect(Collectors.toList());
        if(rs.size()==1){
            return new QueryImpl<>(rs.get(0).condition(),rs.get(0).value());
        }
        String condition = rs.stream().map(Query::condition).collect(Collectors.joining(" OR "));
        List<Object> objects = rs.stream().map(Query::value).collect(Collectors.toList());
        return new QueryImpl<>(condition,objects);
    }

}
