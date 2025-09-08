package com.viv.cards.dto;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "cards")
public record CardsContactInfoDto(
    String message,
    String name,
    String email,
    List<String> onCallSupport,
    String address
) {

}
