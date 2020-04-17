package entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Roles {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column
    private String nameOfRole;

    /*@ManyToMany
    private Set<User> users;*/

    public Roles(String name/*, Set<User> setOfUsers*/) {
        nameOfRole = name;
       /* users = setOfUsers;*/
    }
}
