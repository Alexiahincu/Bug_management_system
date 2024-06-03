package org.example.jsonprotocol;

import com.google.gson.Gson;
import org.example.Domain.Bug;
import org.example.Domain.Programmer;
import org.example.Domain.Tester;
import org.example.Service.IObserver;
import org.example.Service.IService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ChatServicesJsonProxy implements IService {
    private String host;
    private int port;

    private IObserver client;

    private BufferedReader input;
    private PrintWriter output;
    private Gson gsonFormatter;
    private Socket connection;

    private BlockingQueue<Response> qresponses;
    private volatile boolean finished;
    public ChatServicesJsonProxy(String host, int port) {
        this.host = host;
        this.port = port;
        qresponses=new LinkedBlockingQueue<Response>();
    }

     @Override
    public Tester tLogin(String username, String password, IObserver client) throws Exception {
        initializeConnection();

        Request request = JsonProtocolUtils.createTLoginRequest(username, password);
        sendRequest(request);
        Response response = readResponse();
        if (response.getType()== ResponseType.OK){
            this.client=client;
            return response.getTester();
        }
        if (response.getType()== ResponseType.ERROR){
            String err=response.getErrorMessage();;
            closeConnection();
            throw new Exception(err);
        }
        return null;
    }

    @Override
    public Programmer pLogin(String username, String password, IObserver iobs) throws Exception {
        initializeConnection();

        Request request = JsonProtocolUtils.createPLoginRequest(username, password);
        sendRequest(request);
        Response response = readResponse();
        if (response.getType()== ResponseType.OK){
            this.client=iobs;
            return response.getProgrammer();
        }
        if (response.getType()== ResponseType.ERROR){
            String err=response.getErrorMessage();;
            closeConnection();
            throw new Exception(err);
        }
        return null;
    }

    @Override
    public List<Bug> getAllBugs() throws Exception {
        Request req= JsonProtocolUtils.createGetAllBugsRequest();
        sendRequest(req);
        Response response=readResponse();

        if (response.getType()== ResponseType.ERROR){
            String err=response.getErrorMessage();;
            closeConnection();
            throw new Exception(err);
        }

        this.client = client;
        return response.getBugs();

    }

    @Override
    public void AddBug(String name, String description) throws Exception {
        Request req= JsonProtocolUtils.createAddBugRequest(name, description);
        sendRequest(req);
        Response response=readResponse();
        if (response.getType()== ResponseType.OK){
//            this.client=client;
        }
        if (response.getType()== ResponseType.ERROR){
            String err=response.getErrorMessage();
            closeConnection();
            throw new Exception(err);
        }
    }

    @Override
    public void SolveBug(Integer id) throws Exception {
        Request req= JsonProtocolUtils.createSolveBugRequest(id);
        sendRequest(req);
        Response response=readResponse();
        if (response.getType()== ResponseType.OK){
//            this.client=client;
        }
        if (response.getType()== ResponseType.ERROR){
            String err=response.getErrorMessage();
            closeConnection();
            throw new Exception(err);
        }
    }
    

//    public void logout(Angajat Angajat, IObserver client) throws Exception {
//
//        Request req = JsonProtocolUtils.createLogoutRequest(Angajat);
//        sendRequest(req);
//       Response response = readResponse();
//        closeConnection();
//        if (response.getType() == ResponseType.ERROR){
//            String err = response.getErrorMessage();
//            throw new Exception(err);
//        }
//    }
    

    private void closeConnection() {
        finished=true;
        try {
            input.close();
            output.close();
            connection.close();
            client=null;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void sendRequest(Request request)throws Exception {
        String reqLine=gsonFormatter.toJson(request);
        try {
            output.println(reqLine);
            output.flush();
        } catch (Exception e) {
            throw new Exception("Error sending object "+e);
        }

    }

    private Response readResponse() throws Exception {
       Response response=null;
        try{
            response=qresponses.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }
    private void initializeConnection() throws Exception {
        try {
            gsonFormatter=new Gson();
            connection=new Socket(host,port);
            output=new PrintWriter(connection.getOutputStream());
            output.flush();
            input=new BufferedReader(new InputStreamReader(connection.getInputStream()));
            finished=false;
            startReader();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void startReader(){
        Thread tw=new Thread(new ReaderThread());
        tw.start();
    }


    private void handleUpdate(Response response){
        client.update();
    }

    private boolean isUpdate(Response response){
         return response.getType()== ResponseType.UPDATE;
//        return false;
    }


    private class ReaderThread implements Runnable{
        public void run() {
            while(!finished){
                try {
                    String responseLine=input.readLine();
                    System.out.println("response received "+responseLine);
                    Response response=gsonFormatter.fromJson(responseLine, Response.class);
                    if (isUpdate(response)){
                        handleUpdate(response);
                    }else{

                        try {
                            qresponses.put(response);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Reading error "+e);
                }
            }
        }
    }
}
