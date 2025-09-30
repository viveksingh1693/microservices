package com.viv.message.functions;

import java.util.function.Function;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.viv.message.dto.AccountMsgDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class MessageFunctions {

    @Bean
    public Function<AccountMsgDto, AccountMsgDto> email() {
        return accountMsgDto -> {
            log.info("Emailing: {}", accountMsgDto);
            return accountMsgDto;
        };
    }

    @Bean
    public Function<AccountMsgDto, Long> sms() {
        return accountMsgDto -> {
            log.info("Send Mesaging: {}", accountMsgDto);
            return accountMsgDto.accountNumber();
        };
    }

}
