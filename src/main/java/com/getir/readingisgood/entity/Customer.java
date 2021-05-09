package com.getir.readingisgood.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Builder
@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;
    @CreationTimestamp
    private LocalDateTime createDateTime;
}
