import java.io.*;
import java.net.Socket;

public class MyRunnable implements Runnable {
    private Socket socket;

    MyRunnable(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        try {
            System.out.println("Someone found " + this.getName());

            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            DataInputStream dataInputStream = new DataInputStream(inputStream);
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

            String line;
            //noinspection InfiniteLoopStatement
            while (true) {
                line = dataInputStream.readUTF();
                if (line.equals("exit")) {
                    System.out.println("end Thread");
                    break;
                }

                System.out.println(line);
                System.out.println(this.getName() + " sending answer...");

                dataOutputStream.writeUTF("Your line:" + line + "; My answer: Cool Server!");
                dataOutputStream.flush();

                System.out.println(this.getName() + " waiting for next line...");
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String getName() {
        return Thread.currentThread().getName();
    }
}
