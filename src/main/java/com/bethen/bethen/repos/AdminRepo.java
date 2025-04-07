package com.bethen.bethen.repos;

import com.bethen.bethen.models.AdminModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;


public interface AdminRepo extends MongoRepository<AdminModel, String> {
    Optional<AdminModel> findAdminByEmail(String email);
}
