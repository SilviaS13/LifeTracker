package Service;

import java.util.HashMap;

import static Controller.MainController.*;

public class UriTree {
    public static Node top;

    public static HashMap<String, Integer> RequestMethod = new HashMap<String, Integer>(){{
        put("GET",0);
        put("POST",1);
        put("PUT", 2);
        put("DELETE", 3);
    }};

    public UriTree() {
        top = new Node("", null);
        Node first = new Node("api", new ProcessRequest[]{
                null,
                (params) -> PostUser(params),
                null,
                null
        });
        top.Subnodes.add(first);
        //user
        Node user = new Node("_userid", new ProcessRequest[]{
                (params) -> GetUser(params),
                (params) -> PostActivity(params),
                (params) -> PutUser(params),
                (params) -> DeleteUser(params)
        });
        first.Subnodes.add(user);
        /*
        //panel
        Node panel = new Node("_panelid", new ProcessRequest[]{
                (params) -> GetPanel(params),
                (params) -> PostPanel(params),
                (params) -> PutPanel(params),
                (params) -> DeletePanel(params)
        });
        user.Subnodes.add(panel);
        //-
        user.Subnodes.add(new Node("panels", new ProcessRequest[]{
                (params) -> GetPanels(params)
                //null,
                //null,
                //null
        }));*/
        //activity
        Node activity = new Node("_activityid", new ProcessRequest[]{
                (params) -> GetActivity(params),
                (params) -> PostCheck(params),
                (params) -> PutActivity(params),
                (params) -> DeleteActivity(params)
        });
        user.Subnodes.add(activity);
        //-
        user.Subnodes.add(new Node("activities", new ProcessRequest[]{
                (params) -> GetActivities(params)
        }));
        user.Subnodes.add(new Node("friends", new ProcessRequest[]{
                (params) -> GetFriends(params)
        }));
        user.Subnodes.add(new Node("similar", new ProcessRequest[]{
                (params) ->GetSimilar(params)
        }));
        //check
        Node check = new Node("_checkid", new ProcessRequest[]{
                (params) -> GetCheck(params)
        });
        activity.Subnodes.add(check);
        //-
        activity.Subnodes.add(new Node("checks", new ProcessRequest[]{
                (params) -> GetChecks(params)
        }));

        /*
        current version of a tree:
        .
        ____-userid
        ____________activities
        ____________friends
        ____________similar
        ____________-activityid
        ________________________checks
        ________________________-checkid

         */
    }
}
