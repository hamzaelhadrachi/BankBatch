package com.zerotohero.bankspringbatch.dao;

import com.zerotohero.bankspringbatch.dao.BankTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankTransactionRepository extends JpaRepository<BankTransaction,Long> {
}
