package com.board.springbd.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="board")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="board_seq")
    private long boardSeq;

    @Column(name = "category_id")
    private long categoryId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", length = 2000)
    private String content;

    @Column(name = "good_count")
    private long goodCount;

    @Column(name = "state")
    private boolean state;

    @Column(name = "update_date")
    private Date updateDate;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "delete_date")
    private Date deleteDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    private Category category;
}
