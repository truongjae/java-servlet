package com.nguyengiatruong.repository;

import com.nguyengiatruong.bean.annotation.Repository;
import com.nguyengiatruong.entity.Blog;
import com.nguyengiatruong.orm.repository.JpaRepository;
@Repository
public interface BlogRepository extends JpaRepository<Blog,Long> {
}
