package com.coffe.shared;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrdersTotalOutcome {
    private String coffeeName;
    private double totalPrice;
}
