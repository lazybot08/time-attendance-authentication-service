package com.wipro.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wipro.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
	Optional<Department> findByDeptName(String deptName);
}
