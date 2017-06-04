package Controller;

import Model.*;
import com.google.gson.Gson;
import Service.HttpResponse;

import java.util.Base64;
import java.util.HashMap;

public class MainController {
    static String auth;
    static HashMap<String, String> tokens = new HashMap<>();

    static Gson g = new Gson();
    //GET
    public static HttpResponse GetUser(String[] params) { //params = {userid}
        try {
            User gotten = DatabaseController.GetUser(params[0]);
            if (gotten == null) {
                return new HttpResponse(404);
            }
            String res = g.toJson(gotten);
            return new HttpResponse(200, res);
        }
        catch(Exception ex)
        {
            return new HttpResponse(400);
        }
    }
    public static HttpResponse GetActivity(String[] params) { //params = {userid???, activityid}
        try {
            Activity gotten = DatabaseController.GetActivity(params[1]);
            if (gotten == null) {
                return new HttpResponse(404);
            }
            String res = g.toJson(gotten);
            return new HttpResponse(200, res);
        }
        catch(Exception ex)
        {
            return new HttpResponse(400);
        }
    }
    public static HttpResponse GetActivities(String[] params) { //params = {userid}
        try {
            Activity[] gotten = DatabaseController.GetActivities(params[0]);
            if (gotten == null) {
                return new HttpResponse(404);
            }
            String res = g.toJson(gotten);
            return new HttpResponse(200, res);
        }
        catch(Exception ex)
        {
            return new HttpResponse(400);
        }
    }
    public static HttpResponse GetCheck(String[] params) { //params = {userid???, activityid???, checkid}
        try {
            Check gotten = DatabaseController.GetCheck(params[2]);
            if (gotten == null) {
                return new HttpResponse(404);
            }
            String res = g.toJson(gotten);
            return new HttpResponse(200, res);
        }
        catch(Exception ex)
        {
            return new HttpResponse(400);
        }
    }
    public static HttpResponse GetChecks(String[] params) { //params = {userid???, activityid}
        try {
            Check[] gotten = DatabaseController.GetChecks(params[1]);
            if (gotten == null) {
                return new HttpResponse(404);
            }
            String res = g.toJson(gotten);
            return new HttpResponse(200, res);
        }
        catch(Exception ex)
        {
            return new HttpResponse(400);
        }
    }

    //not done
    public static HttpResponse GetFriends(String[] params) {
        return new HttpResponse(400, "Not yet implemented :(");
    }
    public static HttpResponse GetSimilar(String[] params) {
        return new HttpResponse(400, "Not yet implemented :(");
    }

    public static HttpResponse PublishActivity(String[] params) {
        return new HttpResponse(400, "NI");
    }
    public static HttpResponse AuthoriseUser(String[] params) {
        return new HttpResponse(400, "NI");
    }

    //POST
    public static HttpResponse PostUser(String[] params) { //params = {body}
        try {
            Auth gotten = g.fromJson(params[0], Auth.class);
            User user = new User(gotten.Name);
            gotten.Id = user.Id;
            DatabaseController.PostAuth(gotten);
            DatabaseController.PostUser(user);
            return new HttpResponse(201);
        }
        catch(Exception ex)
        {
            return new HttpResponse(409);
        }
    }
    public static HttpResponse PostActivity(String[] params) { //params = {userid, body}
        try {
            Activity gotten = g.fromJson(params[1], Activity.class);
            gotten.UserId = Long.parseLong(params[0]); //?
            DatabaseController.PostActivity(gotten);
            return new HttpResponse(201);
        }
        catch(Exception ex)
        {
            return new HttpResponse(409);
        }
    }
    public static HttpResponse PostCheck(String[] params) { //params = {userid???, activityid, body}
        try {
            Check gotten = g.fromJson(params[2], Check.class);
            gotten.ActivityId = Long.parseLong(params[1]); //?
            DatabaseController.PostCheck(gotten);
            return new HttpResponse(201);
        }
        catch(Exception ex)
        {
            return new HttpResponse(409);
        }
    }

    //PUT
    public static HttpResponse PutUser(String[] params) { //params = {body}
        try {
            User gotten = g.fromJson(params[0], User.class);
            User prev = DatabaseController.GetUser(gotten.Id.toString());
            if (gotten.FriendsId != prev.FriendsId) {
                int i = gotten.FriendsId.length - 1;
                while (i > prev.FriendsId.length - 1) {
                    DatabaseController.PostFriend(gotten.Id, gotten.FriendsId[i]);
                    i--;
                }
            }
            DatabaseController.PutUser(gotten);
            return new HttpResponse(201);
        }
        catch(Exception ex)
        {
            return new HttpResponse(400);
        }
    }
    public static HttpResponse PutActivity(String[] params) { //params = {userid, body}
        try {
            Activity gotten = g.fromJson(params[1], Activity.class);
            gotten.UserId = Long.parseLong(params[0]); //?
            DatabaseController.PutActivity(gotten);
            return new HttpResponse(201);
        }
        catch(Exception ex)
        {
            return new HttpResponse(400);
        }
    }

    //DELETE
    public static HttpResponse DeleteUser(String[] params) { //params = {userid}
        try {
            DatabaseController.DeleteUser(params[0]);
            return new HttpResponse(200);
        }
        catch(Exception ex)
        {
            return new HttpResponse(400);
        }
    }
    public static HttpResponse DeleteActivity(String[] params) { //params = {userid???, activityid
        try {
            DatabaseController.DeleteActivity(params[1]);
            return new HttpResponse(200);
        }
        catch(Exception ex)
        {
            return new HttpResponse(400);
        }
    }


    public static void GetTokens() {
        Auth[] auths = DatabaseController.GetAuths();
        for (Auth auth : auths) {
            tokens.put(Long.toString(auth.Id), Base64.getEncoder().encodeToString(auth.Password.getBytes()));
        }
    }
    public static boolean CheckAuth(String s) {
        return auth.equals(tokens.get(s));
    }
    public static void SetAuth(String s) {
        auth = s;
    }
}
