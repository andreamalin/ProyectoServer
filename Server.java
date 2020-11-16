import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    static final int clientPort = 1400;
    static final int serversPort = 1500;
    static final int dNSPort = 1200;

    public static void main(String[] args) {
        ServerDataBase dataBase = Dao.getDataBase();
        final User[] user = new User[1];
    	Protocolo protocol = new Protocolo();
        System.out.println("Comenzando conexiones...");

        Runnable runnableClient1 = () -> {
            ArrayList<Contact> contacts = null;
            ArrayList<Mail> mails = null;
            ArrayList<User> allUsers = dataBase.getUsers();
            ArrayList<ServerIp> servers = dataBase.getServers();

            boolean loggedIn = false, check = false;
            String server = "", username="", password="";
            Integer aux = 0;
            try {
                ServerSocket clientServer = new ServerSocket(clientPort);
                Socket socketClient = clientServer.accept();

                InputStreamReader isr = new InputStreamReader(socketClient.getInputStream());
                BufferedReader in = new BufferedReader(isr);

                System.out.println("Conexion con el cliente: ACEPTADA");

                // es importante el segundo argumento (true) para que tenga autoflush al hacer print
                PrintWriter out = new PrintWriter(socketClient.getOutputStream(), true);

                if (in.readLine().equals("checkServer")) {
                    username = in.readLine();
                    server = in.readLine();
                    password = in.readLine();
                    //Se guardan los datos dentro del protocolo
                    protocol.setClient(username, server, password);

                    // Revisando que exista
                    for (ServerIp temp: servers) {
                        if (temp.getServerName().equalsIgnoreCase(server)) {
                            check = true;
                            break;
                        }
                    }

                    out.println(protocol.setServer(check));
                }
                System.out.println("Client: " + in.readLine()); //login

                //if userExists setUser(true)

                // SE ESTAN VALIDANDO LOS DATOS CON LA DB
                if (check){
                    user[0] = dataBase.getUserData(username);
                    if (user[0] != null) {
                        System.out.println("Server: " + user[0].getUsername());
                        out.println(protocol.setUser(true));

                        // Validando password
                        if (user[0].getPassword().equals(password)) {
                            out.println(protocol.setPassword(true));

                            System.out.println("Server: " + user[0].getPassword());
                            aux = dataBase.updateUser(user[0]);
                            if (aux != 0){
                                user[0].setStatus("on");
                                loggedIn = true;
                                out.println("OK LOGIN");
                                System.out.println("SERVER : OK LOGIN");

                                // Jalando todo de la db
                                contacts = dataBase.getUserContacts(user[0].id);
                                mails = dataBase.getUserMails(user[0].id);

                            }

                        } else{  // ERROR no encuentra la contraseña
                            System.out.println("Server : ERROR 102");
                            out.println("ERROR 102");
                        }

                    } else{ // ERROR no encuentra al usuario
                        System.out.println("Server : ERROR 101");
                        out.println("ERROR 101");
                    }
                }

                check = false;
                //while db.loggedin (mientras el usuario este conectado se jala info del cliente)
                while(loggedIn){

                    String msjCliente = in.readLine();
                    //se lee la consola del cliente

                    if (!msjCliente.equals("")) {
                        //SE PIDE EL CLIST
                        if (msjCliente.equalsIgnoreCase("CLIST " + username)) {
                            //Se muestra el mensaje del cliente
                            System.out.println("Client : " + msjCliente);

                            if(contacts.size() > 0){

                                // Se recorre el arraylist y se muestran en pantalla los contactos
                                for (int i = 0; i < contacts.size(); i++){
                                    if(i != (contacts.size() - 1)){
                                        System.out.println("Server : " + contacts.get(i).toString());
                                    } else{
                                        System.out.println("Server : " + contacts.get(i).toString() + " *");
                                    }
                                }

                            } else{
                                System.out.println("Server : ERROR 103");
                            }


                        } //SE PIDE EL GETNEWMAILS
                        else if (msjCliente.equalsIgnoreCase("GETNEWMAILS " + username)) {
                            //Se muestra el mensaje del cliente
                            System.out.println("Client : " + msjCliente);

                            //La db me regresa todos los correos nuevos

                            if(mails.size() > 0){

                                // Se recorre el arraylist y se muestran en pantalla los contactos
                                for (int i = 0; i < mails.size(); i++){
                                    if(i != (mails.size() - 1)){
                                        System.out.println("Server : " + mails.get(i).toString());
                                    } else{
                                        System.out.println("Server : " + mails.get(i).toString() + " *");
                                    }
                                }

                            } else{
                                System.out.println("Server : OK GETNEWMAILS NOMAILS");
                            }

                        } //SI MANDA UN MAIL
                        else if (msjCliente.equalsIgnoreCase("SEND MAIL")) {
                            //Se muestra el mensaje del cliente
                            System.out.println("Client : " + msjCliente);
                            String temp = dataBase.tableSize("mails");

                            Mail mail = new Mail(temp);
                            ArrayList<String> remitentes = new ArrayList<>();
                            Boolean flag = true, error104 = false, error105 = false;

                            //RECIBIR REMITENTES
                            boolean recibirRemitentes = true;
                            while(recibirRemitentes){
                                String posibleRemitente = in.readLine();
                                if (posibleRemitente.contains("*")) {
                                    recibirRemitentes = false; //Se dejan de recibir
                                }
                                System.out.println("Client : " + posibleRemitente); //SE VAN MOSTRANDO EN PANTALLA
                                remitentes.add(posibleRemitente);
                            }

                            //RECIBIR ASUNTO
                            String asunto = in.readLine();
                            System.out.println("Client : " + asunto); //SE VAN MOSTRANDO EN PANTALLA
                            String body = "";
                            String cuerpo;

                            while (in.readLine().equalsIgnoreCase("END SEND MAIL")){
                                //RECIBIR CUERPO
                                cuerpo = in.readLine();
                                body += cuerpo;
                                System.out.println("Client : " + cuerpo); //SE VAN MOSTRANDO EN PANTALLA
                            }

                            mail.setAuthor(user[0].getUsername());
                            mail.setMatter(asunto);
                            mail.setBody(body);

                            // Verificando que exista el servidor y el contacto
                            for (int i = 0; i < remitentes.size(); i++) {
                                String[] aux2 = new String[2];
                                aux2[0] = remitentes.get(i);

                                // Quitando el * del final
                                if (i == (remitentes.size() -1)){
                                    aux2[0] = aux2[0].split(" ")[0];
                                }

                                // Separando por arroba
                                aux2 = aux2[0].split("@");

                                // Verificando contactos
                                for(int j = 0; j < contacts.size(); j++){

                                    if(contacts.get(j).getUsername().equalsIgnoreCase(aux2[0]))
                                        break;

                                    if (j == (contacts.size() - 1))
                                        error104 = true;

                                }

                                // Verificando servers
                                for(int j = 0; j < servers.size(); j++){

                                    if(servers.get(j).getServerName().equalsIgnoreCase(aux2[1]))
                                        break;

                                    if (j == (servers.size() - 1))
                                        error105 = true;

                                }


                            }

                            // Errores contacto o server
                            if(error104){
                                System.out.println("Server : ERROR 104");
                                flag = false;
                            }

                            if(error105){
                                System.out.println("Server : ERROR 105");
                                flag = false;
                            }

                            // Errore que no hay remitentes
                            if(remitentes.size() < 1){
                                System.out.println("Server : ERROR 106");
                                flag = false;
                            }

                            // Verificando asunto
                            if (mail.getMatter().equalsIgnoreCase("")){
                                System.out.println("Server : ERROR 107");
                                flag = false;
                            }

                            // Verificando el cuerpo
                            if (mail.getBody().equalsIgnoreCase("")){
                                System.out.println("Server : ERROR 108");
                                flag = false;
                            }

                            // Si se tuvieron todos entonces se mandan los mails
                            if (flag){
                                ArrayList<User> send = new ArrayList<>();

                                for (String remitente : remitentes) {
                                    String[] aux2 = new String[2];
                                    aux2[0] = remitente;

                                    // Separando por arroba
                                    aux2 = aux2[0].split("@");

                                    send.add(dataBase.getUserData(aux2[0]));
                                }

                                dataBase.addMail(mail, send);

                                System.out.println("Server: OK SEND MAIL");
                                mails = dataBase.getUserMails(user[0].id);
                            }


                        } //Si hace NEWCONT
                        else if (msjCliente.equalsIgnoreCase("NEWCONT")) {
                            //RECIBIR CONTACTO A AGREGAR
                            String contacto = in.readLine();
                            String[] separado = contacto.split("@");
                            Boolean error109 = false, error110 = false;
                            System.out.println("Client: NEWCONT " + contacto); //Senal del cliente
                            //SE SEVISA EN LA DB SI EL CONTACTO EXISTE if contacto in db
                            //NEWCONT ERROR 109 contact@server si no es parte del servidor
                            User addAux = dataBase.getUserData(separado[0]);
                            if (addAux == null || !(addAux.getServer().equalsIgnoreCase(server))){
                                error109 = true;
                            }

                            //NEWCONT ERROR 110 contact@server si el server no existe o no esta online
                            if(addAux == null || (addAux.getStatus().equalsIgnoreCase("off"))) {
                                error110 = true;
                            }else{
                                for (int i = 0; i < servers.size(); i++) {

                                    if (servers.get(i).getServerName().equalsIgnoreCase(separado[0]))
                                        break;

                                    if (i == (servers.size() - 1))
                                        error110 = true;
                                }
                            }

                            // Mostrando errores
                            if (error109)
                                System.out.println("Server : ERROR 109");
                            
                            if (error110)
                                System.out.println("Server : ERROR 110");

                            //SI EXISTE
                            if(!error109 && !error110){
                                String auxId = dataBase.tableSize("contacts");
                                Contact newContact = new Contact(auxId);
                                newContact.setServer(separado[1]);
                                newContact.setUsername(separado[0]);

                                dataBase.addContact(newContact, user[0]);

                                System.out.println("Server: OK NEWCONT " + contacto);

                            }

                        } //Si hace LOGOUT
                        else if (msjCliente.equalsIgnoreCase("LOGOUT")) {
                            //Se muestra el mensaje del cliente
                            System.out.println("Client: " + msjCliente);

                            aux = dataBase.updateUser(user[0]);
                            if (aux != 0){
                                user[0].setStatus("off");
                                loggedIn = false;
                                out.println("off"); //Se avisa al cliente que el usuario ya salio
                                System.out.println("Server: OK LOGOUT");

                                // Se cierran puertos
                                in.close();
                                out.close();
                                socketClient.close();

                            } else{
                                out.println("on"); //Se avisa al cliente que el usuario no se pudo salir
                            }

                        }
                    }

                    out.println(""); //Se avisa al cliente que todo va bien
                    msjCliente = ""; //luego de leerlo se regresa a vacio
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
            
        //Para correr el puerto 1500
        Runnable runnableServer1 = () -> {
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
        };

        //Para correr el puerto 1200
        Runnable runnableDNS1 = () -> {
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
