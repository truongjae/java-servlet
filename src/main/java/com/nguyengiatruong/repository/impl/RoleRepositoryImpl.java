package com.nguyengiatruong.repository.impl;

import com.nguyengiatruong.bean.annotation.Repository;
import com.nguyengiatruong.entity.Role;
import com.nguyengiatruong.orm.repository.impl.AbstractQuerySimple;
import com.nguyengiatruong.repository.RoleRepository;
import static com.nguyengiatruong.util.ReflectionUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Repository
public class RoleRepositoryImpl extends AbstractQuerySimple<Role,Long> implements RoleRepository {
    @Override
    public List<Role> findAllByUserId(long userId) {
        String sql="SELECT role.* from role join permission p on role.id = p.role_id where p.user_id = ?";
        try(Connection connection = connectionPool.getConnection()){
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setObject(1,userId);
            ResultSet rs = ps.executeQuery();
            List<Role> roles= new LinkedList<>();
            Role role;
            while(rs.next()){
                role = convertToEntity(rs,Role.class);
                roles.add(role);
            }
            return roles;
        }
        catch (Exception e){
            e.printStackTrace();
            return new LinkedList<>();
        }
    }
}
