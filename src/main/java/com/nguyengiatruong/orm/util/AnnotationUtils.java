package com.nguyengiatruong.orm.util;

import com.nguyengiatruong.orm.annotation.Column;
import com.nguyengiatruong.orm.annotation.Entity;
import com.nguyengiatruong.orm.annotation.Id;
import org.apache.commons.lang3.StringUtils;

import java.util.Locale;

public class AnnotationUtils {
    public static <T> String getCLassName(Class<T> t){
        String name = t.getAnnotation(Entity.class).name();
        name = StringUtils.isEmpty(name) ? t.getSimpleName().toLowerCase() : name;
        return name;
    }
    public static <T> String getColumnName(Class<T> t,String name) throws NoSuchFieldException {
        String columnName = t.getDeclaredField(name).getAnnotation(Column.class).name();
        columnName = StringUtils.isEmpty(columnName) ? ConvertToColumnName(name) : columnName;
        return columnName;
    }
    public static String ConvertToColumnName(String name){
        StringBuilder columnName = new StringBuilder();
        char[] chars = name.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if(chars[i] >= 'A' && chars[i] <= 'Z'){
                columnName.append("_").append((char)(chars[i]+32));
                continue;
            }
            columnName.append(chars[i]);
        }
        return columnName.toString();
    }
    public static <T> boolean getAutoIncreament(Class<T> t,String name) throws NoSuchFieldException {
        return t.getDeclaredField(name).getDeclaredAnnotation(Id.class).increament();
    }
    public static <T> String getPrimaryKey(Class<T> t,String name) throws NoSuchFieldException {
       String primaryKey = t.getDeclaredField(name).getAnnotation(Id.class).name();
       primaryKey = StringUtils.isEmpty(primaryKey) ? ConvertToColumnName(name) : primaryKey;
       return primaryKey;

    }
}
