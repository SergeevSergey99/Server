import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    final static int PORT = 3247;
    public static void main(String[] args) throws IOException {
/*
        Thread thread[] = new Thread[5];

        for (int i = 0; i < thread.length; i++) {
            thread[i] = new Thread(new MyRunnable(), "Thread" + i);
            thread[i].setPriority(Thread.MAX_PRIORITY);

            thread[i].start();
            System.out.println(thread[i].getName() + " started");
        }*/


        ServerSocket serverSocket = new ServerSocket(PORT);

        //noinspection InfiniteLoopStatement
        while (true) {
            Socket socket = serverSocket.accept();
            Thread thread = new Thread(new MyRunnable(socket));
            thread.start();

        }
    }
}