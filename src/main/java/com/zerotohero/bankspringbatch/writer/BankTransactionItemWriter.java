package com.zerotohero.bankspringbatch.writer;

import com.zerotohero.bankspringbatch.dao.BankTransaction;
import com.zerotohero.bankspringbatch.dao.BankTransactionRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BankTransactionItemWriter implements ItemWriter<BankTransaction> {

    @Autowired
    private BankTransactionRepository bankRepository;

    @Override
    public void write(List<? extends BankTransaction> list) throws Exception {
        bankRepository.saveAll(list);
    }
}
