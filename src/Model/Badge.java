package Model;

import Controller.DatabaseController;

public class Badge {
    public String Name;
    public int Points;

    public static Badge[] Badges;

    public Badge(String name, int points) {
        Name = name;
        Points = points;
    }

    public static void LoadBadges()
    {
        Badges = DatabaseController.GetBadges();
    }
}
