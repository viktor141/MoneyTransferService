package ru.vixtor.moneytransferservice;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.testcontainers.containers.GenericContainer;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MoneyTransferServiceApplicationTests {

    @Autowired
    TestRestTemplate restTemplate;

    static HttpHeaders headers = new HttpHeaders();


    public static GenericContainer<?> restApp = new GenericContainer<>("restapp")
            .withExposedPorts(5500);

    @BeforeAll
    public static void setUp() {
        restApp.start();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    void contextLoadsTransferOk() {
        String jsonBodyStr = "{\n" +
                "  \"cardFromNumber\": \"1111222233334444\",\n" +
                "  \"cardFromValidTill\": \"12/23\",\n" +
                "  \"cardFromCVV\": \"111\",\n" +
                "  \"cardToNumber\": \"4444333322221111\",\n" +
                "  \"amount\": {\n" +
                "    \"value\": 100000,\n" +
                "    \"currency\": \"RUR\"\n" +
                "  }\n" +
                "}";

        HttpEntity<String> request = new HttpEntity<>(jsonBodyStr, headers);
        String response = restTemplate.postForObject(
                "http://localhost:" + restApp.getMappedPort(5500) + "/transfer",request, String.class);
        assertEquals("{\"operationId\":\"0\",\"code\":\"AUTHORISED\"}", response);
    }

}
