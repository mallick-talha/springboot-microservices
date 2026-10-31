package com.banks.gatewayserver.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController

public class FailmsgController {
    @RequestMapping("/contactSupport")
    public Mono<String>failMsg(){
        return Mono.just("An error occured. Please try after some timesor contact support team").cache();
    }
}
