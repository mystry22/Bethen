package com.bethen.bethen.repos;

import com.bethen.bethen.models.InvestmentModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InvestmentRepo extends MongoRepository<InvestmentModel, String> {
}
