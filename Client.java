import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner; 

public class Client {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in); 
        Protocolo protocol = new Protocolo();

        try {
            //el cliente siempre estara escuchando el puerto 1400
            Socket socketServer = new Socket("localhost", Server.clientPort);
            InputStreamReader isr = new InputStreamReader(socketServer.getInputStream());
            BufferedReader in = new BufferedReader(isr);

            // es importante el segundo argumento (true) para que tenga autoflush al hacer print
            PrintWriter out = new PrintWriter(socketServer.getOutputStream(), true);


            //Lo primero que hace el cliente es pedir el usuario@server
            System.out.print("Ingrese user@server: ");
            String user = scan.nextLine();
            //Luego se pide la contraseña
            System.out.print("Ingrese password: ");
            String password = scan.nextLine();

            out.println(protocol.LOGIN(user, password));
            System.out.println("Server: " + in.readLine());
            out.println(protocol.CLIST());
            out.println(protocol.GETNEWMAILS());

            in.close();
            out.close();
            socketServer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}