package main;

import utils.Methods;
import utils.Request;
import utils.exceptions.BadRequest;
import utils.exceptions.WrongMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class Worker implements Runnable {
    private Socket socket;
    private BufferedReader in;
    private OutputStream out;
    private StringBuilder request = new StringBuilder();

    public Worker(Socket socket) throws IOException {
        this.socket = socket;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = socket.getOutputStream();
    }

    public void run() {
        try {
            checkRequest();
        } catch (WrongMethod ex) {
            //TODO
        } catch (BadRequest exc) {
            //todo
        }

    }

    private Request checkRequest() throws WrongMethod, BadRequest {
        Request request = new Request();
        String temp = null;
        try {
            temp = in.readLine();
        } catch (IOException e) { /*todo*/ }

        if (temp == null) {
            throw new BadRequest();
        }

        try {
            request.method = Enum.valueOf(Methods.class, temp.substring(0, temp.indexOf(' ')).toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BadRequest();
        }

        if (request.method != Methods.GET && request.method != Methods.HEAD) {
            throw new WrongMethod();
        }

        request.path = temp.substring(temp.indexOf(' ') + 1, temp.lastIndexOf(' '));

        request.version = temp.substring(temp.lastIndexOf(' ') + 1).toUpperCase();
        if (request.version.equals("HTTP/1.0") && request.version.equals("HTTP/1.1")) {
            throw new BadRequest();
        }

        try {
            for (temp = in.readLine(); temp != null && !temp.isEmpty(); temp = in.readLine()) {
                String[] param = temp.split(": ");
                request.params.put(param[0], param[1]);
            }
        } catch (IOException e) {
            //todo
        }

        System.out.println(request.toString());

        return request;
    }
}