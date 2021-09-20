package com.nguyengiatruong.util;

import com.nguyengiatruong.orm.annotation.Id;
import com.nguyengiatruong.orm.util.AnnotationUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ReflectionUtil {
    public static <T> Object get(T t, Field field) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String name = field.getName();
        String methodName = "get"+name.substring(0,1).toUpperCase()+name.substring(1);
        Method method = t.getClass().getMethod(methodName);
        return method.invoke(t);
    }
    public static <T> void set(T t, Field field, Object value) throws NoSuchMethodException, IllegalAccessException {
        field.setAccessible(true);
        field.set(t,value);
    }
    public static <T> T convertToEntity(ResultSet rs, Class tClass) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException, SQLException {
        var t = tClass.getDeclaredConstructor().newInstance();
        var fields = tClass.getDeclaredFields();
        for(Field field : fields){
            if(field.isAnnotationPresent(Id.class)){
                set(t,field,rs.getObject(AnnotationUtils.getPrimaryKey(tClass,field.getName())));
                continue;
            }
            String columnName = AnnotationUtils.getColumnName(tClass,field.getName());
            if(field.getType().equals(LocalDate.class)){
                String date = rs.getString(columnName);
                set(t,field,TimeUtil.convertToLocalDate(date));
            }else if(field.getType().equals(LocalDateTime.class)){
                String date = rs.getString(columnName);
                set(t,field,TimeUtil.convertToLocalDateTime(date));
            }else{
                set(t,field,rs.getObject(columnName));
            }
        }
        return (T) t;

    }
}
