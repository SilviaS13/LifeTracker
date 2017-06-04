package Model;

import Controller.DatabaseController;

public class Auth extends Identified{
    public String Name;
    public String Password;

    public Auth(String name, String password) {
        GenerateId();
        Name = name;
        Password = password;
    }

    public Auth(Long id, String password) {
        Id = id;
        Password = password;
        User user = DatabaseController.GetUser(Id.toString());
        Name = user.Name;
    }
}
