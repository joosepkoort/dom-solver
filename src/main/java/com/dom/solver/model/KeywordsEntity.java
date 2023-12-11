package com.dom.solver.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity(name = "Probabilities_keywords")
@Table(name = "Probabilities_keywords")
@NoArgsConstructor
@Data
@AllArgsConstructor
public class KeywordsEntity implements Serializable {

    @Id
    @SequenceGenerator(name = "my_seq", sequenceName = "seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "my_seq")
    private Long id;

    @Column(name = "value")
    private String value;
    @Column(name = "level")
    private Long level;
    @Column(name = "level_as_word")
    private String levelAsWord;

}