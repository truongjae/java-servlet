package com.nguyengiatruong.orm.paging;

public class PageRequest implements PageAble{

    private int index;
    private int size;

    public PageRequest(int index, int size) {
        this.index = index;
        this.size = size;
    }

    public static PageAble of(int index,int size){
        return new PageRequest(index,size);
    }
    @Override
    public int getOffset() {
        return (index == 1 || index ==0 ) ? 0 : (index -1) * this.size;
    }

    @Override
    public int getIndex() {
        return this.index;
    }

    @Override
    public int getSize() {
        return this.size;
    }
}
