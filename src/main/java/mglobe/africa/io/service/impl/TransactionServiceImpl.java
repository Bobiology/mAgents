package mglobe.africa.io.service.impl;

import mglobe.africa.io.service.TransactionService;
import mglobe.africa.io.domain.Transaction;
import mglobe.africa.io.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Transaction}.
 */
@Service
public class TransactionServiceImpl implements TransactionService {

    private final Logger log = LoggerFactory.getLogger(TransactionServiceImpl.class);

    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Transaction save(Transaction transaction) {
        log.debug("Request to save Transaction : {}", transaction);
        return transactionRepository.save(transaction);
    }

    @Override
    public Page<Transaction> findAll(Pageable pageable) {
        log.debug("Request to get all Transactions");
        return transactionRepository.findAll(pageable);
    }


    @Override
    public Optional<Transaction> findOne(String id) {
        log.debug("Request to get Transaction : {}", id);
        return transactionRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Transaction : {}", id);
        transactionRepository.deleteById(id);
    }
}
