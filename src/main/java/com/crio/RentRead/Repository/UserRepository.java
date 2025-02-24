package com.crio.RentRead.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crio.RentRead.Model.User;

public interface UserRepository extends JpaRepository<User , Long>
{
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
