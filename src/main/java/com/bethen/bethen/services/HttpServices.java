package com.bethen.bethen.services;

import com.bethen.bethen.dto.PaymentLinkRequestDto;
import com.bethen.bethen.dto.post.PaymentResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;


@Component
public class HttpServices {
    private final String TEST_KEY = "sk_test_zfvrwyvc1ybqcxvj70henv5oaf2sidb8csq25et";
    //Initialise WebClient
    private final WebClient webClient;
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

    //Get requests
}
