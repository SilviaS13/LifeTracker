package Service;

import java.util.ArrayList;

public class Node {
    public String Name;
    public ProcessRequest[] Actions;
    public ArrayList<Node> Subnodes = new ArrayList<>();

    public Node(String name, ProcessRequest[] actions)
    {
        Name = name;
        Actions = actions;
    }
}
