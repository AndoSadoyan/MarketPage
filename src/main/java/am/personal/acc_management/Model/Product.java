package am.personal.acc_management.Model;


import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String name;
    @Column(nullable = false)
    private Integer price;
    @Column(nullable = false)
    private Integer amount;

    public Product(String name, Integer price, Integer amount)
    {
        this.name = name;
        this.price = price;
        this.amount = amount;
    }

    @PrePersist
    private void prePersist() {
        if (amount == null) {
            amount = 0;
        }
    }
}
