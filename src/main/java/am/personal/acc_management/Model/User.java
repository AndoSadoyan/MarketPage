package am.personal.acc_management.Model;


import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "accounts")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(unique = true, nullable = false)
    String email;
    @Column(nullable = false)
    String username;
    @Column(nullable = false)
    String password;
    @Column(nullable = false)
    Integer balance;

    public User(String email, String username, String password, Integer balance)
    {
        this.email = email;
        this.username = username;
        this.password = password;
        this.balance = balance;
    }

    @PrePersist
    private void prePersist() {
        if (balance == null) {
            balance = 0;
        }
    }
}



