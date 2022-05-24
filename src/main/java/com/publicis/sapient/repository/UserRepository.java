package com.publicis.sapient.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.publicis.sapient.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
