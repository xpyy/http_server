package main;

import utils.enums.Methods;
import utils.enums.Status;
import utils.exceptions.*;
import utils.files.File;
import utils.files.FileReader;
import utils.files.Type;
import utils.network.Request;
import utils.network.Response;

import java.io.*;
import java.net.Socket;
import java.net.URLDecoder;
import java.time.ZoneId;

public class Worker implements Runnable {
    private BufferedReader in;
    private OutputStream out;
    private Request request = new Request();
    private Response response = new Response();
    private File file;

    public Worker(Socket socket) throws IOException {
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = socket.getOutputStream();
    }

    public void run() {
        try {
            checkRequest();
        } catch (WrongMethod e) {
            createResponse(e);
            return;
        } catch (BadRequest e) {
            createResponse(e);
            return;
        }
        try {
            file = FileReader.readFile(request.path);
        } catch (BadRequest e) {
            createResponse(e);
            return;
        } catch (Forbidden e) {
            createResponse(e);
            return;
        } catch (NotFound e) {
            createResponse(e);
            return;
        } catch (UnsupportedMediaType e) {
            createResponse(e);
            return;
        }
        createResponse(file);
        sendResponse(Status.OK);
    }

    private void checkRequest() throws WrongMethod, BadRequest {
        String temp = null;
        try {
            temp = in.readLine();
        } catch (IOException e) {
            System.out.println("IOException");
        }

        if (temp == null) {
            throw new BadRequest("Empty request");
        }

        try {
            request.method = Enum.valueOf(Methods.class, temp.substring(0, temp.indexOf(' ')).toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new WrongMethod("Wrong method");
        }

        if (!request.method.equals(Methods.GET) && !request.method.equals(Methods.HEAD)) {
            throw new WrongMethod("Wrong not allowed");
        }

        try {
            request.path = URLDecoder.decode(temp.substring(temp.indexOf(' ') + 1, temp.lastIndexOf(' ')), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            System.out.println("UnsupportedEncodingException");
        }

        request.version = temp.substring(temp.lastIndexOf(' ') + 1).toUpperCase();
        if (!request.version.equals("HTTP/1.0") && !request.version.equals("HTTP/1.1")) {
            throw new BadRequest("Bad version");
        }

        try {
            for (temp = in.readLine(); temp != null && !temp.isEmpty(); temp = in.readLine()) {
                String[] param = temp.split(": ");
                request.params.put(param[0], param[1]);
            }
        } catch (IOException e) {
            System.out.println("IOException");
        }
    }

    private void createResponse(File file) {
        response.type = file.type;
        response.body = file.body;
        response.date = java.time.format.DateTimeFormatter.RFC_1123_DATE_TIME.format
                (java.time.ZonedDateTime.now(ZoneId.of("GMT")));
        response.contentLength = response.body.length;
    }

    private void sendResponse(String status) {
        StringBuilder header = new StringBuilder();
        byte[] headers = header.append(String.format("%s %s\r\n", request.version, status))
                .append(String.format("Date: %s \r\n", response.date))
                .append(String.format("Server: %s\r\n", response.server))
                .append(String.format("Content-Length: %s\r\n", response.contentLength))
                .append(String.format("Content-Type: %s\r\n", response.type))
                .append(String.format("Connection: %s \r\n\r\n", response.connection)).toString().getBytes();
        try {
            out.write(headers);
            if (request.method.equals(Methods.GET)) out.write(response.body);
            out.close();
            in.close();
        } catch (IOException e) {

        }
    }

    private void createResponse(CustomException e) {
        response.type = Type.httpType.get(".html");
        response.body = e.getMessage().getBytes();
        sendResponse(e.getStatus());
    }
}