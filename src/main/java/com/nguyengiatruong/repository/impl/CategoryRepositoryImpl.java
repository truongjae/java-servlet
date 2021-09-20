package com.nguyengiatruong.repository.impl;

import com.nguyengiatruong.bean.annotation.Repository;
import com.nguyengiatruong.entity.Category;
import com.nguyengiatruong.orm.repository.impl.AbstractQuerySimple;
import com.nguyengiatruong.repository.CategoryRepository;

@Repository
public class CategoryRepositoryImpl extends AbstractQuerySimple<Category,Long> implements CategoryRepository {
}
