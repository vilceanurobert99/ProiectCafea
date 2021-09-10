package com.coffe.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

import static java.util.Objects.isNull;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "first_name", nullable = false)
    @Size(min = 3, max = 20)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    @Size(min = 3, max = 20)
    private String lastName;
    @Column(nullable = false, unique = true)
    @Email
    private String email;

    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "customer")
    private Set<Orders> orders;

    public void addOrder(Orders order) {
        if (isNull(orders)) {
            this.orders = new HashSet<>();
        }
        this.orders.add(order);
    }

    public void clearOrders() {
        this.orders.clear();
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", orders=" + orders +
                '}';
    }
}
