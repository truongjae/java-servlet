package com.nguyengiatruong.orm.repository.impl;

import com.nguyengiatruong.orm.annotation.Id;
import com.nguyengiatruong.orm.exception.ORMException;
import com.nguyengiatruong.orm.paging.Page;
import com.nguyengiatruong.orm.paging.PageAble;
import com.nguyengiatruong.orm.paging.PageImpl;
import com.nguyengiatruong.orm.pool.ConnectionPool;
import com.nguyengiatruong.orm.repository.JpaRepository;
import com.nguyengiatruong.orm.repository.builder.Query;
import org.apache.commons.lang3.StringUtils;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.nguyengiatruong.orm.util.AnnotationUtils.*;
import static com.nguyengiatruong.util.ReflectionUtil.*;

public class AbstractQuerySimple<T,ID> implements JpaRepository <T,ID> {
    private Class<T> tClass;
    public ConnectionPool connectionPool;
    private String tableName;
    private String save;
    private String update;
    private String delete;
    private String select;
    private String count;
    public AbstractQuerySimple(){
        this.connectionPool = new ConnectionPool();
        this.tClass = (Class<T>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        this.tableName = getCLassName(tClass);
        this.save = "INSERT INTO "+this.tableName+"(%s)" + "VALUES(%s)";
        this.update = "UPDATE "+this.tableName+ " SET %s WHERE %s";
        this.select = "SELECT * FROM "+this.tableName;
        this.delete = "DELETE FROM "+this.tableName+" WHERE %s";
        this.count = "SELECT COUNT(1) as total FROM "+this.tableName;
    }
    @Override
    public void save(T t) {
        Field[] fields = tClass.getDeclaredFields();
        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();
        List<Object> listValues = new ArrayList<>();
        for (int i = 0; i < fields.length; i++) {
            String name = fields[i].getName();
            if(fields[i].isAnnotationPresent(Id.class)){
                try {
                    boolean increament = getAutoIncreament(tClass,name);
                    if(!increament){
                        columns.append(getPrimaryKey(tClass,name)).append(",");
                        values.append("?,");
                        listValues.add(get(t,fields[i]));
                    }
                } catch (Exception e) {
                    throw new ORMException(e.getMessage());
                }
                continue;
            }
            try {
                columns.append(getColumnName(tClass,name)).append(",");
                values.append("?,");
                listValues.add(get(t,fields[i]));
            } catch (Exception e) {
                e.printStackTrace();
                throw new ORMException(e.getMessage());
            }
        }
        columns.deleteCharAt(columns.length()-1);
        values.deleteCharAt(values.length()-1);
        this.save = String.format(this.save,columns.toString(),values.toString());
        Connection connection = null;
        try{
            connection = connectionPool.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(this.save);
            int i = 1;
            for (Object object : listValues){
                ps.setObject(i++,object);
            }
            ps.executeUpdate();
            connection.commit();
        } catch (SQLException throwables) {
            try {
                connection.rollback();
            } catch (Exception e) {
                e.printStackTrace();
                throw  new ORMException(e.getMessage());
            }
        }
        finally {
            if(connection != null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new ORMException(e.getMessage());
                }
            }
        }
    }

    @Override
    public void update(ID id, T t) {
        Field[] fields = tClass.getDeclaredFields();
        StringBuilder setStatement = new StringBuilder();
        List<Object> values = new ArrayList<>();
        for (int i = 1; i < fields.length; i++) {
            String name = fields[i].getName();
            try {
                setStatement.append(getColumnName(tClass,name)).append("=?,");
                values.add(get(t,fields[i]));
            } catch (Exception e) {
                throw new ORMException(e.getMessage());
            }
        }
        Connection connection = null;
        try {
            String where = getPrimaryKey(tClass,fields[0].getName())+"=?";
            values.add(id);
            setStatement.deleteCharAt(setStatement.length()-1);
            this.update = String.format(this.update,setStatement,where);
            connection = connectionPool.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(this.update);
            int i=1;
            for(Object object : values){
                ps.setObject(i++,object);
            }
            ps.executeUpdate();
            connection.commit();
        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new ORMException(e.getMessage());
            }
        }finally {
            if(connection != null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new ORMException(e.getMessage());
                }
            }
        }
    }

    @Override
    public void delete(ID id) {
        try {
            this.delete = String.format(this.delete,"id = ?");
        } catch (Exception e) {
            throw new ORMException(e.getMessage());
        }
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(this.delete);
            ps.setObject(1,id);
            ps.executeUpdate();
            connection.commit();
        }
        catch (Exception e){
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new ORMException(ex.getMessage());
            }
        }finally {
            if(connection!=null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new ORMException(e.getMessage());
                }
            }
        }
    }

    @Override
    public void deleteAll(List<ID> ids) {
        String idConditon = ids.stream().map(String::valueOf).collect(Collectors.joining(","));
        this.delete = String.format(this.delete,"id in ("+idConditon+")");
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(this.delete);
            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            throw new ORMException(e.getMessage());
        }finally {
            if(connection!=null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new ORMException(e.getMessage());
                }
            }
        }

    }

    @Override
    public Optional<T> findByID(ID id) {
        StringBuilder findById = new StringBuilder(this.select);
        findById.append(" WHERE id = ?");
        try(Connection connection = connectionPool.getConnection()){
            PreparedStatement ps = connection.prepareStatement(findById.toString());
            ps.setObject(1,id);
            ResultSet rs = ps.executeQuery();
            T t = null;
            if(rs.next()){
                t = convertToEntity(rs,tClass);
            }
            return Optional.of(t);
        } catch (SQLException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchFieldException throwables) {
            throwables.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Stream<T> findAll() {
        try (Connection connection = connectionPool.getConnection()){
            PreparedStatement ps = connection.prepareStatement(this.select);
            ResultSet rs = ps.executeQuery();
            List<T> list = new ArrayList<>();
            T t;
            while (rs.next()){
                t = convertToEntity(rs,tClass);
                list.add(t);
            }
            return list.stream();
        } catch (SQLException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchFieldException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Page<T> findAll(PageAble pageAble) {
        StringBuilder selectPage = new StringBuilder(this.select);
        if(pageAble.getIndex()!=0 && pageAble.getSize() != 0){
            selectPage.append(" LIMIT ?").append(" OFFSET ?");
        }
        try (Connection connection = connectionPool.getConnection()){
            PreparedStatement ps = connection.prepareStatement(selectPage.toString());
            ps.setInt(1,pageAble.getSize());
            ps.setInt(2,pageAble.getOffset());
            ResultSet rs = ps.executeQuery();
            List<T> list = new LinkedList<>();
            T t;
            while (rs.next()){
                 t = convertToEntity(rs,tClass);
                 list.add(t);
            }
            long totalItem = count();
            Page<T> page = new PageImpl<T>(pageAble.getIndex(),pageAble.getSize(),totalItem,list);
            return page;

        } catch (SQLException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchFieldException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public long count() {
        try (Connection connection = connectionPool.getConnection()){
            PreparedStatement ps = connection.prepareStatement(this.count);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return rs.getLong("total");
            }
            return 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    @Override
    public Optional<T> findAll(Query<T> query) {
        StringBuilder sqlBuilder = new StringBuilder(this.select);
        if(StringUtils.isNotEmpty(query.condition())){
            sqlBuilder.append(" WHERE ").append(query.condition());
        }
        try(Connection connection = connectionPool.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sqlBuilder.toString());
            if(query.value()!=null){
                ps.setObject(1,query.value());
            }
            else if(query.values()!=null) {
                int j=1;
                for(Object value : query.values()){
                    ps.setObject(j++,value);
                }
            }
            ResultSet rs = ps.executeQuery();
            T t = null;
            if (rs.next()){
                t = convertToEntity(rs,tClass);
            }
            return Optional.of(t);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Page<T> findAll(Query<T> query, PageAble pageAble) {
        StringBuilder sqlBuilder = new StringBuilder(this.select);
        if(StringUtils.isNotEmpty(query.condition())){
            sqlBuilder.append(" WHERE ").append(query.condition());
        }
        if(pageAble != null){
            sqlBuilder.append(" LIMIT ? OFFSET ?");
        }
        try(Connection connection = connectionPool.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sqlBuilder.toString());
            int j=1;
            if(query.value()!=null){
                ps.setObject(j++,query.value());
            }
            else if(query.values()!=null) {
                for(Object value : query.values()){
                    ps.setObject(j++,value);
                }
            }
            ps.setInt(j++,pageAble.getSize());
            ps.setInt(j,pageAble.getOffset());
            ResultSet rs = ps.executeQuery();
            List<T> list = new LinkedList<>();
            T t = null;
            while (rs.next()){
                t = convertToEntity(rs,tClass);
                list.add(t);
            }
            long totalItem = count(query);
            return Page.of(pageAble.getIndex(),pageAble.getSize(),totalItem,list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Page.empty();
    }

    @Override
    public long count(Query<T> query) {
        StringBuilder sqlBuilder = new StringBuilder(this.count);
        if(StringUtils.isNotEmpty(query.condition())){
            sqlBuilder.append(" WHERE ").append(query.condition());
        }
        try(Connection connection = connectionPool.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sqlBuilder.toString());
            if(query.value()!=null){
                ps.setObject(1,query.value());
            }
            else if(query.values()!=null) {
                int j=1;
                for(Object value : query.values()){
                    ps.setObject(j++,value);
                }
            }
            ResultSet rs = ps.executeQuery();
            return rs.getLong("total");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
