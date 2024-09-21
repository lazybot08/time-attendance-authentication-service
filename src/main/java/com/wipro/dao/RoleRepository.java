package com.wipro.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wipro.entity.Role;
import com.wipro.enums.RoleName;

public interface RoleRepository extends JpaRepository<Role, Integer> {
	
	Role findByRoleName(RoleName roleName);
}
