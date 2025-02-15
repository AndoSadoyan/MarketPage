package am.personal.acc_management.Model;


import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "accounts")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private Integer balance;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name= "cart",
            joinColumns = @JoinColumn(name = "acc_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> cart = new ArrayList<>();

    public User(String email, String username, String password, Integer balance)
    {
        this.email = email;
        this.username = username;
        this.password = password;
        this.balance = balance;
        cart = new ArrayList<>();
    }

    @PrePersist
    private void prePersist() {
        if (balance == null) {
            balance = 0;
        }
    }
}



