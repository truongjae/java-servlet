package com.nguyengiatruong.orm.repository;

import com.nguyengiatruong.orm.paging.Page;
import com.nguyengiatruong.orm.paging.PageAble;
import com.nguyengiatruong.orm.repository.builder.Query;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface JpaRepository <T,ID>{
    void save(T t);
    void update(ID id, T t);
    void delete(ID id);
    void deleteAll(List<ID> ids);
    Optional<T> findByID(ID id);
    Stream<T> findAll();
    Page<T> findAll(PageAble pageAble);
    long count();
    Optional<T> findAll(Query<T> query);
    Page<T> findAll(Query<T> query, PageAble pageAble);
    long count(Query<T> query);
}
