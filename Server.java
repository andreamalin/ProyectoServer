import java.io.BufferedReader;
import java.io.DataOutput;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    static int clientPort = 1400;
    static int serversPort = 1500;
    static int dNSPort = 1200;

    public static void main(String[] args) {
        System.out.println("Comenzando conexiones...");

        Runnable runnableClient1 = new Runnable() {
            public void run() {
                try {
                    ServerSocket clientServer = new ServerSocket(clientPort);
                    Socket socketClient = clientServer.accept();

                    InputStreamReader isr = new InputStreamReader(socketClient.getInputStream());
                    BufferedReader in = new BufferedReader(isr);
                    
                    System.out.println("Conexion con el cliente: ACEPTADA");

                    // es importante el segundo argumento (true) para que tenga autoflush al hacer print
                    PrintWriter out = new PrintWriter(socketClient.getOutputStream(), true);
            
                    out.println("OK LOGIN");
                    System.out.println("Client: " + in.readLine());
                    System.out.println("Client: " + in.readLine());

                    in.close();
                    out.close();
                    socketClient.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
            
        //Para correr el puerto 1500
        Runnable runnableServer1 = new Runnable(){
            public void run() {
                try {
                    ServerSocket serversServer = new ServerSocket(serversPort);
                    Socket socketServers = serversServer.accept();

                    InputStreamReader isr = new InputStreamReader(socketServers.getInputStream());
                    BufferedReader in = new BufferedReader(isr);
                    
                    // es importante el segundo argumento (true) para que tenga autoflush al hacer print
                    PrintWriter out = new PrintWriter(socketServers.getOutputStream(), true);
            
                    out.println("Bienvenido Servidor");
                    System.out.println("Server: " + in.readLine());

                    in.close();
                    out.close();
                    socketServers.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        //Para correr el puerto 1200
        Runnable runnableDNS1 = new Runnable(){
            public void run() {
                try {
                    ServerSocket dnsServer = new ServerSocket(dNSPort);
                    Socket socketDns = dnsServer.accept();

                    InputStreamReader isr = new InputStreamReader(socketDns.getInputStream());
                    BufferedReader in = new BufferedReader(isr);
                    
                    // es importante el segundo argumento (true) para que tenga autoflush al hacer print
                    PrintWriter out = new PrintWriter(socketDns.getOutputStream(), true);
            
                    out.println("Bienvenido DNS");
                    System.out.println("DNS: " + in.readLine());

                    in.close();
                    out.close();
                    socketDns.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };


        //Para correr el puerto 1400
        Thread clientServerThread = new Thread(runnableClient1);
        clientServerThread.start();
        //Para correr el puerto 1500
        Thread serversServerThread = new Thread(runnableServer1);
        serversServerThread.start();
        //Para correr el puerto 1200
        Thread dnsServerThread = new Thread(runnableDNS1);
        dnsServerThread.start();
    }
    

}
