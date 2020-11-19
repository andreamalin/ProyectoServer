import javax.swing.*;
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
        ServerDataBase dataBase = Dao.getServerDataBase();
        final User[] user = new User[1];
        Protocolo protocol = new Protocolo();
        //--------------------------PRINCIPAL--------------------------------------------
        JButton consoleBtn, newAccountBtn, portsBtn;
        JFrame options = new JFrame("SERVER");

        options.setLayout(null);
        options.setResizable(false);
        options.setLocationRelativeTo(null);
        options.setBounds(10, 10, 350, 235);
        options.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Dandole formato a los botones
        consoleBtn = new JButton("Consola Server");
        consoleBtn.setBounds(15, 15, 305, 50);
        newAccountBtn = new JButton("Agregar Cuenta");
        newAccountBtn.setBounds(15, 75, 305, 50);
        portsBtn = new JButton("Cambiar puertos");
        portsBtn.setBounds(15, 135, 305, 50);

        options.add(consoleBtn);
        options.add(newAccountBtn);
        options.add(portsBtn);

        options.setVisible(true);

        //---------------------------CONSOLA------------------------------------------
        JButton returnCBtn;
        JTextArea watchConsole;
        JScrollPane scrollConsole;
        JFrame console = new  JFrame("Consola del Servidor");

        console.setLayout(null);
        console.setResizable(false);
        console.setLocationRelativeTo(null);
        console.setBounds(10, 10, 345, 385);
        console.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Agregando text area
        watchConsole = new JTextArea(16,48);
        watchConsole.setEditable(false);
        watchConsole.setBounds(15, 15, 300, 250);

        scrollConsole = new JScrollPane(watchConsole, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollConsole.setBounds(15, 15, 300, 250);

        returnCBtn = new JButton("Regresar");
        returnCBtn.setBounds(120, 290, 100, 40);

        console.add(scrollConsole);
        console.add(returnCBtn);
        console.setVisible(false);

        //---------------------------NUEVA CUENTA---------------------------------------
        JLabel usernameLbl, passwordLbl, confirmLbl;
        JTextField usernameTxt, passwordTxt, confirmTxt;
        JButton confirmNABtn, returnNABtn;
        JFrame newAccount = new JFrame("Nueva cuenta");

        newAccount.setLayout(null);
        newAccount.setResizable(false);
        newAccount.setLocationRelativeTo(null);
        newAccount.setBounds(10, 10, 345, 250);
        newAccount.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Inputs
        usernameLbl = new JLabel("Nombre Usuario:");
        usernameLbl.setBounds(15, 15, 150, 40);
        passwordLbl = new JLabel("Contraseña:");
        passwordLbl.setBounds(15, 55, 150, 40);
        confirmLbl = new JLabel("Confirmar:");
        confirmLbl.setBounds(15, 95, 150, 40);

        usernameTxt = new JTextField();
        usernameTxt.setBounds(120, 25, 180, 25);
        passwordTxt = new JTextField();
        passwordTxt.setBounds(120, 65, 180, 25);
        confirmTxt = new JTextField();
        confirmTxt.setBounds(120, 105, 180, 25);

        // Buttons
        confirmNABtn = new JButton("Confirmar");
        confirmNABtn.setBounds(55, 145, 100, 40);
        returnNABtn = new JButton("Regresar");
        returnNABtn.setBounds(180, 145, 100, 40);

        newAccount.add(usernameLbl);
        newAccount.add(passwordLbl);
        newAccount.add(confirmLbl);
        newAccount.add(usernameTxt);
        newAccount.add(passwordTxt);
        newAccount.add(confirmTxt);
        newAccount.add(confirmNABtn);
        newAccount.add(returnNABtn);

        newAccount.setVisible(false);

        //----------------------CAMBIO DE PUERTOS---------------------------------------
        JButton confirmBtn, returnBtn;
        JLabel dnsPortLbl, clientPortLbl, serverPortLbl, dnsPort, clientsPort, serverPort;
        JTextField dnsPortTxt, clientPortTxt, serverPortTxt;
        JFrame ports = new JFrame("Actualizador de Puertos");

        ports.setLayout(null);
        ports.setResizable(false);
        ports.setLocationRelativeTo(null);
        ports.setBounds(10, 10, 345, 320);
        ports.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Los que muestran el puerto actual
        clientsPort = new JLabel("Puerto cliente: " + clientPort);
        clientsPort.setBounds(15, 10, 150, 40);
        serverPort = new JLabel("Puerto servidor: " + serversPort);
        serverPort.setBounds(180, 10, 150, 40);
        dnsPort = new JLabel("Puerto DNS: " + dNSPort);
        dnsPort.setBounds(100, 50, 150, 40);

        // Inputs
        clientPortLbl = new JLabel("Puerto Cliente:");
        clientPortLbl.setBounds(15, 90, 150, 40);
        serverPortLbl = new JLabel("Puerto Server:");
        serverPortLbl.setBounds(15, 130, 150, 40);
        dnsPortLbl = new JLabel("Puerto DNS:");
        dnsPortLbl.setBounds(15, 170, 150, 40);

        clientPortTxt = new JTextField();
        clientPortTxt.setBounds(105,100, 180, 25);
        serverPortTxt = new JTextField();
        serverPortTxt.setBounds(105,140, 180, 25);
        dnsPortTxt = new JTextField();
        dnsPortTxt.setBounds(105, 180, 180, 25);

        // Buttons
        confirmBtn = new JButton("Confirmar");
        confirmBtn.setBounds(55, 220, 100, 40);
        returnBtn = new JButton("Regresar");
        returnBtn.setBounds(180, 220, 100, 40);

        ports.add(clientsPort);
        ports.add(serverPort);
        ports.add(dnsPort);
        ports.add(clientPortLbl);
        ports.add(serverPortLbl);
        ports.add(dnsPortLbl);
        ports.add(clientPortTxt);
        ports.add(serverPortTxt);
        ports.add(dnsPortTxt);
        ports.add(confirmBtn);
        ports.add(returnBtn);

        ports.setVisible(false);

        //------------------------ERROR----------------------------------
        JLabel message;
        JFrame error = new JFrame("Error");

        error.setLayout(null);
        error.setResizable(false);
        error.setLocationRelativeTo(null);
        error.setBounds(10, 10, 300, 100);
        error.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

        message = new JLabel("Un error inesperado ha ocurrdo");
        message.setBounds(50, 10, 200, 40);

        error.add(message);
        error.setVisible(false);


        //-----------------------Listeners---------------------------------
        // Options -> Console
        consoleBtn.addActionListener(e -> {
            options.setVisible(false);
            console.setVisible(true);

            // Console -> Options
            returnCBtn.addActionListener(a -> {
                console.setVisible(false);
                options.setVisible(true);
            });
        });

        // Options -> New User
        newAccountBtn.addActionListener(e -> {
            options.setVisible(false);
            newAccount.setVisible(true);

            // Agregar un nuevo usuario a la db
            confirmNABtn.addActionListener(a -> {

            });

            // New User -> Options
            returnNABtn.addActionListener(a -> {
                newAccount.setVisible(false);
                options.setVisible(true);
            });
        });

        // Options -> Ports
        portsBtn.addActionListener(e -> {
            options.setVisible(false);
            ports.setVisible(true);

            // Ports -> Options
            returnBtn.addActionListener(a -> {
                ports.setVisible(false);
                options.setVisible(true);
            });
        });

        watchConsole.append("Comenzando conexiones...\n");

        //---------------------------------CORRIENDO---------------------------------
        
        new Thread(new Runnable() {
            public void run() {
            ArrayList<Contact> contacts = null;
            ArrayList<Mail> mails = null;
            ArrayList<ServerIp> servers = dataBase.getServers();

            boolean loggedIn = false, check = false, connecting=true;
            String server = "", username="", password="";
            Integer aux;
        
            try {
                ServerSocket clientServer = new ServerSocket(clientPort);
                Socket socketClient = clientServer.accept();

                InputStreamReader isr = new InputStreamReader(socketClient.getInputStream());
                BufferedReader in = new BufferedReader(isr);

                watchConsole.append("Conexion con el cliente: ACEPTADA\n");

                // es importante el segundo argumento (true) para que tenga autoflush al hacer print
                PrintWriter out = new PrintWriter(socketClient.getOutputStream(), true);

                while(connecting){
                    if (in.readLine().equals("checkServer")) {
                        username = in.readLine();
                        server = in.readLine();
                        password = in.readLine();
                        //Se guardan los datos dentro del protocolo
                        protocol.setClient(username, server, password);

                        // Revisando que exista el server
                        for (ServerIp temp: servers) {
                            if (temp.getServerName().equalsIgnoreCase(server)) {
                                check = true;
                                out.println(protocol.setServer(check));
                                watchConsole.append("Client : " + in.readLine() + "\n"); // Login
                                break;
                            } 
                        }
                        
                        if(!check){
                            out.println(protocol.setServer(check));
                            watchConsole.append("Server: Server not found\n");
                        }
                        
                    }

                    // SE ESTAN VALIDANDO LOS DATOS CON LA DB
                    if (check){
                        user[0] = dataBase.getUserData(username);
                        if (user[0] != null) {
                            out.println(protocol.setUser(true));

                            // Validando password
                            if (user[0].getPassword().equals(password)) {
                                out.println(protocol.setPassword(true));

                                aux = dataBase.updateUser(user[0]);
                                if (aux != 0){
                                    user[0].setStatus("on");
                                    loggedIn = true;
                                    connecting = false;
                                    watchConsole.append("Server: OK LOGIN\n");

                                    // Jalando tod de la db
                                    contacts = dataBase.getUserContacts(user[0].id);
                                    mails = dataBase.getUserMails(user[0].id);
                                }

                            } else{  // ERROR no encuentra la contraseña
                                watchConsole.append("Server: ERROR 102\n");
                                out.println(protocol.setPassword(false));
                            }

                        } else{ // ERROR no encuentra al usuario
                            watchConsole.append("Server: ERROR 101\n");
                            out.println(protocol.setPassword(false));
                        }
                    }
                    check = false; //Dejando de revisar al usuario
                }

                //while db.loggedin (mientras el usuario este conectado se jala info del cliente)
                while(loggedIn){
                    String msjCliente = in.readLine();
                    //se lee la consola del cliente

                    if (!msjCliente.equals("")) {
                        //SE PIDE EL CLIST
                        if (msjCliente.equalsIgnoreCase("CLIST " + username)) {
                            //Se muestra el mensaje del cliente
                            watchConsole.append("Client : " + msjCliente + "\n");

                            if(contacts.size() > 0){

                                // Se recorre el arraylist y se muestran en pantalla los contactos
                                for (int i = 0; i < contacts.size(); i++){
                                    if(i != (contacts.size() - 1)){
                                        watchConsole.append("Server : " + contacts.get(i).toString() + "\n");
                                    } else{
                                        watchConsole.append("Server : " + contacts.get(i).toString() + " *\n");
                                    }
                                }

                            } else{
                                watchConsole.append("Server : ERROR 103\n");
                            }


                        } //SE PIDE EL GETNEWMAILS
                        else if (msjCliente.equalsIgnoreCase("GETNEWMAILS " + username)) {
                            //Se muestra el mensaje del cliente
                            watchConsole.append("Client : " + msjCliente + "\n");

                            //La db me regresa todos los correos nuevos

                            if(mails.size() > 0){

                                // Se recorre el arraylist y se muestran en pantalla los contactos
                                for (int i = 0; i < mails.size(); i++){
                                    if(i != (mails.size() - 1)){
                                        watchConsole.append("Server : " + mails.get(i).toString() + "\n");
                                    } else{
                                        watchConsole.append("Server : " + mails.get(i).toString() + " *\n");
                                    }
                                }

                            } else{
                                watchConsole.append("Server : OK GETNEWMAILS NOMAIL\n");
                            }

                        } //SI MANDA UN MAIL
                        else if (msjCliente.equalsIgnoreCase("SEND MAIL")) {
                            //Se muestra el mensaje del cliente
                            watchConsole.append("Client : " + msjCliente + " *\n");
                            String temp = dataBase.tableSize("mails");

                            Mail mail = new Mail(temp);
                            ArrayList<String> remitentes = new ArrayList<>();
                            ArrayList<String> senders = new ArrayList<>();
                            boolean flag = true, error104 = false, error105 = false;

                            //RECIBIR REMITENTES
                            boolean recibirRemitentes = true;
                            while(recibirRemitentes){
                                String posibleRemitente = in.readLine();
                                if (posibleRemitente.contains("*")) {
                                    recibirRemitentes = false; //Se dejan de recibir
                                    watchConsole.append("Client : " + posibleRemitente + " *\n");
                                }else
                                    watchConsole.append("Client : " + posibleRemitente + "\n");

                                remitentes.add(posibleRemitente);
                            }

                            //RECIBIR ASUNTO
                            String asunto = in.readLine();
                            watchConsole.append("Client : " + asunto + "\n"); //SE VAN MOSTRANDO EN PANTALLA
                            String body =  in.readLine();
                            watchConsole.append("Client : " + body + "\n"); //SE VAN MOSTRANDO EN PANTALLA
                            watchConsole.append("Client : " + in.readLine() + "\n"); //Se termina el correo

                            mail.setAuthor(user[0].getUsername());
                            mail.setServer(user[0].getServer());
                            mail.setMatter(asunto.substring(13));
                            mail.setBody(body.substring(10));

                            // Verificando que exista el servidor y el contacto
                            for (int i = 0; i < remitentes.size(); i++) {
                                String[] aux2;
                                String sender = remitentes.get(i);

                                // Quitando el * del final
                                if (i == (remitentes.size() -1)){
                                    sender = sender.substring(8, sender.length() - 1);
                                }else{
                                    sender = sender.substring(8);
                                }

                                // Separando por arroba
                                aux2 = sender.split("@");

                                // Verificando contactos
                                for(int j = 0; j < contacts.size(); j++){

                                    if(contacts.get(j).getUsername().equalsIgnoreCase(aux2[0]))
                                        break;

                                    if (j == (contacts.size() - 1))
                                        error104 = true;

                                }

                                // Verificando servers
                                for(int j = 0; j < servers.size(); j++){

                                    if(servers.get(j).getServerName().equalsIgnoreCase(aux2[aux2.length - 1]))
                                        break;

                                    if (j == (servers.size() - 1))
                                        error105 = true;

                                }

                                senders.add(aux2[0]);
                            }

                            // Errores contacto o server
                            if(error104){
                                watchConsole.append("Server : ERROR 104\n");
                                out.println("ERROR 104");
                                flag = false;
                            }

                            if(error105){
                                watchConsole.append("Server : ERROR 105\n");
                                out.println("ERROR 105");
                                flag = false;
                            }

                            // Errore que no hay remitentes
                            if(remitentes.size() < 1){
                                watchConsole.append("Server : ERROR 106\n");
                                out.println("ERROR 106");
                                flag = false;
                            }

                            // Verificando asunto
                            if (mail.getMatter().equalsIgnoreCase("")){
                                watchConsole.append("Server : ERROR 107\n");
                                out.println("ERROR 107");
                                flag = false;
                            }

                            // Verificando el cuerpo
                            if (mail.getBody().equalsIgnoreCase("")){
                                watchConsole.append("Server : ERROR 108\n");
                                out.println("ERROR 108");
                                flag = false;
                            }

                            // Si se tuvieron todos entonces se mandan los mails
                            if (flag){
                                ArrayList<User> send = new ArrayList<>();

                                for (String remitente : senders) {
                                    send.add(dataBase.getUserData(remitente));
                                }

                                dataBase.addMail(mail, send);

                                watchConsole.append("Server : OK SEND MAIL\n");
                                mails = dataBase.getUserMails(user[0].id);
                            }


                        } //Si hace NEWCONT
                        else if (msjCliente.equalsIgnoreCase("NEWCONT")) {
                            //RECIBIR CONTACTO A AGREGAR
                            String contacto = in.readLine();
                            String[] separado = contacto.split("@");
                            boolean error109 = false, error110 = false;

                            watchConsole.append("Client: NEWCONT " + contacto + "\n"); //Senal del cliente
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
                                watchConsole.append("Server : ERROR 109\n");
                                out.println("ERROR 109");
                            
                            if (error110)
                                watchConsole.append("Server : ERROR 110\n");
                                out.println("ERROR 110");
                            
                            //SI EXISTE
                            if(!error109 && !error110){
                                String auxId = dataBase.tableSize("contacts");
                                Contact newContact = new Contact(auxId);
                                newContact.setServer(separado[1]);
                                newContact.setUsername(separado[0]);

                                dataBase.addContact(newContact, user[0]);

                                watchConsole.append("Server : OK NEWCONT " + contacto + "\n");
                            }

                        } else if (msjCliente.equalsIgnoreCase("NOOP")){
                            //MANTENEMOS VIVO EL SERVIDOR
                            out.println("OK NOOP"); //Mantenemos vivo al cliente
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
                                clientServer.close();

                            } else{
                                out.println("on"); //Se avisa al cliente que el usuario no se pudo salir
                            }

                        }
                    }

                    out.println(""); //Se avisa al cliente que tod va bien
                    msjCliente = ""; //luego de leerlo se regresa a vacio
                }
                out.println("off"); //Se avisa al cliente que el usuario se quedo inactivo
            } catch (Exception e) {
                e.printStackTrace();
            }
            } //THREAD
        }).start(); //RUNNABLE

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
                serversServer.close();
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
                
                in.close();
                out.close();
                socketDns.close();
                dnsServer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        };



        //Para correr el puerto 1500
        Thread serversServerThread = new Thread(runnableServer1);
        serversServerThread.start();
        //Para correr el puerto 1200
        Thread dnsServerThread = new Thread(runnableDNS1);
        dnsServerThread.start();
    }
    

}
