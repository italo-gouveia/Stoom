package com.stoom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stoom.data.model.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

}
