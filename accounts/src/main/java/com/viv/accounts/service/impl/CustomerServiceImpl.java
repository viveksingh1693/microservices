package com.viv.accounts.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.viv.accounts.dto.AccountsDto;
import com.viv.accounts.dto.CardsDto;
import com.viv.accounts.dto.CustomerDetailsDto;
import com.viv.accounts.dto.CustomerDto;
import com.viv.accounts.dto.LoansDto;
import com.viv.accounts.entity.Accounts;
import com.viv.accounts.entity.Customer;
import com.viv.accounts.exception.ResourceNotFoundException;
import com.viv.accounts.mapper.AccountsMapper;
import com.viv.accounts.mapper.CustomerMapper;
import com.viv.accounts.repository.AccountsRepository;
import com.viv.accounts.repository.CustomerRepository;
import com.viv.accounts.service.ICustomerService;
import com.viv.accounts.service.client.CardsFeignClient;
import com.viv.accounts.service.client.LoansFeignClient;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service("customerService")
public class CustomerServiceImpl implements ICustomerService {

    private final AccountsRepository accountsRepository;
    private final CustomerRepository customerRepository;
    private final CardsFeignClient cardsFeignClient;
    private final LoansFeignClient loansFeignClient;

    /**
     * Fetch customer details by mobile number.
     * 
     * @param mobileNumber the mobile number of the customer
     * @return CustomerDetailsDto containing customer details
     */
    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString()));
        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));

        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer,
                new CustomerDetailsDto());
        customerDetailsDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));

        ResponseEntity<LoansDto> fetchLoanDetails = loansFeignClient.fetchLoanDetails(mobileNumber);

        if (fetchLoanDetails.getStatusCode().is2xxSuccessful() && fetchLoanDetails.getBody() != null) {
            customerDetailsDto.setLoansDto(fetchLoanDetails.getBody());
        }

        ResponseEntity<CardsDto> fetchCardDetailsResponse = cardsFeignClient.fetchCardDetails(mobileNumber);

        if (fetchCardDetailsResponse.getStatusCode().is2xxSuccessful() && fetchCardDetailsResponse.getBody() != null) {
            customerDetailsDto.setCardsDto(fetchCardDetailsResponse.getBody());
        }

        return customerDetailsDto;
    }

}
