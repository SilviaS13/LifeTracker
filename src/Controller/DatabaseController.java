package Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Model.*;

//Nata's stuff
public class DatabaseController {
    //Db provides data, main controller turns into json?
    //GET
    //если запись по айдишнику не найдена - возвращает null
    // JDBC URL, username and password of MySQL server
    public static final String url = "jdbc:mysql://localhost:3306/test";
    public static final String user = "root";
    public static final String password = "root";
    // JDBC variables for opening and managing connection
    public static Connection con;
    public static Statement stmt;
    public static ResultSet rs;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static String query = "";

    DatabaseController(){
        // opening database connection to MySQL server
        con = DriverManager.getConnection(url, user, password);
        // getting Statement object to execute query
        stmt = con.createStatement();
    }

    //всякие трайкэтчи и проверки на адекватность параметров должны быть в MainController, а не здесь
    public static Auth GetAuths(String userid){
        Auth auth = new Auth();
        query = "SELECT Pass FROM User WHERE id="+userid+";";
        rs = stmt.executeQuery(query);
        while(rs.next()){
            auth.Password = rs.getString("Pass");
        }
        return Auth;
    }

    public static User GetUser(String userid) {
        User res = new User("Ardecus");
        res.FriendsId = new Long[] {123L, 358L};
        return res;
    } //yep, like this
    public static Activity GetActivity(String activityid) {
        Activity res = new Activity();
        query = "SELECT Name FROM Activity WHERE id="+activityid+";";
        rs = stmt.executeQuery(query);
        while(rs.next()){
            res.Name = rs.getString("Name");
            res.Id = Long.parseLong(activityid);
        }
        return res;
    }
    public static Activity[] GetActivities(String userid) {
        Activity res = new Activity();
        ArrayList<Activity> acts = new ArrayList<Activity>();
        query = "SELECT Name FROM Activity WHERE id_User="+userid+";";
        rs = stmt.executeQuery(query);
        while(rs.next()){
            res.Name = rs.getString("Name");
            res.UserId = Long.parseLong(userid);
            acts.add(res);
        }
        return acts.toArray();
    }
    public static Check GetCheck(String checkid) {
        Check res = new Check();
        query = "SELECT Date FROM Check WHERE id="+checkid+";";
        rs = stmt.executeQuery(query);
        while(rs.next()){
            res.Date = LocalDateTime.parse(rs.getString("Date"), formatter);
        }
        return res;
    }
    public static Check[] GetChecks(String activityid) {
        Check res = new Check();
        ArrayList<Check> chks = new ArrayList<Check>();
        query = "SELECT Date FROM Check WHERE id_Activity="+activityid+";";
        rs = stmt.executeQuery(query);

        while(rs.next()){
            res.Date = LocalDateTime.parse(rs.getString("Date"), formatter);
            res.UserId = Long.parseLong(activityid);
            chks.add(res);
        }
        return chks.toArray();
    }
    public static Badge[] GetBadges() {
        Badge res = new Badge();
        ArrayList<Badge> bages = new ArrayList<Badge>();
        query = "SELECT Name, Points FROM Badge;";
        rs = stmt.executeQuery(query);
        while(rs.next()){
            res.Name = rs.getString("Name");
            res.Points = rs.getInt("Points");
            bages.add(res);
        }
        return bages.toArray();
    }

    //POST (new)
    public  void PostAuth(Auth gotten) {
        query = "INSERT INTO User (id, Name, Pass) VALUES ("+ gotten.Id+", "+gotten.Name+ ", "+gotten.Password+");";
        rs = stmt.executeUpdate(query);
    }
    public  void PostUser(User user) {
        query = "INSERT INTO User (Lvl, Xp) VALUES ("+ user.Level+", "+user.Experience+ ") WHERE id ="+user.Id+";";
        rs = stmt.executeUpdate(query);
    }
    public  void PostActivity(Activity activity) {
        query = "INSERT INTO Activity (id, id_User, Name) VALUES ("+ activity.Id+", "+activity.UserId+ ", "+activity.Name+");";
        rs = stmt.executeUpdate(query);
    }
    public  void PostCheck(Check check) {
        query = "INSERT INTO Check (id, id_Activity, Date) VALUES ("+ check.Id+", "+check.ActivityId+ ", "+check.Date+");";
        rs = stmt.executeUpdate(query);
    }
    /*
    public  void PostFriend(Long id, Long friendId) {
        query = "INSERT INTO User (id, Name, Pass) VALUES ("+ gotten.Id+", "+gotten.Name+ ", "+gotten.Password+");";
        rs = stmt.executeUpdate(query);
    }*/

    //PUT (update)
    public  void PutUser(User user) {
        query = "UPDATE User SET (Name, Pass) VALUES ( "+user.Name+ ", "+user.Password+") WHERE id="+ user.Id+";";
        rs = stmt.executeUpdate(query);
    }
    public  void PutActivity(Activity activity) {
        query = "UPDATE Activity SET (id_User, Name) VALUES ("+ activity.Id+", "+activity.UserId+ ") WHERE id="+ user.Id+";";
        rs = stmt.executeUpdate(query);
    }

    //DELETE
    public  void DeleteUser(String userid) {
        query = "DELETE FROM User WHERE id="+ userid+";";
        rs = stmt.executeUpdate(query);
    }
    public  void DeleteActivity(String activityid) {
        query = "DELETE FROM Activity WHERE id="+ activityid+";";
        rs = stmt.executeUpdate(query);
    }
    /*
    public  void DeleteFriend(Long id, Long friendId) {

    }*/

    public static void CloseConnection(){
        try { con.close(); } catch(SQLException se) { /*can't do anything */ }
        try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
    }
}
