package main;

import com.beust.jcommander.JCommander;
import utils.argumentParser.Parser;
import utils.files.FileReader;
import utils.files.Type;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;


public class Main {
    private static ServerSocket serverSocket;
    private static int sleepTime = 10;
    public static AtomicInteger threadsCount = new AtomicInteger(0);

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
                for (; ; ) {
                    if (threadsCount.get() < parser.getThreadsNum()) break;
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                        System.out.println("thread was interrupted");
                    }
                }
                Socket clientSocket = serverSocket.accept();
                new Thread(new Worker(clientSocket)).start();
                threadsCount.incrementAndGet();
            } catch (IOException e) {
                System.out.println("Failed to create connection");
                System.out.println(e.getMessage());
                System.exit(3);
            }
        }
    }
}
