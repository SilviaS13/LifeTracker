package Model;

public class Activity extends Identified {
    public Long UserId;
    public String Name;

    public Activity() {
        GenerateId();
    }

    public Activity(String name, Long userId) {
        GenerateId();
        UserId = userId;
        Name = name;
    }
}

