package com.finovate.accounts.Service.Impl;

import com.finovate.accounts.Constants.AccountsConstants;
import com.finovate.accounts.DTO.AccountsDto;
import com.finovate.accounts.DTO.CustomerDto;
import com.finovate.accounts.Model.Accounts;
import com.finovate.accounts.Model.Customer;
import com.finovate.accounts.Exception.CustomerAlreadyExistsException;
import com.finovate.accounts.Exception.ResourceNotFoundException;
import com.finovate.accounts.Mapper.AccountsMapper;
import com.finovate.accounts.Mapper.CustomerMapper;
import com.finovate.accounts.Repository.AccountsRepository;
import com.finovate.accounts.Repository.CustomerRepository;
import com.finovate.accounts.Service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsServiceImpl  implements IAccountsService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;

    /**
     * @param customerDto - CustomerDto Object
     */
    @Override
    public void createAccount(CustomerDto customerDto) {
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if( optionalCustomer.isPresent() ) {
            throw new CustomerAlreadyExistsException( "Customer already registered with given mobileNumber "
                    +customerDto.getMobileNumber() );
        }
        Customer customer = CustomerMapper.mapToCustomer( customerDto, new Customer() );
        customer.setCreatedAt( LocalDateTime.now() );
        customer.setCreatedBy( "Anonymous" );
        Customer savedCustomer = customerRepository.save( customer );
        accountsRepository.save( createNewAccount(savedCustomer) );
    }

    /*
     * @param customer - Customer Object
     * @return the new account details
     */
    private Accounts createNewAccount(Customer customer) {
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId( customer.getCustomerId() );
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        newAccount.setCreatedAt( customer.getCreatedAt() );
        newAccount.setCreatedBy(customer.getCreatedBy() );
        return newAccount;
    }



    /*
    /**
     * @param mobileNumber - Input Mobile Number
     * @return Accounts Details based on a given mobileNumber
     */
    @Override
    public CustomerDto fetchAccount( String mobileNumber ) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );
        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountsDto( AccountsMapper.mapToAccountsDto( accounts, new AccountsDto() ) );
        return customerDto;
    }

    /**
     * @param customerDto - CustomerDto Object
     * @return boolean indicating if the update of Account details is successful or not
     */
    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();
        if(accountsDto !=null ){
            Accounts accounts = accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "AccountNumber", accountsDto.getAccountNumber().toString())
            );
            AccountsMapper.mapToAccounts(accountsDto, accounts);
            accounts = accountsRepository.save(accounts);

            Long customerId = accounts.getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "CustomerID", customerId.toString())
            );
            CustomerMapper.mapToCustomer(customerDto, customer);
            customerRepository.save(customer);
            isUpdated = true;
        }
        return  isUpdated;
    }

    /**
     * @param mobileNumber - Input Mobile Number
     * @return boolean indicating if the delete of Account details is successful or not
     */
    @Override
    public boolean deleteAccount(String mobileNumber) {
//        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
//                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
//        );
//        accountsRepository.deleteByCustomerId(customer.getCustomerId());
//        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }


}
