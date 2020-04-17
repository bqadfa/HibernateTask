import com.sun.org.apache.bcel.internal.generic.LUSHR;
import dao.DAOImplementation;
import entities.Discipline;
import entities.Roles;
import entities.Tasks;
import entities.User;
import enums.Status;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class Main {

    private static DAOImplementation dao = new DAOImplementation();

    public static void checkConnectionToDB() {
        String connection = "jdbc:postgresql://localhost:5432/postgres";
        String login = "postgres";
        String password = "postgres";

        try {
            System.out.println("connecting...");

            Connection myC = DriverManager.getConnection(connection, login, password);
            System.out.println("connection success");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        List<Roles> roles = rolesFactory();
        dao.toDB(roles);
        List<User> users = usersFactory(roles);
        dao.toDB(users);
        List<Tasks> tasks = tasksFactory(users.get(0));
        dao.toDB(tasks);
        List<Discipline> disciplines = disciplineFactory(users, users.get(1));
        dao.toDB(disciplines);



        printSeparator();

        System.out.println(roles.get(0));
        printSeparator();
        List<User> lst = dao.getUserByRole("Engineer");
        for (User u : lst)
            System.out.println(u);
        printSeparator();
        System.out.println(disciplines.get(0));
        printSeparator();

        List<User> lst2 = dao.getUsersByDiscipline("Testing");
        for (User u : lst2)
            System.out.println(u);

        printSeparator();

        List<User> lst3 = dao.getUsersWithTODO();
        for (User u : lst3)
            System.out.println(u);

        printSeparator();

        dao.updateHeadOfDiscipline(users.get(2), disciplines.get(2));

        printSeparator();

        dao.deleteUser(users.get(0));
        dao.deleteUser(users.get(1));
        dao.deleteUser(users.get(2));
        dao.deleteUser(users.get(3));
    }

    public static List<Discipline> disciplineFactory(List<User> members, User user) {

        List<Discipline> list = new ArrayList<>();
        list.add(new Discipline("AM", new HashSet<User>(Arrays.asList(members.get(0), members.get(1), members.get(2))), user));
        list.add(new Discipline("Testing", new HashSet<User>(Arrays.asList(members.get(1), members.get(2))), user));
        list.add(new Discipline("Dev", new HashSet<User>(Arrays.asList(members.get(3))), user));
        return list;
    }

    public static List<Roles> rolesFactory() {
        List<Roles> list = new ArrayList<>();
        list.add(new Roles("Engineer"));
        list.add(new Roles("Project Leader"));
        list.add(new Roles("Manager"));
        return list;
    }

    public static List<Tasks> tasksFactory(User user) {

        List<Tasks> list = new ArrayList<>();
        list.add(new Tasks("Create Header", "Create header of HHTP request", new Date(2020, 04, 10),
                new Date(2020, 05, 10), Status.TODO, user));
        list.add(new Tasks("Create Body", "Create body of the site", new Date(2020, 04, 05),
                new Date(2020, 06, 10), Status.TODO, user));
        list.add(new Tasks("Delete button", "Create delete button for admins", new Date(2020, 04, 12),
                new Date(2020, 04, 14), Status.TODO, user));
        list.add(new Tasks("Do something", "Create something, stop being lazy", new Date(2020, 04, 01),
                new Date(2020, 04, 19), Status.TODO, user));
        return list;
    }



    public static List<User> usersFactory(List<Roles> roles) {
        List<User> list = new ArrayList<>();
        int i = 0;
        list.add(new User("Nichita", "Ganja", "Nichita.Ganja@endava.com", "nganja",
                new Date(2020, 3, 10), true, new HashSet<Roles>(Arrays.asList(roles.get(0)))));
        list.add(new User("Dacian", "Rusu", "Dacian.Rusu@endava.com", "drusu",
                new Date(2020, 3, 9), true, new HashSet<Roles>(Arrays.asList(roles.get(1)))));
        list.add(new User("Alex", "Cuciuc", "Alex.Cuciuc@endava.com", "acuciuc",
                new Date(2020, 3, 12), true, new HashSet<Roles>(Arrays.asList(roles.get(1)))));
        list.add(new User("Vladislav", "Seremet", "Vlad.Seremet@endava.com", "vseremet",
                new Date(2020, 3, 1), true, new HashSet<Roles>(Arrays.asList(roles.get(2)))));
        return list;
    }

    public static void printSeparator(){
        System.out.println("----------------------------------------------------------------");
    }
}
