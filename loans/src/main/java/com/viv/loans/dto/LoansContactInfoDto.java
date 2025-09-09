package com.viv.loans.dto;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@ConfigurationProperties(prefix = "loans")
public class LoansContactInfoDto{
    String message;
    String name;
    String email;
    List<String> onCallSupport;
    String address;
}
