package iprwcAPI.Model;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class Account {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    private Account() {}
    public Account(String username, String password) {
        this.username = username;
        this.password = password;
        this.id = UUID.randomUUID().toString();
    }


    public String getId() {
        return id;
    }


    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
