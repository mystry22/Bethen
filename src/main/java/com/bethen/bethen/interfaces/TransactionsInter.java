package com.bethen.bethen.interfaces;

import com.bethen.bethen.dto.ActivatePlanRequestDto;
import com.bethen.bethen.dto.PaymentLinkRequestDto;
import com.bethen.bethen.dto.post.PaymentResponse;
import com.bethen.bethen.models.*;

import java.util.List;
import java.util.Optional;

public interface TransactionsInter {
    public Object generatePaymentLink(PaymentLinkRequestDto paymentLinkRequestDto, String token);
    public String updatePayment(String reference,String amount);
    public List<TransactionsModel> allTransactions();
    public String activatePlan(ActivatePlanRequestDto activatePlanRequestDto, String token );
    public List<TransactionsResponseModel> getUserTransactions(String token);
    public List<BankList> getBankList();
    public NameValidationResponseModel validateName(NameValidationModel nameValidationModel);
}
