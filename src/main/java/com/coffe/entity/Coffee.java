package com.coffe.entity;

import com.coffe.shared.enumeration.Dimension;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "coffee")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Coffee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Dimension dimension;

    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "recipe_id")
    private Recipes recipe;

    @Override
    public String toString() {
        return "Coffee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dimension=" + dimension +
                '}';
    }
}
