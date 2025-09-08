package com.viv.accounts.dto;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "accounts")
public record AccountsContactInfoDto(
    String message,
    String name,
    String email,
    List<String> onCallSupport,
    String address
) {

}
