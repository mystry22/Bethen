package com.bethen.bethen.repos;

import com.bethen.bethen.models.TransactionsModel;
import com.bethen.bethen.models.TransactionsResponseModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepo extends MongoRepository<TransactionsModel, String> {
    Optional<List<TransactionsResponseModel>> getTransactionsByUserId(String userId);
}
