package iprwcAPI.Controller;

import iprwcAPI.DAO.AccountDao;
import iprwcAPI.HTTPResponse;
import iprwcAPI.Models.Account;
import iprwcAPI.RequestObjects.AccountRequestObject;
import iprwcAPI.RequestObjects.AccountReturnObject;
import iprwcAPI.RequestObjects.RoleUserRequestObject;
import iprwcAPI.jwt.JwtRequest;
import iprwcAPI.jwt.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountController {
    @Autowired
    private AccountDao accountDao;

    /**
     * create a jwt token and authenticates using a given username and password, does not require a token
     *
     * @param authenticationRequest json:  {"username": "", "password": ""}
     * @return success or failure
     */
    @PostMapping("/authenticate")
    public HTTPResponse<UserResponse> createAuthToken(@RequestBody JwtRequest authenticationRequest) {
        return accountDao.authenticate(authenticationRequest);
    }

    /**
     * creates a new account
     */

    @PostMapping("/role")
    public String getRole(@RequestBody String email){
        return accountDao.getRoleByEmail(email);
    }

    @PostMapping("/register")
    public HTTPResponse<AccountReturnObject> registerAccount(@RequestBody AccountRequestObject o) {
        return accountDao.registerAccount(o.getFirstName(), o.getLastName(), o.getEmail(), o.getPassword());
    }


    @GetMapping("/account")
    public HTTPResponse<AccountReturnObject> getAccountDetails(@RequestParam(name="id", defaultValue="") String id, @RequestParam(name="email", defaultValue = "") String email) {
        // todo make sure top check if this is the account thats logged in!!!
        if (id.equals(""))
            return accountDao.getIdBelongingToEmail(email);
        return accountDao.getAccountDetails(id);
    }

    @PutMapping("/account/mod")
    public HTTPResponse<Account[]> changeAccount(@RequestBody Account[] accounts) {
        if (accounts.length == 2) {
            return accountDao.changeAccount(accounts);
        }
        return HTTPResponse.<AccountReturnObject>returnFailure("input length is not 2");
    }



    @PostMapping("/account/mod")
    public HTTPResponse<AccountReturnObject> createMod(@RequestBody AccountRequestObject acc){
        return accountDao.createMod(acc);
    }
}



