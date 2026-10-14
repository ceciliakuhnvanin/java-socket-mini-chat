import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ClientHandler implements Runnable {

    /*  copy on write é uma lista thread-safe fornecida pelo Java:
     *  cada cliente é executado em sua própria thread
     *  vários threads podem tentar modificar a lista ao mesmo tempo
     *  que outra thread está fazendo um loop para transmitir uma mensagem.
     */
    public static List<ClientHandler> clientHandlers = new CopyOnWriteArrayList<>();

    /*
     *  conexão -> recebe endereco+porta ENTÃO conecta
     */
    private Socket socket;

    /*
     *  leitura de texto recebido pelo cliente
     */
    private BufferedReader bufferedReader;

    /*
     *  escrita de volta para demais clientes conectados
     */
    private BufferedWriter bufferedWriter;

    private String clientUsername;

    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            this.clientUsername = bufferedReader.readLine();
            clientHandlers.add(this);
            this.broadcastMessage(String.format("%s entrou no chat!", this.clientUsername));
        } catch (IOException e) {
            closeAll(socket, bufferedReader, bufferedWriter);
        }
    }

    @Override
    public void run() {
        String clientMessage;

        while (socket.isConnected()) {
            try {
                clientMessage = bufferedReader.readLine(); // faz leitura da linha enviada
                broadcastMessage(clientMessage);
            } catch (IOException e) {
                closeAll(socket, bufferedReader, bufferedWriter);
                break;
            }
        }
        closeAll(socket, bufferedReader, bufferedWriter);
    }

    private void broadcastMessage(String toSend) {
        for (ClientHandler clientHandler : clientHandlers) {
            try {
                if (!clientHandler.clientUsername.equals(this.clientUsername)) {
                    clientHandler.bufferedWriter.write(toSend);
                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();
                }
            } catch (IOException e) {
                closeAll(socket, bufferedReader, bufferedWriter);
                e.printStackTrace();
            }
        }
    }

    private void remove() {
        clientHandlers.remove(this);
        broadcastMessage(String.format("Tchau, %s!", this.clientUsername));
    }

    private void closeAll(Socket socket,
                          BufferedReader bufferedReader,
                          BufferedWriter bufferedWriter) {
        this.remove();
        SocketHelper.closeResources(socket, bufferedReader, bufferedWriter);
    }

}
