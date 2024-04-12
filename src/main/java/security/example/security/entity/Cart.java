package security.example.security.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CART")
@Getter
@Setter
public class Cart extends AbstractAuditingEntity<Long> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CartItem> cartItems = new ArrayList<>();

    @OneToOne(mappedBy = "cart")
    private Order order;

    @Column(name = "TOTAL")
    private float total;

    @Column(name = "STATUS_PAY")
    private int statusPay;

    @Column(name = "DELETE_FLAG")
    private int deleteFlag;
}
