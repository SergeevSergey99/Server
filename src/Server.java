import java.net.*;
import java.io.*;

public class Server {
    public static void main(String[] args) {
        int port = 3247;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started");
            System.out.println("Waiting for client...");

            Socket socket = serverSocket.accept();

            System.out.println("Someone found me!");

            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            DataInputStream dataInputStream = new DataInputStream(inputStream);
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

            String line;
            //noinspection InfiniteLoopStatement
            while (true) {
                line = dataInputStream.readUTF();
                System.out.println(line);
                System.out.println("sending answer...");

                dataOutputStream.writeUTF("Your line:" + line + "; My answer: Cool Server!");
                dataOutputStream.flush();

                System.out.println("Waiting for next line...");
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
