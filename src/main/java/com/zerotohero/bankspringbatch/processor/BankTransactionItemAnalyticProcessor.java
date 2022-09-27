package com.zerotohero.bankspringbatch.processor;

import com.zerotohero.bankspringbatch.dao.BankTransaction;
import lombok.Getter;
import org.springframework.batch.item.ItemProcessor;


public class BankTransactionItemAnalyticProcessor implements ItemProcessor<BankTransaction,BankTransaction> {
    @Getter
    private double totalDebit;
    @Getter
    private double totalCredit;

    @Override
    public BankTransaction process(BankTransaction bankTransaction) throws Exception {
        if (bankTransaction.getTransactionType().equals("D")){
            totalDebit += bankTransaction.getTransactionAmount();
        }else if (bankTransaction.getTransactionType().equals("C")) {
            totalCredit += bankTransaction.getTransactionAmount();
        }


        return bankTransaction;
    }
}
