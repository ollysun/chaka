package com.chaka.test;

import com.chaka.test.controller.TransactionController;
import com.chaka.test.model.Transaction;
import com.chaka.test.model.TransactionStatisticResponse;
import com.chaka.test.service.TransactionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { ChakaTestApplication.class })
@SpringBootTest(classes = TransactionController.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransactionIntegrationTest {

    private TestRestTemplate restTemplate = new TestRestTemplate();

    @LocalServerPort
    private int port;

    @MockBean
    private TransactionService transactionService;

    private TransactionStatisticResponse transactionStatisticResponse = new TransactionStatisticResponse();

    private List<Transaction> transactionList =  new ArrayList<>();

    public TransactionIntegrationTest() {
    }

    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    @Test
    public void testAddTransactionWithUnprocessedEntity() throws Exception {
        Date date1=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
                .parse("2021-09-27T00:50:13.345Z");
        Transaction transaction = new Transaction("12.3243",new Timestamp(date1.getTime()));
        when(transactionService.addTransaction(any(Transaction.class))).thenReturn(TransactionService.DATESTATUS.FUTURE);
        ResponseEntity<Object> responseEntity = this.restTemplate
                .postForEntity(getRootUrl() + "/transaction", transaction, Object.class);
        assertEquals(422, responseEntity.getStatusCodeValue());
    }

    @Test
    public void testAddTransactionWithBadRequest() throws Exception {
        Date date1=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
                .parse("2020-09-27T00:50:13.345Z");
        Transaction transaction = new Transaction("",new Timestamp(date1.getTime()));
        ResponseEntity<Object> responseEntity = this.restTemplate
                .postForEntity(getRootUrl() + "/transaction", transaction, Object.class);
        assertEquals(400, responseEntity.getStatusCodeValue());
    }

    @Test
    public void testVerifyStatistic() throws Exception {
        Date date1=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
                .parse("2020-09-27T00:50:13.345Z");
        Date date2=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
                .parse("2020-09-27T00:50:13.345Z");
        Date date3=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
                .parse("2020-09-27T00:50:13.345Z");
        Transaction transaction = new Transaction("5.0",new Timestamp(date1.getTime()));
        Transaction transaction2 = new Transaction("5.1",new Timestamp(date1.getTime()));
        Transaction transaction3 = new Transaction("5.2",new Timestamp(date1.getTime()));
        transactionList.add(transaction);
        transactionList.add(transaction2);
        transactionList.add(transaction3);
        List<Double> amount = new ArrayList<>();
        for (Transaction transactionfor:transactionList) {
            amount.add(Double.parseDouble(transactionfor.getAmount()));
        }
        double sumValue = amount.stream()
                                .mapToDouble(Double::doubleValue)
                                .summaryStatistics()
                                .getSum();
        transactionStatisticResponse.setSum(String.valueOf(sumValue));
        when(transactionService.getTransactionStatistic()).thenReturn(transactionStatisticResponse);
        ResponseEntity<TransactionStatisticResponse> responseEntity = this.restTemplate
                .getForEntity(getRootUrl() + "/statistics", TransactionStatisticResponse.class);
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    public void deleteTransaction() {
        doNothing().when(transactionService).deleteTransaction();
        HttpEntity<String> entity = new HttpEntity<>(null, new HttpHeaders());
        ResponseEntity<Void> responseEntity = this.restTemplate
                .exchange(getRootUrl() + "/transactions", HttpMethod.DELETE, entity, (Class<Void>) null);
        //ResponseEntity<String> response = restTemplate.exchange("/books/1", HttpMethod.DELETE, entity, String.class);
        assertEquals(204, responseEntity.getStatusCodeValue());
    }
}
