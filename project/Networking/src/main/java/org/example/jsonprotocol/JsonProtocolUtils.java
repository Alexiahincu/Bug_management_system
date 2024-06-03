package org.example.jsonprotocol;


import org.example.Domain.Bug;


import java.util.ArrayList;
import java.util.List;

public class JsonProtocolUtils {
   

    public static Response createOkResponse(){
        Response resp=new Response();
        resp.setType(ResponseType.OK);
        return resp;
    }

    public static Response createErrorResponse(String errorMessage){
        Response resp=new Response();
        resp.setType(ResponseType.ERROR);
        resp.setErrorMessage(errorMessage);
        return resp;
    }
    

    public static Request createTLoginRequest(String username, String password){
        Request req=new Request();
        req.setType(RequestType.TLOGIN);
        req.setUsername(username);
        req.setPassword(password);
        return req;
    }

   

//    public static Request createLogoutRequest(Angajat Angajat){
//        Request req=new Request();
//        req.setType(RequestType.LOGOUT);
//        req.setAngajat(DTOUtils.getDTO(Angajat));
//        return req;
//    }

    public static Request createGetAllBugsRequest(){
        Request req=new Request();
        req.setType(RequestType.GET_BUGS);
        return req;
    }


//    public static Request createBuyTicketRequest(String clientId, String clientName, String angajatId, String concertId, int seatsNumber){
//        Request req=new Request();
//        req.setType(RequestType.BUY_TICKET);
//
//        req.setClientId(clientId);
//        req.setClientName(clientName);
//        req.setAngajatId(angajatId);
//        req.setConcertId(concertId);
//        req.setSeatsNumber(seatsNumber);
//
//        return req;
//    }


    public static Response createGetAllBugsResponse(List<Bug> bugs){
        Response resp=new Response();
        resp.setType(ResponseType.OK);
        resp.setBugs(bugs);
        return resp;
    }

    public static Request createPLoginRequest(String username, String password) {
        Request req = new Request();
        req.setType(RequestType.PLOGIN);
        req.setUsername(username);
        req.setPassword(password);
        return req;
    }

    public static Request createAddBugRequest(String name, String description) {
        Request req = new Request();
        req.setType(RequestType.ADD_BUG);
        req.setName(name);
        req.setDesc(description);
        return req;
    }

    public static Request createSolveBugRequest(Integer id) {
        Request req = new Request();
        req.setType(RequestType.SOLVE_BUG);
        req.setId(id);
        return req;
    }
}
