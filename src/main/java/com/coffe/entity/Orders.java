package com.coffe.entity;

import com.coffe.shared.enumeration.Delivery;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDate;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Delivery delivery;

    @Min(value = 1)
    @Max(value = 20)
    private int quantity;

    @Column(name = "total_cost", nullable = false)
    @Min(value = 0)
    private double totalCost;

    @Column(nullable = false)
    @Min(value = 0)
    private double profit;

    @Column(name = "audit_date")
    private LocalDate auditDate;

    @Column(name = "coffee_name", nullable = false)
    private String coffeeName;

    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Override
    public String toString() {
        return "Orders{" +
                "id=" + id +
                ", delivery=" + delivery +
                ", quantity=" + quantity +
                ", coffee=" + coffeeName +
                ", totalCost=" + totalCost +
                ", auditDate=" + auditDate +
                '}';
    }

    public String getStringAudit() {
        return auditDate.toString();
    }
}
