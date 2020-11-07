import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner; 

public class Client {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in); 
        Protocolo protocol = new Protocolo();
        boolean pedirUsuario = true;

        try {
            //el cliente siempre estara escuchando el puerto 1400
            Socket socketServer = new Socket("localhost", Server.clientPort);
            InputStreamReader isr = new InputStreamReader(socketServer.getInputStream());
            BufferedReader in = new BufferedReader(isr);
            // es importante el segundo argumento (true) para que tenga autoflush al hacer print
            PrintWriter out = new PrintWriter(socketServer.getOutputStream(), true);

            while(pedirUsuario){ 
                //Lo primero que hace el cliente es pedir el usuario@server
                System.out.print("Ingrese user@server: ");
                String user = scan.nextLine();
                //Luego se pide la contraseña
                System.out.print("Ingrese password: ");
                String password = scan.nextLine();
                out.println(protocol.checkServer(user, password)); //Se manda usuario y contrasena para verificar server
                //Si no hay error con el server, se hace LOGIN
                if ((in.readLine()).equals("")){
                    out.println(protocol.LOGIN());
                    //Si no hay error, se deja de pedir usuario@server
                    if ((in.readLine()).equals("")) {
                        if ((in.readLine()).equals("OK LOGIN")) {
                            pedirUsuario = false; 
                        }
                    }
                }
            }
            /*
            //el cliente estara dentro de la consola hasta que haga logout
            while(protocol.getStatus().equals("on")){
                String msjServer = in.readLine();
                //se lee la consola del cliente
                if (msjServer.equals("")) {
                    System.out.println("Server: " + msjServer);
                    msjServer = ""; //luego de leerlo se regresa a vacio
                }
            }
            */

            in.close();
            out.close();
            socketServer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}