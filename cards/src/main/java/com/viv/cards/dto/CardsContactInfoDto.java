package com.viv.cards.dto;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@ConfigurationProperties(prefix = "cards")
public class CardsContactInfoDto{
    String message;
    String name;
    String email;
    List<String> onCallSupport;
    String address;

}
