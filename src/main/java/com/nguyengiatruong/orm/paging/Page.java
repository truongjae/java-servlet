package com.nguyengiatruong.orm.paging;

import java.util.List;

public interface Page <T>{
    static <T> Page<T> of(int index,int size,long totalItem,List<T> data){
        return new PageImpl<>(index,size,totalItem,data);
    }
    static <T> Page<T> empty(){
        return new PageImpl<>();
    }
    int getTndex();
    int getSize();
    int getTotalItem();
    int getTotalPage();
    List<T> getData();
}
