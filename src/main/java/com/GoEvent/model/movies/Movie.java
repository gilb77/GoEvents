package com.GoEvent.model.movies;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.tomcat.util.codec.binary.Base64;


import javax.persistence.*;


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

    public String encodePhoto(byte[] data){
        return Base64.encodeBase64String(data) ;
    }

}
