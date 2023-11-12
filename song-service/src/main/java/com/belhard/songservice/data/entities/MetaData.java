package com.belhard.songservice.data.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "metadatas")
public class MetaData {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "artist")
    private String artist;

    @Column(name = "albumn")
    private String album;

    @Column(name = "length")
    private String length;

    @Column(name = "resource_id")
    private Long resourceId;

    @Column(name = "year")
    private Integer year;
}


