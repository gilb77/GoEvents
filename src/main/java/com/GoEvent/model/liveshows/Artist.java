package com.GoEvent.model.liveshows;

import lombok.Getter;
import lombok.Setter;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Getter
@Setter
@Table(name = "ARTISTS")
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Lob
    @Column(columnDefinition="Text")
    private String description;

    @Lob
    @Column(columnDefinition="Text")
    private String review;

    @Column
    private String video;


    @Lob
    @Column(name = "image", columnDefinition="BLOB")
    private byte[] image;

}
