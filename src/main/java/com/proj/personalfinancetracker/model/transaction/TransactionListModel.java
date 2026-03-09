package com.proj.personalfinancetracker.model.transaction;

import lombok.Data;

import java.util.List;

@Data
public class TransactionListModel {
    private List<TransactionResponseModel> transactions;
    private int count;

    public TransactionListModel(List<TransactionResponseModel> transactions){
        this.transactions = transactions;
        this.count = transactions.size();
    }
}
