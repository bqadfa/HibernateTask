package entities;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Discipline {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column
    private String name;

   /* @EqualsAndHashCode.Exclude
    @ToString.Exclude*/
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "discipline_id")
    private Set<User> users;

    @OneToOne
    @JoinColumn
    private User headOfDiscipline;

    public Discipline(String nameOfDiscipline, HashSet<User> members, User headOfDiscipline) {
        name = nameOfDiscipline;
        users = members;
        this.headOfDiscipline = headOfDiscipline;
    }

    public void setHeadOfDiscipline(User headOfDiscipline) {
        this.headOfDiscipline = headOfDiscipline;
    }

    public String getName() {
        return name;
    }
}
