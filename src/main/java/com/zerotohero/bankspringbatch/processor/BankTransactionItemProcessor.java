package com.zerotohero.bankspringbatch.processor;

import com.zerotohero.bankspringbatch.dao.BankTransaction;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

//@Component
public class BankTransactionItemProcessor implements ItemProcessor<BankTransaction,BankTransaction> {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy-HH:mm");

    @Override
    public BankTransaction process(BankTransaction item) throws Exception {


        item.setTransactionDate(dateFormat.parse(item.getStrTransactionDate()));

        return item;
    }
}
