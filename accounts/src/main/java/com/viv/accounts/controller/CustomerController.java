package com.viv.accounts.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.viv.accounts.dto.CustomerDetailsDto;
import com.viv.accounts.dto.ErrorResponseDto;
import com.viv.accounts.service.ICustomerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;

@Tag(name = "Rest Api for Customer", description = "CRUD REST APIs to CREATE, UPDATE, FETCH AND DELETE Customer details")
@RestController
@RequestMapping(path = "/api", produces = { MediaType.APPLICATION_JSON_VALUE })
@RequiredArgsConstructor
@Validated
public class CustomerController {

    private final ICustomerService customerService;

    /**
     * Fetch customer details by mobile number.
     * 
     * @param mobileNumber the mobile number of the customer
     * @return ResponseEntity containing CustomerDetailsDto
     */

    @Operation(summary = "Fetch Customer Details REST API", description = "REST API to fetch Customer &  Account details based on a mobile number")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTP Status OK"),
            @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/fetchCustomerDetails")
    public ResponseEntity<CustomerDetailsDto> fetchAccountDetails(
            @RequestParam @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits") String mobileNumber) {

        CustomerDetailsDto customerDetailsDto = customerService.fetchCustomerDetails(mobileNumber);
        if (customerDetailsDto != null) {
            return ResponseEntity.ok(customerDetailsDto);
        }

        return null;
    }

}
