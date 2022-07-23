package game.Controller.game;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import game.Model.Civilization;
import game.Model.DiplomacyRequest;
import game.Model.User;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DiplomacyController {

    public static ArrayList<DiplomacyRequest> getDiplomacyRequests() {
        ArrayList<DiplomacyRequest> diplomacyRequests = new ArrayList<>();
        for (Civilization civilization : GameController.getInstance().getCivilizations())
            diplomacyRequests.addAll(civilization.getDiplomacyRequests()) ;
        return diplomacyRequests;
    }

    public static void addDiplomacy(DiplomacyRequest diplomacyRequest){
        diplomacyRequest.getSender().addDiplomacyRequest(diplomacyRequest);
        diplomacyRequest.getReceiver().addDiplomacyRequest(diplomacyRequest);
    }

    public static void removeDiplomacy (DiplomacyRequest diplomacyRequest){
        diplomacyRequest.getSender().removeDiplomacyRequest(diplomacyRequest);
        diplomacyRequest.getReceiver().removeDiplomacyRequest(diplomacyRequest);
    }

    /**
     * returns diplomacy requests sent by this civilization
     * @param civilization this civilization
     * @return diplomacy requests sent by this civilization
     */
    public static ArrayList<DiplomacyRequest> getDiplomacyRequestsOfCivilization (Civilization civilization){
        ArrayList<DiplomacyRequest> out = new ArrayList<>();
        for (DiplomacyRequest diplomacyRequest : getDiplomacyRequests()) {
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
        for (DiplomacyRequest diplomacyRequest : getDiplomacyRequests()) {
            if (diplomacyRequest.getReceiver().equals(civilization))
                out.add(diplomacyRequest);
        }
        return out ;
    }

}
