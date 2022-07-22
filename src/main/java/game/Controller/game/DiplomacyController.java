package game.Controller.game;

import game.Model.Civilization;
import game.Model.DiplomacyRequest;

import java.util.ArrayList;

public class DiplomacyController {

    private static final ArrayList<DiplomacyRequest> diplomacyRequests = new ArrayList<>() ;

    public static void addDiplomacyRequest (DiplomacyRequest diplomacyRequest){
        diplomacyRequests.add(diplomacyRequest);
    }

    public static void removeDiplomacy (DiplomacyRequest diplomacyRequest){
        diplomacyRequests.remove(diplomacyRequest);
    }

    public static ArrayList<DiplomacyRequest> getDiplomacyRequests() {
        return diplomacyRequests;
    }


    /**
     * returns diplomacy requests sent by this civilization
     * @param civilization this civilization
     * @return diplomacy requests sent by this civilization
     */
    public static ArrayList<DiplomacyRequest> getDiplomacyRequestsOfCivilization (Civilization civilization){
        ArrayList<DiplomacyRequest> out = new ArrayList<>();
        for (DiplomacyRequest diplomacyRequest : diplomacyRequests) {
            if (diplomacyRequest.getSender().equals(civilization))
                out.add(diplomacyRequest);
        }
        return out ;
    }

    /**
     * returns diplomacy requests sent to this civilization
     * @param civilization this civilization
     * @return diplomacy requests sent to this civilization
     */
    public static ArrayList<DiplomacyRequest> getDiplomacyRequestsForCivilization (Civilization civilization){
        ArrayList<DiplomacyRequest> out = new ArrayList<>();
        for (DiplomacyRequest diplomacyRequest : diplomacyRequests) {
            if (diplomacyRequest.getReceiver().equals(civilization))
                out.add(diplomacyRequest);
        }
        return out ;
    }
}
