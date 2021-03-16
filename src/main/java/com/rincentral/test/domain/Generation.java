package com.rincentral.test.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Generation {

    @Id
    private Long id;

    private String title;

    @OneToOne
    private Model model;

    private String years;

    private Integer length;

    private Integer width;

    private Integer height;

}
