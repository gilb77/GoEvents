package com.GoEvent.model.movies;


import com.GoEvent.model.Invite;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "MovieInvite")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieInvite extends Invite {

    @OneToOne(cascade = CascadeType.ALL)
    private MovieEvent movieEvent;

    @Column
    private int seat;

}
