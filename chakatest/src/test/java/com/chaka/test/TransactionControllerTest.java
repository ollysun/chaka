package com.chaka.test;

import com.chaka.test.controller.TransactionController;
import com.chaka.test.exception.TransactionException;
import com.chaka.test.model.Transaction;
import com.chaka.test.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;


@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@EnableWebMvc
@ActiveProfiles("test")
@SpringBootTest(classes = TransactionController.class)
public class TransactionControllerTest {

    private List<Transaction> transactionList = new ArrayList<>();
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void init() throws Exception {
        Date date1=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
                .parse("2020-09-27T00:50:13.345Z");
        Date date2=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
                .parse("2020-09-27T00:51:13.345Z");
        Date date3=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
                .parse("2020-09-27T00:52:13.345Z");
        Transaction transaction = new Transaction("5.0",new Timestamp(date1.getTime()));
        Transaction transaction1 = new Transaction("5.1",new Timestamp(date2.getTime()));
        Transaction transaction2 = new Transaction("5.2",new Timestamp(date3.getTime()));
        transactionList.add(transaction);
        transactionList.add(transaction1);
        transactionList.add(transaction2);
    }

    @Test
    public void testCreateTransactionWithBadRequest() throws Exception {

        final Transaction transaction = new Transaction();
        transaction.setAmount("12.3243");
        //transaction.setTimestamp(Timestamp.valueOf("2020-08-30T00:50:13.345Z"));
        String body = objectMapper.writeValueAsString(transaction);

        mockMvc.perform(post("/transaction")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateTransactionSuccess() throws Exception {

        Date date1=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
                                .parse("2020-09-27T00:50:13.345Z");
        Transaction transaction = new Transaction("12.3243",new Timestamp(date1.getTime()));
        String body = objectMapper.writeValueAsString(transaction);

        mockMvc.perform(post("/transaction")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isCreated());
    }

    @Test
    public void testStatistics() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                 .get("/statistics")
                .accept(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testStatisticsReturnException() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .get("/statistics")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof TransactionException))
                .andExpect(result -> assertEquals("Empty List", result.getResolvedException().getMessage()));

    }




}
