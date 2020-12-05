package mglobe.africa.io.web.rest;

import mglobe.africa.io.MAgentsApp;
import mglobe.africa.io.domain.Transaction;
import mglobe.africa.io.repository.TransactionRepository;
import mglobe.africa.io.service.TransactionService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import mglobe.africa.io.domain.enumeration.TransType;
/**
 * Integration tests for the {@link TransactionResource} REST controller.
 */
@SpringBootTest(classes = MAgentsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TransactionResourceIT {

    private static final String DEFAULT_TRANS_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_TRANS_REFERENCE = "BBBBBBBBBB";

    private static final Long DEFAULT_TRANS_AMOUNT = 1L;
    private static final Long UPDATED_TRANS_AMOUNT = 2L;

    private static final Integer DEFAULT_CURRENCY = 1;
    private static final Integer UPDATED_CURRENCY = 2;

    private static final TransType DEFAULT_TRANS_TYPE = TransType.DEPOSIT;
    private static final TransType UPDATED_TRANS_TYPE = TransType.WITHDRAWAL;

    private static final String DEFAULT_TRANS_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_TRANS_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_CUST_ID = "AAAAAAAAAA";
    private static final String UPDATED_CUST_ID = "BBBBBBBBBB";

    private static final Instant DEFAULT_TRANS_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TRANS_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private MockMvc restTransactionMockMvc;

    private Transaction transaction;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Transaction createEntity() {
        Transaction transaction = new Transaction()
            .transReference(DEFAULT_TRANS_REFERENCE)
            .transAmount(DEFAULT_TRANS_AMOUNT)
            .currency(DEFAULT_CURRENCY)
            .transType(DEFAULT_TRANS_TYPE)
            .transStatus(DEFAULT_TRANS_STATUS)
            .custID(DEFAULT_CUST_ID)
            .transDate(DEFAULT_TRANS_DATE);
        return transaction;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Transaction createUpdatedEntity() {
        Transaction transaction = new Transaction()
            .transReference(UPDATED_TRANS_REFERENCE)
            .transAmount(UPDATED_TRANS_AMOUNT)
            .currency(UPDATED_CURRENCY)
            .transType(UPDATED_TRANS_TYPE)
            .transStatus(UPDATED_TRANS_STATUS)
            .custID(UPDATED_CUST_ID)
            .transDate(UPDATED_TRANS_DATE);
        return transaction;
    }

    @BeforeEach
    public void initTest() {
        transactionRepository.deleteAll();
        transaction = createEntity();
    }

    @Test
    public void createTransaction() throws Exception {
        int databaseSizeBeforeCreate = transactionRepository.findAll().size();
        // Create the Transaction
        restTransactionMockMvc.perform(post("/api/transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transaction)))
            .andExpect(status().isCreated());

        // Validate the Transaction in the database
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeCreate + 1);
        Transaction testTransaction = transactionList.get(transactionList.size() - 1);
        assertThat(testTransaction.getTransReference()).isEqualTo(DEFAULT_TRANS_REFERENCE);
        assertThat(testTransaction.getTransAmount()).isEqualTo(DEFAULT_TRANS_AMOUNT);
        assertThat(testTransaction.getCurrency()).isEqualTo(DEFAULT_CURRENCY);
        assertThat(testTransaction.getTransType()).isEqualTo(DEFAULT_TRANS_TYPE);
        assertThat(testTransaction.getTransStatus()).isEqualTo(DEFAULT_TRANS_STATUS);
        assertThat(testTransaction.getCustID()).isEqualTo(DEFAULT_CUST_ID);
        assertThat(testTransaction.getTransDate()).isEqualTo(DEFAULT_TRANS_DATE);
    }

    @Test
    public void createTransactionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = transactionRepository.findAll().size();

        // Create the Transaction with an existing ID
        transaction.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransactionMockMvc.perform(post("/api/transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transaction)))
            .andExpect(status().isBadRequest());

        // Validate the Transaction in the database
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void checkTransReferenceIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionRepository.findAll().size();
        // set the field null
        transaction.setTransReference(null);

        // Create the Transaction, which fails.


        restTransactionMockMvc.perform(post("/api/transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transaction)))
            .andExpect(status().isBadRequest());

        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllTransactions() throws Exception {
        // Initialize the database
        transactionRepository.save(transaction);

        // Get all the transactionList
        restTransactionMockMvc.perform(get("/api/transactions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transaction.getId())))
            .andExpect(jsonPath("$.[*].transReference").value(hasItem(DEFAULT_TRANS_REFERENCE)))
            .andExpect(jsonPath("$.[*].transAmount").value(hasItem(DEFAULT_TRANS_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY)))
            .andExpect(jsonPath("$.[*].transType").value(hasItem(DEFAULT_TRANS_TYPE.toString())))
            .andExpect(jsonPath("$.[*].transStatus").value(hasItem(DEFAULT_TRANS_STATUS)))
            .andExpect(jsonPath("$.[*].custID").value(hasItem(DEFAULT_CUST_ID)))
            .andExpect(jsonPath("$.[*].transDate").value(hasItem(DEFAULT_TRANS_DATE.toString())));
    }
    
    @Test
    public void getTransaction() throws Exception {
        // Initialize the database
        transactionRepository.save(transaction);

        // Get the transaction
        restTransactionMockMvc.perform(get("/api/transactions/{id}", transaction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(transaction.getId()))
            .andExpect(jsonPath("$.transReference").value(DEFAULT_TRANS_REFERENCE))
            .andExpect(jsonPath("$.transAmount").value(DEFAULT_TRANS_AMOUNT.intValue()))
            .andExpect(jsonPath("$.currency").value(DEFAULT_CURRENCY))
            .andExpect(jsonPath("$.transType").value(DEFAULT_TRANS_TYPE.toString()))
            .andExpect(jsonPath("$.transStatus").value(DEFAULT_TRANS_STATUS))
            .andExpect(jsonPath("$.custID").value(DEFAULT_CUST_ID))
            .andExpect(jsonPath("$.transDate").value(DEFAULT_TRANS_DATE.toString()));
    }
    @Test
    public void getNonExistingTransaction() throws Exception {
        // Get the transaction
        restTransactionMockMvc.perform(get("/api/transactions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateTransaction() throws Exception {
        // Initialize the database
        transactionService.save(transaction);

        int databaseSizeBeforeUpdate = transactionRepository.findAll().size();

        // Update the transaction
        Transaction updatedTransaction = transactionRepository.findById(transaction.getId()).get();
        updatedTransaction
            .transReference(UPDATED_TRANS_REFERENCE)
            .transAmount(UPDATED_TRANS_AMOUNT)
            .currency(UPDATED_CURRENCY)
            .transType(UPDATED_TRANS_TYPE)
            .transStatus(UPDATED_TRANS_STATUS)
            .custID(UPDATED_CUST_ID)
            .transDate(UPDATED_TRANS_DATE);

        restTransactionMockMvc.perform(put("/api/transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTransaction)))
            .andExpect(status().isOk());

        // Validate the Transaction in the database
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeUpdate);
        Transaction testTransaction = transactionList.get(transactionList.size() - 1);
        assertThat(testTransaction.getTransReference()).isEqualTo(UPDATED_TRANS_REFERENCE);
        assertThat(testTransaction.getTransAmount()).isEqualTo(UPDATED_TRANS_AMOUNT);
        assertThat(testTransaction.getCurrency()).isEqualTo(UPDATED_CURRENCY);
        assertThat(testTransaction.getTransType()).isEqualTo(UPDATED_TRANS_TYPE);
        assertThat(testTransaction.getTransStatus()).isEqualTo(UPDATED_TRANS_STATUS);
        assertThat(testTransaction.getCustID()).isEqualTo(UPDATED_CUST_ID);
        assertThat(testTransaction.getTransDate()).isEqualTo(UPDATED_TRANS_DATE);
    }

    @Test
    public void updateNonExistingTransaction() throws Exception {
        int databaseSizeBeforeUpdate = transactionRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransactionMockMvc.perform(put("/api/transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transaction)))
            .andExpect(status().isBadRequest());

        // Validate the Transaction in the database
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteTransaction() throws Exception {
        // Initialize the database
        transactionService.save(transaction);

        int databaseSizeBeforeDelete = transactionRepository.findAll().size();

        // Delete the transaction
        restTransactionMockMvc.perform(delete("/api/transactions/{id}", transaction.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Transaction> transactionList = transactionRepository.findAll();
        assertThat(transactionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
