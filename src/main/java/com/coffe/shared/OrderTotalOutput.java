package com.coffe.shared;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
public class OrderTotalOutput {
    private  Map<String, List<OrdersTotalOutcome>> ordersPerDay;
    private  Map<String, Double> profitPerDay;
}
