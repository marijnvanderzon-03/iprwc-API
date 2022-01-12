package iprwcAPI.Controller;

import iprwcAPI.DAO.AccountDao;
import iprwcAPI.HTTPResponse;
import iprwcAPI.RequestObjects.AccountRequestObject;
import iprwcAPI.jwt.JwtRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public HTTPResponse createAuthToken(@RequestBody JwtRequest authenticationRequest) {
        return accountDao.authenticate(authenticationRequest);
    }

    /**
     * creates a new account
     */
    @PostMapping("/register")
    public HTTPResponse registerAccount(@RequestBody AccountRequestObject o) {
        return accountDao.registerAccount(o.getUsername() ,o.getPassword());
    }

    @GetMapping("/account")
    public HTTPResponse getAccountDetails(@RequestParam(name="id", defaultValue="") String id, @RequestParam(name="username", defaultValue = "") String username) {
        // todo make sure top check if this is the account thats logged in!!!
        if (id.equals(""))
            return accountDao.getIdBelongingToUsername(username);
        return accountDao.getAccountDetails(id);
    }


}

