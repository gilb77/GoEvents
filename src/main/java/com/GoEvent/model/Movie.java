package com.GoEvent.model;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.codec.binary.Base64;

import javax.persistence.*;
import java.io.File;

@Entity
@Getter
@Setter
@Data
@Table(name = "movie")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column
    private int length;

    @Column
    private String description;

    @Lob
    @Column(name = "image", columnDefinition="BLOB")
    private byte[] image;


    public  String encodePhoto(byte[] data){
       return Base64.encodeBase64String(data) ;
    }
}
