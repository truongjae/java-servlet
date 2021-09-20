package com.nguyengiatruong.repository;

import com.nguyengiatruong.bean.annotation.Repository;
import com.nguyengiatruong.entity.Category;
import com.nguyengiatruong.orm.repository.JpaRepository;
@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
}
