import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;

    public Client(Socket socket, String username) {
        try{
            this.socket = socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.username = username;
        } catch (IOException e) {
            closeAll(socket, bufferedReader, bufferedWriter);
            e.printStackTrace();
        }
    }

    private void sendMessage() {
        try {
            bufferedWriter.write(username);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            Scanner scanner = new Scanner(System.in);
            while (socket.isConnected()) {
                String toSend = scanner.nextLine();
                bufferedWriter.write(String.format("[%s] %s", this.username, toSend));
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        } catch (IOException e) {
            closeAll(socket, bufferedReader, bufferedWriter);
        }
    }

    private void listen() {
        new Thread(() -> {
           String newMessage;

           while (socket.isConnected()) {
               try {
                   newMessage = bufferedReader.readLine();
                   System.out.println(newMessage);
               } catch (IOException e) {
                   closeAll(socket, bufferedReader, bufferedWriter);
                   e.printStackTrace();
               }
           }
        }).start();
    }

    private void closeAll(Socket socket,
                          BufferedReader bufferedReader,
                          BufferedWriter bufferedWriter) {
        SocketHelper.closeResources(socket, bufferedReader, bufferedWriter);
    }

    public static void main(String[] args) throws IOException {
        // 1 - ler username; 2 - configurar SOCKET endereco+porta; 3 - ouvir.
        Scanner scanner = new Scanner(System.in);
        System.out.println("Insira seu username para o chat: ");
        String username = scanner.nextLine();
        Socket socket = new Socket("localhost", 5000);
        Client client = new Client(socket, username);
        client.listen();
        client.sendMessage();
    }

}
