package com.bank.accounts.service.impl;

import com.bank.accounts.dto.*;
import com.bank.accounts.entity.Accounts;
import com.bank.accounts.entity.Customer;
import com.bank.accounts.exception.ResourceNotFoundException;
import com.bank.accounts.mapper.AccountsMapper;
import com.bank.accounts.mapper.CustomerMapper;
import com.bank.accounts.repository.AccountsRepository;
import com.bank.accounts.repository.CustomerRepository;
import com.bank.accounts.service.ICustomerService;
import com.bank.accounts.service.client.CardsFeignClient;
import com.bank.accounts.service.client.LoansFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements ICustomerService {

      public AccountsRepository accountsRepository;
      public CustomerRepository customerRepository;
      public CardsFeignClient cardsFeignClient;
      public LoansFeignClient loansFeignClient;

    @Override
    public CustomerDetailsDto fetchCustomerDetails(String correlationId,String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );

        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());
        customerDetailsDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));

        ResponseEntity<LoansDto> loansDtoResponseEntity = loansFeignClient.fetchLoanDetails(correlationId,mobileNumber);
        customerDetailsDto.setLoansDto(loansDtoResponseEntity.getBody());

        ResponseEntity<CardsDto> cardsDtoResponseEntity = cardsFeignClient.fetchCardDetails(correlationId,mobileNumber);
        customerDetailsDto.setCardsDto(cardsDtoResponseEntity.getBody());

        return customerDetailsDto;
    }

}
