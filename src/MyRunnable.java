import java.io.*;
import java.net.Socket;
import java.util.Arrays;

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

            File dir = new File("C:\\Users\\MSI\\Google Диск\\ДЗ\\2 курс\\физика\\lab");

            //noinspection InfiniteLoopStatement
            while (true) {
                line = dataInputStream.readUTF();

                System.out.println(line);
                System.out.println(this.getName() + " sending answer...");

                if (line.equals("exit")) {
                    System.out.println("end Thread");
                    break;
                }
                if (line.equals("ls")) {
                    line = Arrays.toString(dir.list());
                    System.out.println(line);
                }


                dataOutputStream.writeUTF(line);
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
