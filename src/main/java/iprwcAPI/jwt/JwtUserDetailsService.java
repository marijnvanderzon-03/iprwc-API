package iprwcAPI.jwt;

import iprwcAPI.DAO.AccountDao;
import iprwcAPI.Models.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    private AccountDao accountDao;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    /** username means email in this context
     * @param email the users email
     * @return details on the user
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountDao.getByEmail(email);
        if (account == null)
            throw new UsernameNotFoundException("User not found with email: " + email);
        return new org.springframework.security.core.userdetails.User(account.getEmail(), account.getPassword(), new ArrayList<>());
    }

    public boolean doPasswordsMatch(String password, String encryptedPassword) {
        return bcryptEncoder.matches(password, encryptedPassword);
    }

    public String getHashedPassword(String password) {
        return bcryptEncoder.encode(password);
    }

    public void saveAccount(Account account) {
        accountDao.addAccount(account);
    }
}
