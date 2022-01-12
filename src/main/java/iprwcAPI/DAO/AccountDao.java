package iprwcAPI.DAO;

import iprwcAPI.HTTPResponse;
import iprwcAPI.Model.Account;
import iprwcAPI.Repository.AccountRepository;
import iprwcAPI.RequestObjects.AccountReturnObject;
import iprwcAPI.jwt.JwtRequest;
import iprwcAPI.jwt.JwtResponse;
import iprwcAPI.jwt.JwtTokenUtil;
import iprwcAPI.jwt.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

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

    public Account getByEmail(String email) {
        Optional<Account> acc = accountRepository.findByUsername(email);
        if (acc.isEmpty()) return null;
        else return acc.get();
    }
    public HTTPResponse<AccountReturnObject> getIdBelongingToUsername(String username) {
        Optional<Account> account = accountRepository.findByUsername(username);
        if (account.isEmpty())
            return HTTPResponse.<AccountReturnObject>returnFailure("could not find account with that email");
        AccountReturnObject obj = new AccountReturnObject(account.get().getId(),
                account.get().getUsername());
        return HTTPResponse.<AccountReturnObject>returnSuccess(obj);
    }

    public HTTPResponse<AccountReturnObject> getAccountDetails(String id) {
        Optional<Account> account = accountRepository.findById(id);
        if (account.isEmpty())
            return HTTPResponse.returnFailure("could not find account with id: " + id);
        return HTTPResponse.returnSuccess(new AccountReturnObject(account.get().getId(),account.get().getUsername()));
    }
    /** register a new accoutn with the following information
     * @param password the encrypted password used by the account
     * @return an HTTPResponse containing the created account
     */
    public HTTPResponse registerAccount(String username, String password) {

        if (username.equals("") || password.equals(""))
            return HTTPResponse.<Account>returnFailure("one ore more required parameters were empty");
        else if (accountRepository.findByUsername(username).isPresent())
            return HTTPResponse.<Account>returnFailure("that user already exists: " + username);

        Account a = new Account(username, password);
        userDetailsService.saveAccount(a);
        return HTTPResponse.<Account>returnSuccess(a);
    }

    /** authenticates an account
     * @param authenticationRequest the data to authenticate with
     * @return failure or success
     */
    public HTTPResponse authenticate(JwtRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        } catch (DisabledException e) {
            return HTTPResponse.<JwtResponse>returnUserDisabled("user: " + authenticationRequest.getUsername() + " is disabled");

        } catch (BadCredentialsException e) {
            return HTTPResponse.<JwtResponse>returnInvalidCredentials("");

        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return HTTPResponse.<JwtResponse>returnSuccess(new JwtResponse(token));
    }
}

