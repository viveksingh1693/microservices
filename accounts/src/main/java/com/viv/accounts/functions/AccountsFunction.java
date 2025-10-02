package com.viv.accounts.functions;

import java.util.function.Consumer;

import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class AccountsFunction {


    public Consumer<Long> updateCommuncation() {
        return message -> log.info("Received message: {}", message);
    }

}
