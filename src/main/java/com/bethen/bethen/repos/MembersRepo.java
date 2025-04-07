package com.bethen.bethen.repos;

import com.bethen.bethen.models.MemberModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MembersRepo extends MongoRepository<MemberModel, String> {
    Optional<MemberModel> findByEmail(String email);

}
