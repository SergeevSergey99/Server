import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
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
            File dir = new File(".\\files");

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

                if (line.toLowerCase().startsWith("download")) {
//                if (line.substring(0, 8).equals("download")) {
                    //  String f = Files.readAllLines()
                    String fileName = line.substring(9);
                    File file = new File(dir.getPath() + "\\" + fileName);
                    if (file.exists()) {
                        byte[] fileContent = Files.readAllBytes(file.toPath());
                        line = new String(fileContent);
                        System.out.println(Arrays.toString(fileContent));
                    } else line = "Error!: Stupid user.";
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
