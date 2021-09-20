package com.nguyengiatruong.orm.paging;

import java.util.List;

public class PageImpl<T> implements Page<T>{
    private int index;
    private int size;
    private long totalItem;
    private int totalPage;
    private List<T> data;

    public PageImpl() {
    }
    public PageImpl(int index, int size, long totalItem, List<T> data) {
        this.index = index;
        this.size = size;
        this.totalItem = totalItem;
        this.data = data;
        this.totalPage = getTotalPage();
    }



    @Override
    public int getTndex() {
        return this.index;
    }

    @Override
    public int getSize() {
        return this.size;
    }

    @Override
    public int getTotalItem() {
        return (int) this.totalItem;
    }

    @Override
    public int getTotalPage() {
        return this.size == 0 ? 0 : (int) Math.floor(this.totalItem/size);
    }

    @Override
    public List<T> getData() {
        return this.data;
    }
}
