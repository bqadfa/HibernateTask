package entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@SQLDelete(sql = "UPDATE Roles SET enabled = NOT enabled where id = ?")
public class Roles {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column
    private String nameOfRole;

    @Column(columnDefinition = "bool default true")
    private Boolean enabled;

    /*@ManyToMany
    private Set<User> users;*/

    public Roles(String name/*, Set<User> setOfUsers*/) {
        nameOfRole = name;
       /* users = setOfUsers;*/
    }
}
