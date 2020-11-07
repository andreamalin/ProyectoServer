import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    static final int clientPort = 1400;
    static final int serversPort = 1500;
    static final int dNSPort = 1200;

    public static void main(String[] args) {
        ServerDataBase dataBase = Dao.getDataBase();
    	Protocolo protocol = new Protocolo();
        System.out.println("Comenzando conexiones...");

        Runnable runnableClient1 = new Runnable() {
            public void run() {
                boolean loggedIn = true;
                String server="", user="", password="";
                try {
                    ServerSocket clientServer = new ServerSocket(clientPort);
                    Socket socketClient = clientServer.accept();

                    InputStreamReader isr = new InputStreamReader(socketClient.getInputStream());
                    BufferedReader in = new BufferedReader(isr);
                    
                    System.out.println("Conexion con el cliente: ACEPTADA");

                    // es importante el segundo argumento (true) para que tenga autoflush al hacer print
                    PrintWriter out = new PrintWriter(socketClient.getOutputStream(), true);
                    
                    if (in.readLine().equals("checkServer")) {
                        user = in.readLine();
                        server = in.readLine();
                        password = in.readLine();
                        //Se guardan los datos dentro del protocolo
                        protocol.setClient(user, server, password);

                        //aqui una instancia hacia la db revisaria si el string existe en los ip
                        //if(stringExists) setServer(true)
                        out.println(protocol.setServer(true));
                    }
                    System.out.println("Client: " + in.readLine()); //login

                    //Aqui una instancia hacia la db revisaria si el string existe en user y password
                    
                    //if userExists setUser(true)
                    String setUser = protocol.setUser(true);
                    if (!setUser.equals("")) {
                        System.out.println("Server: " + setUser);
                    }
                    out.println(setUser);
                    
                    //if passwordExists setPassword(true)
                    //si es true, hay que cambiar a loggedIn en la db
                    String setPassword = protocol.setPassword(true);
                    if (!setPassword.equals("")) {
                        System.out.println("Server: " + setPassword);
                    }
                    out.println(setPassword);

                    //while db.loggedin (mientras el usuario este conectado se jala info del cliente)
                    while(loggedIn){
                        String msjCliente = in.readLine();
                        //se lee la consola del cliente
                        if (msjCliente != null) {
                            //Se muestra el mensaje del cliente
                            System.out.println("Client: " + msjCliente);
                            //SE PIDE EL CLIST
                            if (msjCliente.equalsIgnoreCase("CLIST " + user)) {
                                //La db me regresa la CLIST Server: contacto
                                //System.out.println(db.getCLIST());
                                System.out.println("Server: john@123.23.1 *");
                            } //SE PIDE EL GETNEWMAILS
                            else if (msjCliente.equalsIgnoreCase("GETNEWMAILS " + user)) {
                                //La db me regresa todos los correos nuevos 
                                //Server: OK GETNEWMAILS sender subject body 
                                System.out.println("Server: OK GETNEWMAILS john@123.23.1 fiesta no faltes *");
                            } else if (msjCliente.equalsIgnoreCase("LOGOUT")) {
                                out.println("off"); //Se avisa al cliente que el usuario ya salio
                                loggedIn = false;
                            }
                            out.println(""); //Se avisa al cliente que todo va bien
                            msjCliente = ""; //luego de leerlo se regresa a vacio
                        } 
                    }
                    //Se cierra el puerto
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
