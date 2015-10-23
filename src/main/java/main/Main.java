package main;

import com.beust.jcommander.JCommander;
import utils.argumentParser.Parser;
import utils.files.FileReader;
import utils.files.Type;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Main {
    private static ServerSocket serverSocket;

    public static void main(String[] args) {
        Parser parser = new Parser();
        new JCommander(parser, args);

        try {
            serverSocket = new ServerSocket(parser.getPort());
        } catch (IOException e) {
            System.out.println("Port " + parser.getPort() + " is blocked.");
            System.exit(2);
        }

        Type.setType();
        FileReader.setFilesDir(parser.getRootDir());

        for (; ; ) {
            try {
                Socket clientSocket = serverSocket.accept();
                new Thread(new Worker(clientSocket)).start();
            } catch (IOException e) {
                System.out.println("Failed to create connection");
                System.out.println(e.getMessage());
                System.exit(3);
            }
        }
    }
}
