import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Main {

    private static int port;
    private static ServerSocket serverSocket;

    public static void main(String[] args) {
        try {
            port = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("Use integer port as the first argument");
            System.exit(1);
        }

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println("Port " + port + " is blocked.");
            System.exit(2);
        }

        for (; ; ) {
            try {
                Socket clientSocket = serverSocket.accept();
                new Thread(new Worker(clientSocket)).start();
            } catch (IOException e) {
                System.out.println("Failed to establish connection");
                System.out.println(e.getMessage());
                System.exit(3);
            }
        }
    }
}
