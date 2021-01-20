package com.smuniov.telegrambot.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "cities", uniqueConstraints = {@UniqueConstraint(columnNames = "name", name = "cities_unique_name")})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CityData {
    public static final int START_SEQ = 100000;
    @Id
    @SequenceGenerator(name = "global_seq", sequenceName = "global_seq", allocationSize = 1, initialValue = START_SEQ)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
    private Integer id;

    @Column(name = "name", unique = true, nullable = false)
    @NotBlank
    private String name;

    @Column(name = "description", unique = true, nullable = false)
    @NotBlank
    @Size(min = 5)
    private String description;
}
