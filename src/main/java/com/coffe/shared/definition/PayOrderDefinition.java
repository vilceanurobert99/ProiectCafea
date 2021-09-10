package com.coffe.shared.definition;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PayOrderDefinition {
    @NonNull
    private OrderDefinition orderDefinition;
    @NonNull
    private String cardNumber;
    @NonNull
    private String expirationDate;
    @NonNull
    private String customerName;
    @NonNull
    private String civ;
}
