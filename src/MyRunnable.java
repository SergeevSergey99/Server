import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
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
            File dir = new File(".");

            //noinspection InfiniteLoopStatement
            while (true) {
                line = dataInputStream.readUTF();

                System.out.println("command: " + line);
                System.out.println(this.getName() + " sending answer...");

                if (line.equals("exit")) {
                    System.out.println("end Thread");
                    break;
                }
                if (line.equals("ls")) {
                    line = Arrays.toString(dir.list());
                    System.out.println(line);
                }

                if (line.substring(0, 8).equals("download")) {
                    //  String f = Files.readAllLines()
                    String fileName = line.substring(9);
                    byte[] fileContent = Files.readAllBytes(Paths.get(".\\src\\"+fileName));
                    line = new String(fileContent);
                    System.out.println(Arrays.toString(fileContent));
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
