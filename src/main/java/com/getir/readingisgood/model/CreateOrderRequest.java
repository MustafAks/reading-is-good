package com.getir.readingisgood.model;


import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequest {

    private List<OrderProducts> products;
    private Long customerId;

}
