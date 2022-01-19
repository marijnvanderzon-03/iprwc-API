package iprwcAPI.DAO;

import iprwcAPI.HTTPResponse;

import iprwcAPI.Models.Account;
import iprwcAPI.Repository.AccountRepository;
import iprwcAPI.RequestObjects.AccountRequestObject;
import iprwcAPI.RequestObjects.AccountReturnObject;
import iprwcAPI.jwt.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Component
public class AccountDao {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private JwtUserDetailsService userDetailsService;
    @Autowired
    private AccountRepository accountRepository;

    public void addAccount(Account account) {
        accountRepository.save(account);
    }

    public String getRoleByEmail(String email){
        Optional<Account> account = accountRepository.findByEmail(email);
        String role = account.get().getRole();
        return role;
    }

    public Account getByEmail(String email) {
        Optional<Account> acc = accountRepository.findByEmail(email);
        if (acc.isEmpty()) return null;
        else return acc.get();
    }
    public HTTPResponse<AccountReturnObject> getIdBelongingToEmail(String email) {
        Optional<Account> account = accountRepository.findByEmail(email);
        if (account.isEmpty())
            return HTTPResponse.<AccountReturnObject>returnFailure("could not find account with that email");
        AccountReturnObject obj = new AccountReturnObject(account.get());
        return HTTPResponse.<AccountReturnObject>returnSuccess(obj);
    }


    public HTTPResponse<AccountReturnObject> getAccountDetails(String id) {
        Optional<Account> account = accountRepository.findById(id);
        if (account.isEmpty())
            return HTTPResponse.returnFailure("could not find account with id: " + id);
        return HTTPResponse.returnSuccess(new AccountReturnObject(account.get()));
    }

    public HTTPResponse<Account[]> changeAccount(Account[] accounts) {
        Account old = accounts[0];
        Account newObject = accounts[1];
        Optional<Account> account = accountRepository.findById(old.getId());
        if (account.isEmpty()) {
            return HTTPResponse.returnFailure("could not find account with id: " + old.getId());
        }
        newObject.setId(old.getId());

        accountRepository.save(newObject);
        return HTTPResponse.returnSuccess(accounts);
    }

    public HTTPResponse<AccountReturnObject> createMod(AccountRequestObject acc){
        HTTPResponse<AccountReturnObject> r = registerAccount(acc.getFirstName(), acc.getLastName(), acc.getEmail(), acc.getPassword());
        if (!r.isSuccess())
            return r;
        String email = r.getData().getEmail();
        return HTTPResponse.returnSuccess(r.getData());
    }



    /** register a new accoutn with the following information
     * @param firstName the first name
     * @param lastName the last name
     * @param email the email of the account, used to log in
     * @param password the encrypted password used by the account
     * @return an HTTPResponse containing the created account
     */
    public HTTPResponse<AccountReturnObject> registerAccount(String firstName, String lastName, String email, String password) {

        if (email.equals("") || password.equals(""))
            return HTTPResponse.<AccountReturnObject>returnFailure("one ore more required parameters were empty");
        else if (accountRepository.findByEmail(email).isPresent())
            return HTTPResponse.<AccountReturnObject>returnFailure("that email already exists: " + email);

        String hashedPassword = userDetailsService.getHashedPassword(password);
        Account a = new Account(firstName, lastName, email, hashedPassword, "");
        accountRepository.save(a);
        return HTTPResponse.<AccountReturnObject>returnSuccess(new AccountReturnObject(a));
    }

    /** authenticates an account
     * @param authenticationRequest the data to authenticate with
     * @return failure or success
     */
    public HTTPResponse<UserResponse> authenticate(JwtRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        } catch (DisabledException e) {
            return HTTPResponse.<UserResponse>returnUserDisabled("user: " + authenticationRequest.getUsername() + " is disabled");
        } catch (BadCredentialsException e) {
            return HTTPResponse.<UserResponse>returnInvalidCredentials("");

        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        final Account user = accountRepository.findByEmail(authenticationRequest.getUsername()).get();
        return returnToken(token, user);
    }

    public HTTPResponse<UserResponse> returnToken(String response, Account account) {
        UserResponse userDetails = new UserResponse(account.getEmail(), account.getFirstName(), account.getLastName(), response);
        return HTTPResponse.<UserResponse>returnSuccess(userDetails);
    }
}