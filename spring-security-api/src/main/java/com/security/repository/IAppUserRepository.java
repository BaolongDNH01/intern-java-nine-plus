package com.security.repository;

import com.security.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IAppUserRepository extends JpaRepository<AppUser, Integer> {

    @Query(value = " select au.id, au.username, au.password, au.role_id from app_user au where au.username = :username ", nativeQuery = true)
    AppUser getAppUserByUsername(@Param("username") String username);
}
