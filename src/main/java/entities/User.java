package entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Users")
@SQLDelete(sql = "UPDATE Users SET enabled = NOT enabled where id = ?")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;


    private String nameOfUser;


    private String lastNameOfUser;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String username;

    private Date createdDate;

    @Column(columnDefinition = "bool default true")
    private Boolean enabled;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinTable(name = "users_roles", joinColumns = {@JoinColumn(name = "users_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Roles> roles;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "tasks_id")
    private Set<Tasks> tasks;

    @ManyToOne
    private Discipline discipline;

    public User(String name, String surname, String email, String username, Date createdDate, Boolean enabled, HashSet<Roles> roles/*,
                HashSet<Tasks> tasks, Discipline discipline*/) {
        nameOfUser = name;
        lastNameOfUser = surname;
        this.email = email;
        this.username = username;
        this.createdDate = createdDate;
        this.enabled = enabled;
        this.roles = roles;
        /*this.tasks = tasks;*/
        /*this.discipline = discipline;*/
    }

    /*public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }

    public User(Discipline discipline) {
        this.discipline = discipline;
    }*/
}
