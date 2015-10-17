import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Worker implements Runnable {

    private Socket socket;
    private InputStream in;
    private OutputStream out;

    public Worker(Socket socket) throws IOException {
        this.socket = socket;
        in = socket.getInputStream();
        out = socket.getOutputStream();
    }

    public void run() {

    }
}
