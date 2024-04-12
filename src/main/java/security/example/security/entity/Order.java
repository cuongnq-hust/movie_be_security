package security.example.security.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "ORDERS")
@Getter
@Setter
public class Order extends AbstractAuditingEntity<Long> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "IS_PAY")
    private int isPay;
    @Column(name = "TOTAL")
    private float total;
    @Column(name = "TAX")
    private float tax;
    @Column(name = "FEE")
    private float fee;

    @OneToOne
    @JoinColumn(name = "CART_ID")
    private Cart cart;

    @Column(name = "DELETE_FLAG")
    private int deleteFlag;
}