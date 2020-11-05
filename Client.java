import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        try {
            //el cliente siempre estara escuchando el puerto 1400
            Socket socketServer = new Socket("localhost", Server.clientPort);
            InputStreamReader isr = new InputStreamReader(socketServer.getInputStream());
            BufferedReader in = new BufferedReader(isr);

            // es importante el segundo argumento (true) para que tenga autoflush al hacer print
            PrintWriter out = new PrintWriter(socketServer.getOutputStream(), true);

            System.out.println("Servidor: " + in.readLine());
            out.println("Hola Servidor");

            in.close();
            out.close();
            socketServer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}