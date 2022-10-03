package com.nineplus.pharmacy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nineplus.pharmacy.model.TUser;

public interface TUserRepository extends JpaRepository<TUser,Long> {
	
    TUser findByUserNm(String userNm);

}
