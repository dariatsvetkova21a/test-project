package com.rincentral.test.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Car {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private Model model;

    @OneToOne
    private Modification modification;
}
