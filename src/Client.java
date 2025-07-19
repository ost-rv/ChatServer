import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

class Client implements Runnable {
    Socket socket;
    ChatServer chatServer;
    // средства ввода и вывода
    Scanner in;
    PrintStream out;

    public Client(Socket socket, ChatServer chatServer) {
        this.chatServer = chatServer;
        this.socket = socket;

        Thread thread = new Thread(this);
        thread.start();
    }

    void receive(String msg) {
        out.println(msg);
    }

    public void run() {
        try {
            // получаем потоки ввода и вывода
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();

            // создаем удобные средства ввода и вывода
            in = new Scanner(is);
            out = new PrintStream(os);

            // читаем из сети и пишем в сеть
            out.println("Welcome to chat!");
            String input = in.nextLine();

            while (!input.equals("bye")) {
                chatServer.sendAll(input);
                input = in.nextLine();
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}