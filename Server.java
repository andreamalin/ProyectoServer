import javax.swing.*;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class Server {

    // Puertos por default e información del server
    static int clientPort = 1400;
    static int serversPort = 1500;
    static int dNSPort = 1200;
    private static final String serverName = "lexUt";
    private static String ip = " ";

    // Indica cuando es que se esta usando la base de datos
    private static ServerDataBase dataBase;
    private static ArrayList<Mail> mails = null;
    private static final AtomicBoolean dataBaseUsed = new AtomicBoolean(false);

    // Variables para conexion
    private static ServerSocket clientServer, serversServer, dnsServer;
    private static Socket socketClient, socketServers, socketDns;
    private static InputStreamReader isr, isrS, isrDNS;
    private static BufferedReader in, inS, inDNS;
    private static PrintWriter out, outS, outDNS;

    // DNS Flags
    private static final AtomicBoolean getIps = new AtomicBoolean(false);
    private static final AtomicBoolean flagDns = new AtomicBoolean(true);
    private static final AtomicBoolean sendDns = new AtomicBoolean(false);
    private static final AtomicBoolean dnsLogOut = new AtomicBoolean(false);
    private static final AtomicBoolean serverLogOut = new AtomicBoolean(false);

    // Estructuras de datos en las que se guardaran datos
    private static final HashMap<String, String> serversMap = new HashMap<>();


    private static void usingDataBase(){
        dataBaseUsed.set(!dataBaseUsed.get());
    }

    public static void main(String[] args) {
        dataBase = Dao.getServerDataBase();
        final User[] user = new User[1];
        Protocolo protocol = new Protocolo();
        serversMap.put("1", "server1"); //PARA HACER PRUEBAS

        //--------------------------PRINCIPAL--------------------------------------------
        JButton consoleBtn, newAccountBtn, portsBtn, dnsConsoleBtn, serverConsoleBtn;
        JFrame options = new JFrame(serverName);

        options.setLayout(null);
        options.setResizable(false);
        options.setLocationRelativeTo(null);
        options.setBounds(10, 10, 350, 355);
        options.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Dandole formato a los botones
        consoleBtn = new JButton("Consola Cliente");
        consoleBtn.setBounds(15, 15, 305, 50);
        serverConsoleBtn = new JButton("Consola Server");
        serverConsoleBtn.setBounds(15, 75, 305, 50);
        dnsConsoleBtn = new JButton("Consola DNS");
        dnsConsoleBtn.setBounds(15, 135, 305, 50);
        newAccountBtn = new JButton("Agregar Cuenta");
        newAccountBtn.setBounds(15, 195, 305, 50);
        portsBtn = new JButton("Cambiar puertos");
        portsBtn.setBounds(15, 255, 305, 50);

        options.add(consoleBtn);
        options.add(serverConsoleBtn);
        options.add(dnsConsoleBtn);
        options.add(newAccountBtn);
        options.add(portsBtn);

        options.setVisible(true);

        //---------------------------CONSOLA CLIENTE------------------------------------------
        JButton returnCBtn;
        JTextArea watchConsole;
        JScrollPane scrollConsole;
        JFrame console = new JFrame("Consola del Servidor");

        console.setLayout(null);
        console.setResizable(false);
        console.setLocationRelativeTo(null);
        console.setBounds(10, 10, 345, 385);
        console.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Agregando text area
        watchConsole = new JTextArea(16, 48);
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

        //---------------------------CONSOLA SERVER------------------------------------------
        JButton returnSBtn;
        JTextArea watchServerConsole;
        JScrollPane scrollSConsole;
        JFrame serverConsole = new JFrame("Consola del Servidor");

        serverConsole.setLayout(null);
        serverConsole.setResizable(false);
        serverConsole.setLocationRelativeTo(null);
        serverConsole.setBounds(10, 10, 345, 385);
        serverConsole.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Agregando text area
        watchServerConsole = new JTextArea(16, 48);
        watchServerConsole.setEditable(false);
        watchServerConsole.setBounds(15, 15, 300, 250);

        scrollSConsole = new JScrollPane(watchServerConsole, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollSConsole.setBounds(15, 15, 300, 250);

        returnSBtn = new JButton("Regresar");
        returnSBtn.setBounds(120, 290, 100, 40);

        serverConsole.add(scrollSConsole);
        serverConsole.add(returnSBtn);
        serverConsole.setVisible(false);

        //---------------------------CONSOLA DNS------------------------------------------
        JButton returnDNSBtn, confirmDNSBtn;
        JLabel ipLbl;
        JTextField ipTxt;
        JTextArea watchDNS;
        JScrollPane scrollDNS;
        JFrame dnsConsole = new JFrame("Consola del DNS");

        dnsConsole.setLayout(null);
        dnsConsole.setResizable(false);
        dnsConsole.setLocationRelativeTo(null);
        dnsConsole.setBounds(10, 10, 345, 425);
        dnsConsole.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Agregando text area
        watchDNS = new JTextArea(16, 48);
        watchDNS.setEditable(false);
        watchDNS.setBounds(15, 15, 300, 250);

        scrollDNS = new JScrollPane(watchDNS, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollDNS.setBounds(15, 15, 300, 250);

        ipLbl = new JLabel("Ingrese ip:");
        ipLbl.setBounds(40, 275, 150, 40);
        ipTxt = new JTextField();
        ipTxt.setBounds(110, 285, 150, 25);

        // Botones
        confirmDNSBtn = new JButton("Cambiar");
        confirmDNSBtn.setBounds(40, 325, 100, 40);

        returnDNSBtn = new JButton("Regresar");
        returnDNSBtn.setBounds(180, 325, 100, 40);

        dnsConsole.add(scrollDNS);
        dnsConsole.add(ipLbl);
        dnsConsole.add(ipTxt);
        dnsConsole.add(confirmDNSBtn);
        dnsConsole.add(returnDNSBtn);
        dnsConsole.setVisible(false);

        //---------------------------NUEVA CUENTA---------------------------------------
        JLabel usernameLbl, passwordLbl, confirmLbl;
        JTextField usernameTxt;
        JPasswordField passwordTxt, confirmTxt;
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
        passwordTxt = new JPasswordField();
        passwordTxt.setBounds(120, 65, 180, 25);
        confirmTxt = new JPasswordField();
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
        clientPortTxt.setBounds(105, 100, 180, 25);
        serverPortTxt = new JTextField();
        serverPortTxt.setBounds(105, 140, 180, 25);
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

        message = new JLabel("Un error inesperado ha ocurrido");
        message.setBounds(50, 10, 200, 40);

        error.add(message);
        error.setVisible(false);

        //------------------------Success----------------------------------
        JLabel messages;
        JFrame success = new JFrame("Exito");

        success.setLayout(null);
        success.setResizable(false);
        success.setLocationRelativeTo(null);
        success.setBounds(10, 10, 240, 100);
        success.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

        messages = new JLabel("Se completo con exito");
        messages.setBounds(50, 10, 200, 40);

        success.add(messages);
        success.setVisible(false);

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

        // Option -> serverConsole
        serverConsoleBtn.addActionListener(e -> {
            options.setVisible(false);
            serverConsole.setVisible(true);

            // serverConsole -> Options
            returnSBtn.addActionListener(a -> {
                serverConsole.setVisible(false);
                options.setVisible(true);
            });
        });

        // Option -> dnsConsole
        dnsConsoleBtn.addActionListener(e -> {
            options.setVisible(false);
            dnsConsole.setVisible(true);

            // Agregando IP
            confirmDNSBtn.addActionListener(a -> {
                ip = ipTxt.getText();
                ipTxt.setText("");
                sendDns.set(true); // Esperando a que se duerma
            });

            // dnsConsole -> Options
            returnDNSBtn.addActionListener(a -> {
                dnsConsole.setVisible(false);
                options.setVisible(true);
            });
        });

        // Options -> New User
        newAccountBtn.addActionListener(e -> {
            options.setVisible(false);
            newAccount.setVisible(true);

            // Agregar un nuevo usuario a la db
            confirmNABtn.addActionListener(a -> {
                User addedUser;
                String tempUsername, tempPassword, tempConfirm;
                Integer checkAdded = 0;
                tempUsername = usernameTxt.getText();
                tempPassword = String.valueOf(passwordTxt.getPassword());
                tempConfirm = String.valueOf(confirmTxt.getPassword());

                usernameTxt.setText("");
                passwordTxt.setText("");
                confirmTxt.setText("");

                // Ahora se comparan
                if (tempPassword.equals(tempConfirm)) {
                    addedUser = new User("0");
                    addedUser.setUsername(tempUsername);
                    addedUser.setPassword(tempPassword);
                    addedUser.setServer(serverName);

                    // Ahora se intenta meter al usuario
                    while (dataBaseUsed.get()) {
                    }
                    usingDataBase();
                    if(dataBase.getUserData(tempUsername) == null){
                        checkAdded = dataBase.addUser(addedUser);
                    }
                    usingDataBase();

                    if (checkAdded == 0)
                        error.setVisible(true);
                    else
                        success.setVisible(true);

                }

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

            // Cambiar ports
            confirmBtn.addActionListener(a -> {
                String tempClient, tempServer, tempDNS;
                int auxClient, auxServer, auxDNS;

                tempClient = clientPortTxt.getText();
                tempServer = serverPortTxt.getText();
                tempDNS = dnsPortTxt.getText();

                // Ahora se intenta cambair 
                try {
                    auxClient = Integer.parseInt(tempClient);
                    auxServer = Integer.parseInt(tempServer);
                    auxDNS = Integer.parseInt(tempDNS);

                    clientPort = auxClient;
                    serversPort = auxServer;
                    dNSPort = auxDNS;

                    success.setVisible(true);
                    clientsPort.setText("Puerto cliente: " + clientPort);
                    serverPort.setText("Puerto servidor: " + serversPort);
                    dnsPort.setText("Puerto DNS: " + dNSPort);

                } catch (Exception p) {
                    error.setVisible(true);
                } finally {
                    clientPortTxt.setText("");
                    serverPortTxt.setText("");
                    dnsPortTxt.setText("");
                }

            });

            // Ports -> Options
            returnBtn.addActionListener(a -> {
                ports.setVisible(false);
                options.setVisible(true);
            });
        });

        watchConsole.append("Comenzando conexiones...\n");

       //---------------------------------CORRIENDO---------------------------------

        //THREAD
        new Thread(() -> {
            ArrayList<Contact> contacts = null;
            boolean loggedIn = false;
            String server, username, password;
            Integer aux;

            try {
                clientServer = new ServerSocket(clientPort);
                socketClient = clientServer.accept();

                isr = new InputStreamReader(socketClient.getInputStream());
                in = new BufferedReader(isr);

                watchConsole.append("Conexion con el cliente: ACEPTADA\n");

                // es importante el segundo argumento (true) para que tenga autoflush al hacer print
                out = new PrintWriter(socketClient.getOutputStream(), true);

                while (true) {
                    if (in.readLine().equals("checkServer")) {
                        username = in.readLine();
                        server = in.readLine();
                        password = in.readLine();
                        //Se guardan los datos dentro del protocolo
                        protocol.setClient(username, server, password);

                        // Revisando que exista el server

                        if (serverName.equalsIgnoreCase(server)) {
                            out.println(protocol.setServer(true));
                            watchConsole.append("Client : " + in.readLine() + "\n"); // Login

                            // Obteniendo la informacion para validar
                            while (dataBaseUsed.get()) {
                            }
                            usingDataBase();
                            user[0] = dataBase.getUserData(username);
                            usingDataBase();
                            if (user[0] != null) {
                                out.println(protocol.setUser(true));

                                // Validando password
                                if (user[0].getPassword().equals(password)) {
                                    out.println(protocol.setPassword(true));

                                    while (dataBaseUsed.get()) {
                                    }
                                    usingDataBase();
                                    aux = dataBase.updateUser(user[0]);
                                    usingDataBase();

                                    if (aux != 0) {
                                        user[0].setStatus("on");
                                        loggedIn = true;
                                        watchConsole.append("Server: OK LOGIN\n");

                                        // Jalando tod de la db
                                        while (dataBaseUsed.get()) {
                                        }
                                        usingDataBase();
                                        contacts = dataBase.getUserContacts(user[0].id);
                                        mails = dataBase.getUserMails(user[0].id);
                                        usingDataBase();
                                    }

                                } else {  // ERROR no encuentra la contraseña
                                    watchConsole.append("Server: ERROR 102\n");
                                    out.println(protocol.setPassword(false));
                                }

                            } else { // ERROR no encuentra al usuario
                                watchConsole.append("Server: ERROR 101\n");
                                out.println(protocol.setPassword(false));
                            }

                            break;
                        } else {
                            out.println(protocol.setServer(false));
                            watchConsole.append("Server: Server not found\n");
                        }

                    }

                }

                //while db.loggedin (mientras el usuario este conectado se jala info del cliente)
                while (loggedIn) {
                    String msjCliente = in.readLine();
                    //se lee la consola del cliente

                    if (!msjCliente.equals("")) {
                        //SE PIDE EL CLIST
                        if (msjCliente.equalsIgnoreCase("CLIST " + username)) {
                            //Se muestra el mensaje del cliente
                            watchConsole.append("Client : " + msjCliente + "\n");

                            if (contacts.size() > 0) {

                                // Se recorre el arraylist y se muestran en pantalla los contactos
                                for (int i = 0; i < contacts.size(); i++) {
                                    if (i != (contacts.size() - 1)) {
                                        watchConsole.append("Server : " + contacts.get(i).toString() + "\n");
                                    } else {
                                        watchConsole.append("Server : " + contacts.get(i).toString() + " *\n");
                                    }
                                }

                            } else {
                                watchConsole.append("Server : ERROR 103\n");
                            }


                        } //SE PIDE EL GETNEWMAILS
                        else if (msjCliente.equalsIgnoreCase("GETNEWMAILS " + username)) {
                            //Se muestra el mensaje del cliente
                            watchConsole.append("Client : " + msjCliente + "\n");

                            //La db me regresa todos los correos nuevos

                            if (mails.size() > 0) {

                                // Se recorre el arraylist y se muestran en pantalla los contactos
                                for (int i = 0; i < mails.size(); i++) {
                                    if (i != (mails.size() - 1)) {
                                        watchConsole.append("Server : " + mails.get(i).toString() + "\n");
                                    } else {
                                        watchConsole.append("Server : " + mails.get(i).toString() + " *\n");
                                    }
                                }

                                // Borrando los mails
                                mails = new ArrayList<>();
                                while (dataBaseUsed.get()) {
                                }
                                usingDataBase();
                                dataBase.deleteMails(user[0].id);
                                usingDataBase();

                            } else {
                                watchConsole.append("Server : OK GETNEWMAILS NOMAIL\n");
                            }

                        } //SI MANDA UN MAIL
                        else if (msjCliente.equalsIgnoreCase("SEND MAIL")) {
                            //Se muestra el mensaje del cliente
                            watchConsole.append("Client : " + msjCliente + "\n");
                            while (dataBaseUsed.get()) {
                            }
                            usingDataBase();
                            String temp = dataBase.tableSize("mails");
                            usingDataBase();

                            Mail mail = new Mail(temp);
                            ArrayList<String> sendUsers = new ArrayList<>();
                            ArrayList<String> senders = new ArrayList<>();
                            ArrayList<User> send = new ArrayList<>();
                            boolean flag = true, error104 = false, error105 = false;

                            //RECIBIR REMITENTES
                            boolean recibirRemitentes = true;
                            while (recibirRemitentes) {
                                String posibleRemitente = in.readLine();
                                
                                if (posibleRemitente.contains("*")){
                                    watchConsole.append("Client : MAIL TO " + posibleRemitente + "\n");
                                    posibleRemitente = posibleRemitente.replace("\\*", "");
                                    recibirRemitentes = false; //Se dejan de recibir'
                                } else {
                                    watchConsole.append("Client : MAIL TO " + posibleRemitente + "\n");
                                }
                                if (posibleRemitente.equalsIgnoreCase("LOGOUT")){
                                    msjCliente = "LOGOUT";  
                                    recibirRemitentes = false; //Se dejan de recibir'
                                }
                                sendUsers.add(posibleRemitente);
                            }

                            if(!msjCliente.equalsIgnoreCase("LOGOUT")){

                                //RECIBIR ASUNTO
                                String asunto = in.readLine();
                                watchConsole.append("Client : " + asunto + "\n"); //SE VAN MOSTRANDO EN PANTALLA
                                String body = in.readLine();
                                watchConsole.append("Client : " + body + "\n"); //SE VAN MOSTRANDO EN PANTALLA
                                watchConsole.append("Client : " + in.readLine() + "\n"); //Se termina el correo

                                mail.setAuthor(user[0].getUsername());
                                mail.setServer(user[0].getServer());
                                mail.setMatter(asunto.substring(13));
                                mail.setBody(body.substring(10));

                                // Verificando que exista el servidor y el contacto
                                for (String sendUser : sendUsers) {
                                    String[] aux2;
                                    // Separando por arroba
                                    aux2 = sendUser.split("@"); // [0] = Nombre usuario, [1] = Nombre server

                                    for (int j = 0; j < contacts.size(); j++) { // Verificando que exista
                                        if (contacts.get(j).getUsername().equalsIgnoreCase(aux2[0])) {
                                            break;
                                        }

                                        if (j == (contacts.size() - 1)) { // No encontro al usuario
                                            error104 = true;
                                            break;
                                        }

                                    }

                                    if (serverName.equalsIgnoreCase(aux2[aux2.length - 1])) {
                                        error105 = false;
                                    } else if (serversMap.get(aux2[aux2.length - 1]) != null) {

                                        //--SE BUSCA EL CONTACTO EN OTRO SERVER--
                                        watchServerConsole.append("Esperando conexion...\n");
                                        try {
                                            Socket socketServer = new Socket(serversMap.get(aux2[aux2.length - 1]),
                                                    serversPort);
                                            InputStreamReader isrSS = new
                                                    InputStreamReader(socketClient.getInputStream());
                                            BufferedReader inSS = new BufferedReader(isrSS);
                                            PrintWriter outSS = new PrintWriter(socketClient.getOutputStream(),
                                                    true);
                                            watchServerConsole.append("Server conectado\n");

                                            String serverMessage;
                                            boolean existsUser = true;

                                            // Reviso que este el usuario
                                            outSS.println("CHECK CONTACT " + sendUser);
                                            watchServerConsole.append("Server : CHECK CONTACT " + sendUser + "\n");

                                            serverMessage = inSS.readLine(); // Obteniendo si se encuentra el usuario

                                            if (serverMessage.equals("CHECK ERROR 205")) { // Si se encontro al usuario
                                                watchServerConsole.append("Other Server : " + serverMessage + "\n");
                                                error104 = true;     // Indica que no hubo exito en encontrar al usuario
                                                existsUser = false;
                                            }

                                            serverMessage = inSS.readLine(); // Obteniendo si es el server
                                            if (serverMessage.equals("CHECK ERROR 206")) {
                                                watchServerConsole.append("Other Server : " + serverMessage + "\n");
                                                error105 = true;
                                                existsUser = false;
                                            }

                                            if (existsUser) {    // MANDA EL MENSAJE
                                                // Mandando al otro server
                                                outSS.println("SEND MAIL " + sendUser);
                                                outSS.println("MAIL FROM " + mail.getAuthor() + "@" + serverName);
                                                outSS.println("MAIL SUBJECT " + mail.getMatter());
                                                outSS.println("MAIL BODY " + mail.getBody());
                                                outSS.println("END MAIL");

                                                watchServerConsole.append("Server : SEND MAIL " + sendUser + "\n");
                                                watchServerConsole.append("Server : MAIL FROM " +
                                                        mail.getAuthor() + "@" + serverName + "\n");
                                                watchServerConsole.append("Server : MAIL SUBJECT " +
                                                        mail.getMatter() + "\n");
                                                watchServerConsole.append("Server : MAIL BODY " +
                                                        mail.getBody() + "\n");
                                                watchServerConsole.append("Server : END MAIL\n");

                                                //Verificando que este bien
                                                serverMessage = inSS.readLine(); // Obteniendo si se encuentra el usuario
                                                watchServerConsole.append("Other Server : " + serverMessage + "\n");

                                                // Obteniendo errores
                                                serverMessage = inSS.readLine();
                                                if (serverMessage.equals("SEND ERROR 202 "))
                                                    watchServerConsole.append("Other Server : " + serverMessage + "\n");

                                                if (serverMessage.equals("SEND ERROR 203 "))
                                                    watchServerConsole.append("Other Server : " + serverMessage + "\n");

                                                if (serverMessage.equals("SEND ERROR 204 "))
                                                    watchServerConsole.append("Other Server : " + serverMessage + "\n");

                                            }

                                            inSS.close();
                                            outSS.close();
                                            socketServer.close();
                                            error105 = false; // dejar en false si lo encuentra
                                        } catch (Exception a) {
                                            a.printStackTrace();
                                        }

                                    }

                                    // Verificando contactos
                                    senders.add(aux2[0]);
                                }
                                

                                // Errores contacto o server
                                if (error104) {
                                    watchConsole.append("Server : ERROR 104\n");
                                    out.println("ERROR 104");
                                    flag = false;
                                } 

                                if (error105) {
                                    watchConsole.append("Server : ERROR 105\n");
                                    out.println("ERROR 105");
                                    flag = false;
                                }

                                // Errore que no hay sendUsers
                                if (sendUsers.size() < 1) {
                                    watchConsole.append("Server : ERROR 106\n");
                                    out.println("ERROR 106");
                                    flag = false;
                                } 

                                // Verificando asunto
                                if (mail.getMatter().equalsIgnoreCase("")) {
                                    watchConsole.append("Server : ERROR 107\n");
                                    out.println("ERROR 107");
                                    flag = false;
                                } 

                                // Verificando el cuerpo
                                if (mail.getBody().equalsIgnoreCase("")) {
                                    watchConsole.append("Server : ERROR 108\n");
                                    out.println("ERROR 108");
                                    flag = false;
                                } 

                                // Si se tuvieron todos entonces se mandan los mails
                                if (flag) {
                                    while (dataBaseUsed.get()) {
                                    }
                                    usingDataBase();
                                    for (String sender : senders) {
                                        send.add(dataBase.getUserData(sender));
                                    }

                                    dataBase.addMail(mail, send);

                                    watchConsole.append("Server : OK SEND MAIL\n");
                                    mails = dataBase.getUserMails(user[0].id);
                                    usingDataBase();
                                    out.println(""); //Se avisa al cliente que tdodo va bien
                                }   
                            }


                        } //Si hace NEWCONT
                        else if (msjCliente.equalsIgnoreCase("NEWCONT")) {
                            //RECIBIR CONTACTO A AGREGAR
                            String contacto = in.readLine();

                            if(!contacto.equalsIgnoreCase("NOOP") &&
                                    !contacto.equalsIgnoreCase("LOGOUT")){
                                String[] separado = contacto.split("@"); // [0] = contacto, [1] = servername
                                boolean error109 = true, error110 = true;

                                watchConsole.append("Client: NEWCONT " + contacto + "\n"); //Senal del cliente

                                // Se obtiene al user de la db
                                while (dataBaseUsed.get()) { }
                                usingDataBase();
                                User addAux = dataBase.getUserData(separado[0]);
                                usingDataBase();

                                if (addAux == null) { // No encuentra al usuario

                                    if (serversMap.get(separado[1]) != null) { // Encuentra al server

                                        //---SE BUSCA EL CONTACTO EN OTRO SERVER---
                                        try{
                                            Socket socketServer = new Socket(serversMap.get(separado[1]),
                                                    serversPort);
                                            InputStreamReader isrSS = new
                                                    InputStreamReader(socketClient.getInputStream());
                                            BufferedReader inSS = new BufferedReader(isrSS);
                                            PrintWriter outSS = new PrintWriter(socketClient.getOutputStream(),
                                                    true);

                                            String serverMessage;

                                            // Reviso que este el usuario
                                            outSS.println("CHECK CONTACT " + contacto);
                                            watchServerConsole.append("Server : CHECK CONTACT " + contacto + "\n");

                                            serverMessage = inSS.readLine(); // Obteniendo si se encuentra el usuario

                                            if (serverMessage.equals("CHECK ERROR 205")) { // No se encontro al usuario
                                                watchServerConsole.append("Other Server : " + serverMessage + "\n");

                                                watchConsole.append("Server : NEWCONT ERROR 109 "
                                                        + contacto + "\n");
                                                out.println("ERROR 109");
                                            } else
                                                error110 = false; // Encuentra el user

                                            serverMessage = inSS.readLine(); // Obteniendo si es el server
                                            if (serverMessage.equals("CHECK ERROR 206")){
                                                watchServerConsole.append("Other Server : " + serverMessage + "\n");

                                                watchConsole.append("Server : NEWCONT ERROR 110 "
                                                        + contacto + "\n");
                                                out.println("ERROR 110");
                                            } else
                                                error109 = false;

                                            inSS.close();
                                            outSS.close();
                                            socketServer.close();
                                        } catch (Exception a){
                                                a.printStackTrace();
                                            }
                                    } else { // No encuentra al server
                                        watchConsole.append("Server : NEWCONT ERROR 109 "
                                                + contacto + "\n");
                                        out.println("ERROR 109");

                                        watchConsole.append("Server : NEWCONT ERROR 110 "
                                                + contacto + "\n");
                                        out.println("ERROR 110");
                                    }

                                }

                                //SI EXISTE
                                if (!error109 || !error110) {
                                    while (dataBaseUsed.get()) {
                                    }
                                    usingDataBase();
                                    String auxId = dataBase.tableSize("contacts");
                                    Contact newContact = new Contact(auxId);
                                    newContact.setServer(separado[1]);
                                    newContact.setUsername(separado[0]);

                                    dataBase.addContact(newContact, user[0]);
                                    usingDataBase();
                                    watchConsole.append("Server : OK NEWCONT " + contacto + "\n");
                                	out.println(""); //Se avisa al cliente que todo va bien
                                }

                            } else {
                                msjCliente = "LOGOUT";
                            }

                        } else if (msjCliente.equalsIgnoreCase("NOOP")) {
                            //MANTENEMOS VIVO EL SERVIDOR
                            watchConsole.append("Client : " + msjCliente + "\n");
                            watchConsole.append("Server : OK NOOP\n");
                            out.println("Warning: Sesion will be closed in 20sec"); //Mantenemos vivo al cliente
                        } 
                        //Si hace LOGOUT
                        if (msjCliente.equalsIgnoreCase("LOGOUT")) {
                            //Se muestra el mensaje del cliente
                            watchConsole.append("Client: " + msjCliente + "\n");
                            while (dataBaseUsed.get()) {
                            }
                            usingDataBase();
                            aux = dataBase.updateUser(user[0]);
                            usingDataBase();
                            if (aux != 0) {
                                dnsLogOut.set(true);
                                serverLogOut.set(true);
                                user[0].setStatus("off");
                                loggedIn = false;
                                out.println("off"); //Se avisa al cliente que el usuario ya salio
                                watchConsole.append("Server: OK LOGOUT\n");
                                // Se cierran puertos
                                in.close();
                                out.close();
                                socketClient.close();
                                clientServer.close();
                                System.exit(1); //Salida definitiva de consola
                            } else {
                                out.println("on"); //Se avisa al cliente que el usuario no se pudo salir
                            }
                        }
                    }

                    out.println(""); //Se avisa al cliente que tod va bien
                }
                out.println("off"); //Se avisa al cliente que el usuario se quedo inactivo
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start(); //RUNNABLE

        //----------------------------------SERVER CONNECTION-----------------------------------
        Runnable runnableServer1 = () -> {
            try {
                serversServer = new ServerSocket(serversPort);
                socketServers = serversServer.accept();
                isrS = new InputStreamReader(socketServers.getInputStream());
                inS = new BufferedReader(isrS);
                outS = new PrintWriter(socketServers.getOutputStream(), true);

                while (!serverLogOut.get()) {
                    // Se observa que es lo que quiere el server

                    String serverMessage, cutMessage;

                    serverMessage = inS.readLine();
                    cutMessage = serverMessage.substring(0, 5);

                    if (cutMessage.equalsIgnoreCase("SEND ")) {
                        watchServerConsole.append("OTHER SERVER : " + serverMessage + "\n");
                        cutMessage = serverMessage.substring(14);
                        String[] splitMessage = cutMessage.split("@");

                        //Se muestra el mensaje del cliente
                        Mail mail;
                        User foundUser = null;
                        ArrayList<User> send = new ArrayList<>();
                        boolean flag = true;
                        String temp = "0";

                        //RECIBIR DATOS DEL MAIL
                        String sender = in.readLine();
                        watchServerConsole.append("OTHER SERVER : " + sender + "\n"); //SE VAN MOSTRANDO EN PANTALLA
                        String matter = in.readLine();
                        watchServerConsole.append("OTHER SERVER : " + matter + "\n"); //SE VAN MOSTRANDO EN PANTALLA
                        String body = in.readLine();
                        watchServerConsole.append("OTHER SERVER : " + body + "\n"); //SE VAN MOSTRANDO EN PANTALLA
                        watchServerConsole.append("OTHER SERVER : " + in.readLine() + "\n"); //Se termina el correo

                        // Verificando los datos
                        if (splitMessage.length > 1) {
                            while (dataBaseUsed.get()) {
                            }
                            usingDataBase();
                            temp = dataBase.tableSize("mails");
                            foundUser = dataBase.getUserData(splitMessage[0]);
                            usingDataBase();

                            if (foundUser == null) { // No se encontro el user
                                watchServerConsole.append("Server : SEND ERROR 201 " + cutMessage + "\n");
                                outS.println("SEND ERROR 201 " + cutMessage);
                                flag = false;       // Ya no entra a meter el mail
                            } else
                                outS.println("");

                        }

                        mail = new Mail(temp);  // Inicializando el mail

                        String aux = sender.substring(10);
                        String[] splitAux = aux.split("@");
                        if (splitAux.length > 1) {   // Formato aceptado
                            mail.setAuthor(splitAux[0]);
                            mail.setServer(splitAux[1]);
                            outS.println("");
                        } else {
                            watchServerConsole.append("Server : SEND ERROR 202\n");
                            outS.println("SEND ERROR 202");
                            flag = false;       // Ya no entra a meter el mail
                        }

                        // Ahora se verifica si tiene autor y cuerpo
                        aux = matter.substring(13);
                        if (!aux.equals("")) {
                            outS.println("");
                            mail.setMatter(aux);
                        } else {
                            watchServerConsole.append("Server : SEND ERROR 203\n");
                            outS.println("SEND ERROR 203");
                            flag = false;       // Ya no entra a meter el mail
                        }

                        aux = body.substring(10);
                        if (!aux.equals("")) {
                            outS.println("");
                            mail.setBody(aux);
                        } else {
                            watchServerConsole.append("Server : SEND ERROR 204\n");
                            outS.println("SEND ERROR 204");
                            flag = false;       // Ya no entra a meter el mail
                        }

                        // Si se tuvieron todos entonces se mandan los mails
                        if (flag) {
                            send.add(foundUser);
                            while (dataBaseUsed.get()) {
                            }
                            usingDataBase();

                            dataBase.addMail(mail, send);

                            watchServerConsole.append("Server : OK SEND MAIL\n");
                            mails = dataBase.getUserMails(user[0].id); // Actualizando los mails
                            usingDataBase();
                        }


                    } else if (cutMessage.equalsIgnoreCase("CHECK")) { // Verificando si aqui esta el contacto
                        watchServerConsole.append("Other Server : " + serverMessage);
                        cutMessage = serverMessage.substring(14);
                        String[] splitMessage = cutMessage.split("@");

                        // Verificando que sea valido
                        if (splitMessage.length > 1) {
                            User foundUser;
                            while (dataBaseUsed.get()) {
                            }
                            usingDataBase();
                            foundUser = dataBase.getUserData(splitMessage[0]);
                            usingDataBase();

                            if (foundUser == null) { // Sino se encontro el user
                                outS.println("CHECK ERROR 205");
                                watchServerConsole.append("Server : CHECK ERROR 205");
                            } else
                                outS.println("");


                            if (!serverName.equals(splitMessage[1])) { // Si no coinciden los datos
                                outS.println("CHECK ERROR 205");
                                watchServerConsole.append("Server : CHECK ERROR 205");
                            } else
                                outS.println("");

                        }

                    } else{
                        outS.println("INVALID COMMAND ERROR");
                        watchServerConsole.append("Server : INVALID COMMAND ERROR");
                    }

                }

                inS.close();
                outS.close();
                socketServers.close();
                serversServer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        
        //--------------------------------DNS CONNECTION----------------------------------------
        Runnable runnableDNS1 = () -> {
            watchDNS.append("Comenzando conexiones...\n");
            try {
                dnsServer = new ServerSocket(dNSPort);
                socketDns = dnsServer.accept();
                isrDNS = new InputStreamReader(socketDns.getInputStream());
                inDNS = new BufferedReader(isrDNS);
                outDNS = new PrintWriter(socketDns.getOutputStream(), true);
                String dnsResponse;
                watchDNS.append("Conexión con el DNS: Aceptado\n");
                // Cuando inicie
                do{
                    while (!sendDns.get()){ } // Esperando a obtener la ip
                    watchDNS.append("Server : ONLINE " + serverName + " " + ip + "\n");
                    outS.println("ONLINE " + serverName + " " + ip);
                    dnsResponse = inDNS.readLine();

                    if (dnsResponse.equalsIgnoreCase("ONLINE ERROR 301"))
                        flagDns.set(false);
                    watchDNS.append("DNS : " + dnsResponse + "\n");

                }while(flagDns.get());

                while (getIps.get()){ }  // Esperando a que le digan que necesitan los ips
                outS.println("GETIPTABLE");
                watchDNS.append("SERVER : GETIPTABLE\n");

                // A continuación se obtienen todos los
                boolean continueListen = true;
                while (continueListen) {
                    String server = inDNS.readLine();

                    if (server.equalsIgnoreCase("GETIPTABLE ERROR 303")){
                        watchDNS.append("DNS : GETIPTABLE ERROR 303\n");
                        break;
                    }

                    if (server.contains("*"))
                        continueListen = false; //Se dejan de recibir
                    watchDNS.append("DNS : " + server + "\n");
                    server = server.substring(12);
                    String[] aux = server.split(" ");
                    serversMap.put(aux[0], aux[1]);
                }

                // Se le indica al DNS que se saldra
                while (!dnsLogOut.get()){ }
                watchDNS.append("Server : OFFLINE " + serverName + "\n");
                outS.println("OFFLINE " + serverName);

                // Mostrando el lo que se obtuvo del server
                watchDNS.append("DNS : " + inDNS.readLine() + "\n");

                inDNS.close();
                outDNS.close();
                socketDns.close();
                dnsServer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        // Server
        Thread serversServerThread = new Thread(runnableServer1);
        serversServerThread.start();

        //Para correr el puerto 1200
        Thread dnsServerThread = new Thread(runnableDNS1);
        dnsServerThread.start();

    }
}
