package com.viv.accounts.service;

import com.viv.accounts.dto.CustomerDetailsDto;

public interface ICustomerService {

    CustomerDetailsDto fetchCustomerDetails(String mobileNumber,String correlationId);

}
