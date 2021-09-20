package com.nguyengiatruong.repository.impl;

import com.nguyengiatruong.bean.annotation.Repository;
import com.nguyengiatruong.entity.Blog;
import com.nguyengiatruong.orm.repository.impl.AbstractQuerySimple;
import com.nguyengiatruong.repository.BlogRepository;

@Repository
public class BlogRepositoryImpl extends AbstractQuerySimple<Blog,Long> implements BlogRepository {
}
