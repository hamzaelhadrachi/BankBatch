package com.zerotohero.bankspringbatch.processor;

import com.zerotohero.bankspringbatch.dao.BankTransaction;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

@Component
public class BankTransactionItemProcessor implements ItemProcessor<BankTransaction,BankTransaction> {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy-HH:mm");
    @Override
    public BankTransaction process(BankTransaction item) throws Exception {

        item.setTransactionDate(dateFormat.parse(String.valueOf(item.getTransactionDate())));

        return item;
    }
}
