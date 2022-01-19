package iprwcAPI.RequestObjects;

import iprwcAPI.Models.Account;

import java.util.HashSet;
import java.util.Set;

public class AccountReturnObject {
    String id;
    String firstName;
    String lastName;
    String email;
    String role;
    public AccountReturnObject() {
    }

    public AccountReturnObject(Account account) {
        this.id = account.getId();
        this.firstName = account.getFirstName();
        this.lastName = account.getLastName();
        this.email = account.getEmail();
        this.role = account.getRole();
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
