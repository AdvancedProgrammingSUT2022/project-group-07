package game.Common.Model.Network;

import game.Common.Enum.Network.TypeOfRequest;
import game.Common.Enum.Network.TypeOfRequestParameter;

import java.util.HashMap;

public class ClientRequest {

    private final TypeOfRequest typeOfRequest ;
    private HashMap<TypeOfRequestParameter, Object> params = new HashMap<>() ;

    public ClientRequest (TypeOfRequest typeOfRequest){
        this.typeOfRequest = typeOfRequest ;
    }

    public ClientRequest(TypeOfRequest typeOfRequest, HashMap<TypeOfRequestParameter, Object> params) {
        this.typeOfRequest = typeOfRequest;
        this.params = params;
    }

    public TypeOfRequest getTypeOfRequest() {
        return typeOfRequest;
    }

    public HashMap<TypeOfRequestParameter, Object> getParams() {
        return params;
    }
}
