package entities;

import enums.Status;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;


@Entity
@Data
@NoArgsConstructor
public class Tasks {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column
    private String nameOfTask;

    @Column
    private String description;

    @Column
    private Date startDate;

    @Column
    private Date endDate;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    private User user;

    public Tasks(String name, String description, Date sDate, Date eDate, Status status, User user) {
        nameOfTask = name;
        this.description = description;
        startDate = sDate;
        endDate = eDate;
        this.status = status;
        this.user = user;
    }


}
