package com.board.springbd.model;

import lombok.*;

import javax.persistence.*;

@Entity @Table(name = "category")
@Getter @Setter @Builder @ToString
@NoArgsConstructor @AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "category_id")
    private long categoryId;

    @Column(name = "category_name", nullable = false, unique = true)
    private String categoryName;

    @Column(name = "state")
    private boolean state;
}
