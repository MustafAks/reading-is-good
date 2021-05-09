package com.getir.readingisgood.model;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductQuantityRequest {

    private Integer quantity;
    private Long id;

}
