import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner; 

public class DNS {

    public static void main(String[] args){
        try {
            //el dns siempre estara escuchando el puerto 1200
            Socket socketDns = new Socket("localhost", Server.dNSPort);
            InputStreamReader isr = new InputStreamReader(socketDns.getInputStream());
            BufferedReader in = new BufferedReader(isr);
            // es importante el segundo argumento (true) para que tenga autoflush al hacer print
            PrintWriter out = new PrintWriter(socketDns.getOutputStream(), true);

            DNSDataBase dataBase = Dao.getDNSDataBase();
            String message = "", messageAux = "", response = "";
            ServerIp temp;

            String serverSignal = in.readLine();
            
            //Si recibe la senal de que el server esta online
            if(serverSignal.equalsIgnoreCase("ONLINE")){
                String servername = in.readLine();
                String ip = in.readLine();
                out.println("OK ONLINE " + servername);

                //out.println("OK ONLINE " + dataBase.getServer(message));
                // Obtiene el nombre del server al cual se quiere buscar en el dns
                temp = dataBase.getServer(message);

                if(temp != null)
                    response = temp.getIp();
                else
                    response = "NOT FOUND";

                // Mandar response
                //out.println(response); //Se manda la senal al server

                // Ahora obtiene obtiene todos los servers de quien se lo pidio
                // while message

                temp = dataBase.getServer(message);
                if(temp == null){
                    temp = new ServerIp("0");
                    temp.setServerName(message);
                    temp.setIp(messageAux);
                    dataBase.addServerIP(temp);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
