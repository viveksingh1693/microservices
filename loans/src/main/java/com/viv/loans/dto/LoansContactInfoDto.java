package com.viv.loans.dto;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "loans")
public record LoansContactInfoDto(
    String message,
    String name,
    String email,
    List<String> onCallSupport,
    String address
) {

}
