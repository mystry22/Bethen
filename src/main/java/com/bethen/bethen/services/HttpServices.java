package com.bethen.bethen.services;

import com.bethen.bethen.dto.PaymentLinkRequestDto;
import com.bethen.bethen.dto.post.PaymentResponse;
import com.bethen.bethen.models.BankList;
import com.bethen.bethen.models.BankListResponse;
import com.bethen.bethen.models.NameValidationModel;
import com.bethen.bethen.models.NameValidationResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.lang.reflect.Type;
import java.util.List;


@Component
public class HttpServices {
    private final String TEST_KEY = "sk_live_ovtu6tgmw8pfbvt4t9kumlzn4hjwsefsbtwc7af";
    private final String API_KEY = "sk_live_ovtu6tgmw8pfbvt4t9kumlzn4hjwsefsbtwc7af";
    private final String URL = "https://api.budpay.com/api/v1/bank_list/NGN";
    //Initialise WebClient
    private final WebClient webClient;

    @Autowired
    private RestTemplate restTemplate;
    //Add base URL
    public HttpServices(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("https://api.budpay.com/api/v2/").build();
    }

    //Post requests

    public Mono<?> getPaymentLink(String url, PaymentLinkRequestDto bodyRequest){
            return webClient.post()
                    .uri(url)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer "+ TEST_KEY)
                    .body(Mono.just(bodyRequest), PaymentLinkRequestDto.class)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError(),
                            response -> Mono.error(new RuntimeException("Client Error"))
                    )
                    .onStatus(status -> status.is5xxServerError(),
                            response -> Mono.error(new RuntimeException("Server Error")))
                    .bodyToMono(PaymentResponse.class);


    }

//    private Object utilityHeaderBuild(){
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("Authorization", "Bearer "+API_KEY);
//        return headers;
//    }

    //Get bank list
    public Object getBankList(){
        try{
           HttpHeaders headers = new HttpHeaders();
           headers.setContentType(MediaType.APPLICATION_JSON);
           headers.set("Authorization", "Bearer "+API_KEY);

           HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<BankListResponse> exchange = restTemplate.exchange(URL, HttpMethod.GET, entity, new ParameterizedTypeReference<BankListResponse>() {});

            if(exchange.getStatusCode().is2xxSuccessful()){
                BankListResponse bankLists = exchange.getBody();
                List<BankList> banks = bankLists.getData();
                return banks;
            }
        }
        catch (HttpServerErrorException | HttpClientErrorException e){
            throw new RuntimeException(e.getResponseBodyAsString());
        }

        return  null;
    }

    //Get account details
    public NameValidationResponseModel nameEnquiry(NameValidationModel nameValidationModel){
        try {

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + API_KEY);

            HttpEntity<NameValidationModel> entity = new HttpEntity<>(nameValidationModel, headers);

            ResponseEntity<NameValidationResponseModel> exchange = restTemplate.exchange("https://api.budpay.com/api/v2/account_name_verify", HttpMethod.POST, entity, new ParameterizedTypeReference<NameValidationResponseModel>() {
            });

            if (exchange.getStatusCode().is2xxSuccessful()) {
                NameValidationResponseModel data = exchange.getBody();
                return data;
            }

        }catch (HttpClientErrorException | HttpServerErrorException e){
            throw new RuntimeException(e.getResponseBodyAsString());
        }

        return null;
    }


}
