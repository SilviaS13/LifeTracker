package Model;

import java.time.LocalDateTime;

public class User extends Identified{
    //idgaf about a smell of this
    public String Name;

    public int Level = 1;
    public int Experience = 0;
    public LocalDateTime[] Badges = new LocalDateTime[Badge.Badges.length];

    public Long[] FriendsId;

    public User(String name) {
        GenerateId();
        Name = name;
    }
}
