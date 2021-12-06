package com.enojen.getir.repository;

import com.enojen.getir.model.ERole;
import com.enojen.getir.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(ERole name);
}