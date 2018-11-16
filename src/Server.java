import java.net.*;
import java.io.*;

public class Server {
    public static void main(String[] args) {
        int port = 3247;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
