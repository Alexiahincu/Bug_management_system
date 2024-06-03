package org.example.jsonprotocol;

import com.google.gson.Gson;
import org.example.Domain.Bug;
import org.example.Domain.Tester;
import org.example.Service.IObserver;
import org.example.Service.IService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatClientJsonWorker implements Runnable, IObserver {
    private IService server;
    private Socket connection;

    private BufferedReader input;
    private PrintWriter output;
    private Gson gsonFormatter;
    private volatile boolean connected;
    public ChatClientJsonWorker(IService server, Socket connection) {
        this.server = server;
        this.connection = connection;
        gsonFormatter=new Gson();
        try{
            output=new PrintWriter(connection.getOutputStream());
            input=new BufferedReader(new InputStreamReader(connection.getInputStream()));
            connected=true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while(connected){
            try {
                String requestLine=input.readLine();
                Request request=gsonFormatter.fromJson(requestLine, Request.class);
                Response response=handleRequest(request);
                if (response!=null){
                    sendResponse(response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            input.close();
            output.close();
            connection.close();
        } catch (IOException e) {
            System.out.println("Error "+e);
        }
    }

    

    private static Response okResponse=JsonProtocolUtils.createOkResponse();

    private Response handleRequest(Request request){
       Response response = new Response();
        if (request.getType()== RequestType.TLOGIN){
            System.out.println("Tester login request ... "+request.getType());

            String username = request.getUsername();
            String password = request.getPassword();
            try {
                response.setType(ResponseType.OK);
                response.setTester(server.tLogin(username, password, this));
//                if(!angajat1.getUsername().equals(angajat.getUsername()) || !angajat1.getPassword().equals(angajat.getPassword()))
//                    throw new Exception();
                return response;
            } catch (Exception e) {
                connected=false;
                return JsonProtocolUtils.createErrorResponse(e.getMessage());
            }
        }

        if (request.getType()== RequestType.PLOGIN){
            System.out.println("Programmer login request ... "+request.getType());

            String username = request.getUsername();
            String password = request.getPassword();
            try {
                response.setType(ResponseType.OK);
                response.setProgrammer(server.pLogin(username, password, this));
//                if(!angajat1.getUsername().equals(angajat.getUsername()) || !angajat1.getPassword().equals(angajat.getPassword()))
//                    throw new Exception();
                return response;
            } catch (Exception e) {
                connected=false;
                return JsonProtocolUtils.createErrorResponse(e.getMessage());
            }
        }
//        if (request.getType() == RequestType.LOGOUT){
//            System.out.println("Logout request");
//
//            AngajatDTO udto=request.getAngajat();
//            Angajat Angajat=DTOUtils.getFromDTO(udto);
//            try {
//                server.logout(Angajat, this);
//                return okResponse;
//
//            } catch (Exception e) {
//                connected=false;
//                return JsonProtocolUtils.createErrorResponse(e.getMessage());
//            }
//        }
        if (request.getType() == RequestType.GET_BUGS){
            System.out.println("Get bugs request");

            try {
                //return okResponse;
                List<Bug> bugs = server.getAllBugs();
                return JsonProtocolUtils.createGetAllBugsResponse(bugs);
            } catch (Exception e) {
                connected=false;
                return JsonProtocolUtils.createErrorResponse(e.getMessage());
            }
        }

        if (request.getType() == RequestType.ADD_BUG){
            System.out.println("Add bug request");

            try {
                server.AddBug(request.getName(), request.getDesc());
                //connected=false;
                return okResponse;
            } catch (Exception e) {
                connected=false;
                return JsonProtocolUtils.createErrorResponse(e.getMessage());
            }
        }

        if (request.getType() == RequestType.SOLVE_BUG){
            System.out.println("Solve bug request");

            try {
                server.SolveBug(request.getId());
                //connected=false;
                return okResponse;
            } catch (Exception e) {
                connected=false;
                return JsonProtocolUtils.createErrorResponse(e.getMessage());
            }
        }

        if (request.getType() == RequestType.UPDATE) {
           update();
        }

        return response;
    }

    private void sendResponse(Response response) throws IOException{
        String responseLine=gsonFormatter.toJson(response);
        System.out.println("sending response "+responseLine);
        synchronized (output) {
            output.println(responseLine);
            output.flush();
        }
    }

    @Override
    public void update() {
        try {
            Response response = new Response();
            response.setType(ResponseType.UPDATE);
            sendResponse(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
