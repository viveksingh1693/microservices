package com.viv.accounts.dto;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "accounts")
public class AccountsContactInfoDto {
    String message;
    String name;
    String email;
    List<String> onCallSupport;
    String address;

}
