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
        boolean pedirFuncion = true;

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
                out.println(protocol.checkServer(user, password)); 
                //Se manda usuario y contrasena para verificar server
                out.println(protocol.getUser());
                out.println(protocol.getServer());
                out.println(protocol.getPassword());
                
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
            //Seguido de ingresar la cuenta, el cliente pide el CLIST
            out.println(protocol.CLIST());
            //Seguido de ingresar la cuenta, el cliente pide los GETNEWMAILS
            out.println(protocol.GETNEWMAILS());
            /*SE TERMINA LA FASE DEL LOGIN, POR LO QUE EL USER TIENE 
            ACCESO A LA CONSOLA PARA PEDIR LO QUE DESEA
            */

            //el cliente estara dentro de la consola hasta que haga logout
            System.out.println("__________________________________________");
            System.out.println("Hola Usuario, las funciones que puedes realizar son:\nGETNEWMAILS\nSEND MAIL\nNEWCONT\nLOGOUT");
            System.out.println("__________________________________________");


            while(pedirFuncion){
                System.out.print("User: "); //Se pide al usuario que ingrese instruccion
                //Se lee lo ingresado por el usuario
                String mensaje = scan.nextLine();
                String msjServer = protocol.buscarFuncion(mensaje);
                out.println(msjServer); //Se manda la senal al server
                //Se lee la senal del server
                String msjDelServer = in.readLine();
                if (msjDelServer.equalsIgnoreCase("off")) {
                    pedirFuncion = false; //Si se cierra sesion, se dejan de pedir comandos
                }
            }
            //Se cierra la conexion
            in.close();
            out.close();
            socketServer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}