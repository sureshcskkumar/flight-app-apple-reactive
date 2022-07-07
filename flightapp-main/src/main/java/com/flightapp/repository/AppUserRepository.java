package com.flightapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flightapp.entity.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {

	List<AppUser> findByEmail(String email);

}
