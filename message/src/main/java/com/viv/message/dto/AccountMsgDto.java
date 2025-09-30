package com.viv.message.dto;

/**
 * Account Message Data Transfer Object
 */
public record AccountMsgDto(
    Long accountNumber,
    String name,
    String email,
    String mobileNumber) {
} 
