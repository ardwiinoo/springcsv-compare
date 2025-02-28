package com.ardwiinoo.csvapp.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "countries")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Country {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "country_name")
    private String countryName;

    @Column(name = "region")
    private String region;
}
