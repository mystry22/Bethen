package com.bethen.bethen.repos;

import com.bethen.bethen.models.TransactionsModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepo extends MongoRepository<TransactionsModel, String> {
}
