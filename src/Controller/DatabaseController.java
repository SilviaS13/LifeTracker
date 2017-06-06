package Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import Model.*;

//Nata's stuff
public class DatabaseController {
    //Db provides data, main controller turns into json?
    //GET
    //если запись по айдишнику не найдена - возвращает null
    // JDBC URL, username and password of MySQL server
    private static volatile DatabaseController instance;

    static final String url = "jdbc:mysql://localhost:3306/LifeTracker";
    static final String user = "root";
    static final String password = "";
    // JDBC variables for opening and managing connection
    static Connection con;
    static Statement stmt;
    static ResultSet rs;
    static int rsIUD;
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public static String query = "";

    public static DatabaseController getInstance() {
        DatabaseController localInstance = instance;
        if (localInstance == null) {
            synchronized (DatabaseController.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new DatabaseController();
                    instance.DatabaseConnect();
                }
            }
        }
        return localInstance;
    }

    public void DatabaseConnect(){
        // opening database connection to MySQL server
        try {
            con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // getting Statement object to execute query

    }

    //всякие трайкэтчи и проверки на адекватность параметров должны быть в MainController, а не здесь
    public  Auth GetAuths(String userid){
        Auth auth = new Auth();
        query = "SELECT Pass FROM User WHERE id="+userid+";";
        try {
            rs = stmt.executeQuery(query);while(rs.next()){
                auth.Password = rs.getString("Pass");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return auth;
    }

    public  Auth[] GetAuths() {
        Auth res = new Auth();
        ArrayList<Auth> auts = new ArrayList<Auth>();
        query = "SELECT Pass FROM Auth;";
        try {
            rs = stmt.executeQuery(query);
            while(rs.next()){
                res.Password = rs.getString("Pass");
                auts.add(res);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Auth[] a = new Auth[auts.size()];
        return auts.toArray(a);
    }

    public  User GetUser(String userid) {
        User res = new User("Silvia");
        res.FriendsId = new Long[] {123L, 358L};
        return res;
    } //yep, like this
    public  Activity GetActivity(String activityid) {
        Activity res = new Activity();
        query = "SELECT Name FROM Activity WHERE id="+activityid+";";
        try{
            rs = stmt.executeQuery(query);
            while(rs.next()){
                res.Name = rs.getString("Name");
                res.Id = Long.parseLong(activityid);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return res;
    }
    public  Activity[] GetActivities(String userid) {
        Activity res = new Activity();
        ArrayList<Activity> acts = new ArrayList<Activity>();
        query = "SELECT Name FROM Activity WHERE id_User="+userid+";";
        try {
            rs = stmt.executeQuery(query);
            while(rs.next()){
                res.Name = rs.getString("Name");
                res.UserId = Long.parseLong(userid);
                acts.add(res);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Activity[] a = new Activity[acts.size()];
        return acts.toArray(a);
    }
    public  Check GetCheck(String checkid) {
        Check res = new Check(Long.parseLong(checkid));
        query = "SELECT Date FROM Check WHERE id="+checkid+";";
        try {
            rs = stmt.executeQuery(query);
            while(rs.next()){
                res.Date = LocalDateTime.parse(rs.getString("Date"), formatter);
            }
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }        
        return res;
    }
    public  Check[] GetChecks(String activityid) {
        Check res = new Check(Long.parseLong(activityid));
        ArrayList<Check> chks = new ArrayList<Check>();
        query = "SELECT Date FROM Check WHERE id_Activity="+activityid+";";
        try {
            rs = stmt.executeQuery(query);
            while(rs.next()){
                res.Date = LocalDateTime.parse(rs.getString("Date"), formatter);
                res.ActivityId = Long.parseLong(activityid);
                chks.add(res);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Check[] c = new Check[chks.size()];
        return chks.toArray(c);
    }
    public  Badge[] GetBadges() {
        Badge res = new Badge(" ",0);
        ArrayList<Badge> badges = new ArrayList<Badge>();
        query = "SELECT Name, Points FROM Badge;";
       try {
           rs = stmt.executeQuery(query);
           while(rs.next()){
               res.Name = rs.getString("Name");
               res.Points = rs.getInt("Points");
               badges.add(res);
           }
       }
       catch (SQLException e) {
           e.printStackTrace();
       }
        Badge[] b = new Badge[badges.size()];
        return badges.toArray(b);
    }

    //POST (new)
    public  void PostAuth(Auth gotten) {
        query = "INSERT INTO User (id, Name, Pass) VALUES ("+ gotten.Id+", "+gotten.Name+ ", "+gotten.Password+");";
        try {
            rsIUD = stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public  void PostUser(User user) {
        query = "INSERT INTO User (Lvl, Xp) VALUES ("+ user.Level+", "+user.Experience+ ") WHERE id ="+user.Id+";";
        try {
            rsIUD = stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public  void PostActivity(Activity activity) {
        query = "INSERT INTO Activity (id, id_User, Name) VALUES ("+ activity.Id+", "+activity.UserId+ ", "+activity.Name+");";
        try {
            rsIUD = stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public  void PostCheck(Check check) {
        query = "INSERT INTO Check (id, id_Activity, Date) VALUES ("+ check.Id+", "+check.ActivityId+ ", "+check.Date+");";
        try {
            rsIUD = stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public  void PostFriend(Long id, Long friendId) {
        query = "INSERT INTO Followers (id, id_UserWatcher, id_UserWatching) VALUES ("+ id+", "+friendId+ ");";
        try {
            rsIUD = stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //PUT (update)
    public  void PutAuth(Auth auth) {
        query = "UPDATE User SET (Name, Pass) VALUES ( "+auth.Name+ ", "+auth.Password+") WHERE id="+ auth.Id+";";
        try {
            rsIUD = stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public  void PutUser(User user) {
        query = "";//!
        try {
            rsIUD = stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public  void PutActivity(Activity activity) {
        query = "UPDATE Activity SET (id_User, Name) VALUES ("+ activity.Id+", "+activity.UserId+ ") WHERE id="+ activity.UserId+";";
        try {
            rsIUD = stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //DELETE
    public  void DeleteUser(String userid) {
        query = "DELETE FROM User WHERE id="+ userid+";";
        try {
            rsIUD = stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public  void DeleteActivity(String activityid) {
        query = "DELETE FROM Activity WHERE id="+ activityid+";";
        try {
            rsIUD = stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public  void DeleteFriend(Long id, Long friendId) {
        query = "DELETE FROM Followers WHERE id_UserWatcher="+ id+", id_UserWatching="+friendId+ ";";
        try {
            rsIUD = stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public  void CloseConnection(){
        try { con.close(); } catch(SQLException se) { /*can't do anything */ }
        try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
    }


}