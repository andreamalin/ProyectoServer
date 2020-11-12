import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner; 
import javax.swing.*;
import java.awt.event.*;

public class Client{
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in); 
        Protocolo protocol = new Protocolo();

        boolean pedirUsuario = true;
        boolean pedirFuncion = true;


        /* 
                GUI LOGIN
        */
        JFrame window = new JFrame("Ingrese su cuenta");
        //Texto pidiendo usuario
        JLabel textUser = new JLabel("Ingresar");        
        textUser.setText("Username:");
        textUser.setBounds(10, -30, 100, 100);
        //Cuadro en blanco para el usuario
        JLabel labelUser = new JLabel();
        labelUser.setBounds(10, 70, 200, 100);
        //Input por parte del usuario -> ingresa user
        JTextField textfieldUser= new JTextField();
        textfieldUser.setBounds(110, 10, 130, 20);
        //Texto pidiendo server
        JLabel textServer = new JLabel();        
        textServer.setText("Server:");
        textServer.setBounds(10, 0, 100, 100);
        //Cuadro en blanco para el server
        JLabel labelServer = new JLabel();
        labelServer.setBounds(10, 110, 200, 100);
        //Input por parte del usuario -> ingresa server
        JTextField textfieldServer= new JTextField();
        textfieldServer.setBounds(110, 40, 130, 20);
        //Texto pidiendo contra
        JLabel textPassword = new JLabel();        
        textPassword.setText("Password:");
        textPassword.setBounds(10, 30, 100, 100);
        //Cuadro en blanco para el password
        JLabel labelPassword = new JLabel();
        labelPassword.setBounds(10, 150, 200, 100);
        //Input por parte del usuario -> ingresa contra
        JPasswordField textfieldPassword= new JPasswordField();
        textfieldPassword.setBounds(110, 70, 130, 20);
        //Creando boton para interactuar al terminar de ingresar datos
        JButton b=new JButton("Ingresar");
        b.setBounds(100,100,140, 40); 
        //Agregando todo a la vista
        window.add(labelUser);
        window.add(textfieldUser);
        window.add(textUser);
        window.add(labelServer);
        window.add(textfieldServer);
        window.add(textServer);
        window.add(labelPassword);
        window.add(textfieldPassword);
        window.add(textPassword);
        window.add(b); 
        window.setSize(300, 200);
        window.setLayout(null);
        window.setVisible(true);

        /* 
                GUI USER
        */
        JFrame window2 = new JFrame("Funciones del usuario");
        //Texto pidiendo usuario
        JLabel info = new JLabel();        
        info.setText("Hola Usuario, elige la opcion a realizar:");
        info.setBounds(20, -30, 300, 100);
        //Creando boton para pedir CLIST
        JButton clist=new JButton("CLIST");
        clist.setBounds(60,30,140, 20); 
        //Creando boton para pedir GETNEWMAILS
        JButton getMails=new JButton("GETNEWMAILS"); 
        getMails.setBounds(60,50,140, 20); 
        //Creando boton para pedir SENDMAIL
        JButton sendMail=new JButton("SEND MAIL"); 
        sendMail.setBounds(60,70,140, 20); 
        //Creando boton para pedir NEWCONT
        JButton newCont=new JButton("NEWCONT"); 
        newCont.setBounds(60,90,140, 20); 
        //Creando boton para pedir LOGOUT
        JButton logout=new JButton("LOGOUT"); 
        logout.setBounds(60,110,140, 20); 
        //Agregando todo a la vista
        window2.add(info);
        window2.add(clist);
        window2.add(getMails);
        window2.add(sendMail);
        window2.add(newCont);
        window2.add(logout);
        window2.setSize(280, 200);
        window2.setLayout(null);
        window2.setVisible(false);

        /* 
                GUI E-MAIL
        */
        JFrame window3 = new JFrame("Redacta tu e-mail");
        window3.setSize(450, 300);
        //Texto pidiendo mail recipient
        JLabel textMail = new JLabel();        
        textMail.setText("Mail recipient user@server (separa con espacio varios correos)");
        textMail.setBounds(0, -30, 500, 100);
        //Cuadro en blanco para el mail recipient
        JLabel labelMail = new JLabel();
        labelMail.setBounds(0, 30, 300, 100);
        //Input por parte del usuario -> ingresa recipient
        JTextField textfieldMail = new JTextField();
        textfieldMail.setBounds(0, 30, 300, 20);
        //Texto pidiendo mail subject
        JLabel textMailSubject = new JLabel();        
        textMailSubject.setText("Mail subject: ");
        textMailSubject.setBounds(0, 10, 500, 100);
        //Cuadro en blanco para el subject
        JLabel labelMailSubject = new JLabel();
        labelMailSubject.setBounds(0, 70, 300, 100);
        //Input por parte del usuario -> ingresa subject
        JTextField textfieldMailSubject = new JTextField();
        textfieldMailSubject.setBounds(0, 70, 300, 20);
        //Texto pidiendo mail body
        JLabel textMailBody = new JLabel();        
        textMailBody.setText("Mail body: ");
        textMailBody.setBounds(0, 50, 500, 100);
        //Cuadro en blanco para el body
        JLabel labelMailBody = new JLabel();
        labelMailBody.setBounds(0, 110, 300, 100);
        //Input por parte del usuario -> ingresa body
        JTextField textfieldMailBody = new JTextField();
        textfieldMailBody.setBounds(0, 110, 300, 60);
        //Boton para mandar el correo
        JButton sendNewMail=new JButton("Send Mail"); 
        sendNewMail.setBounds(100,200,100, 30); 
        //Agregando todo a la vista
        window3.add(textMail);
        window3.add(labelMail);
        window3.add(textfieldMail);
        window3.add(textMailSubject);
        window3.add(labelMailSubject);
        window3.add(textfieldMailSubject);
        window3.add(textMailBody);
        window3.add(labelMailBody);
        window3.add(textfieldMailBody);
        window3.add(sendNewMail); 
        window3.setLayout(null);
        window3.setVisible(false); 

        /* 
                GUI NEWCONT
        */
        JFrame window4 = new JFrame("Guarda un nuevo contacto");
        window4.setSize(400, 150);
        //Texto pidiendo new contact
        JLabel textContact = new JLabel();        
        textContact.setText("New contact contact@server: ");
        textContact.setBounds(0, -30, 500, 100);
        //Cuadro en blanco para el new contact
        JLabel labelContact = new JLabel();
        labelContact.setBounds(0, 30, 300, 100);
        //Input por parte del usuario -> ingresa new contact
        JTextField textfieldContact = new JTextField();
        textfieldContact.setBounds(0, 30, 300, 20);
        //Boton para agregar el nuevo contacto
        JButton addContact=new JButton("ADD CONTACT"); 
        addContact.setBounds(100,50, 150, 25); 
        //Se agrega todo
        window4.add(textContact);
        window4.add(labelContact);
        window4.add(textfieldContact);
        window4.add(addContact); 
        window4.setLayout(null);
        window4.setVisible(false);



        /*
            SE COMIENZAN LAS CONEXIONES
        */
        try {
            //el cliente siempre estara escuchando el puerto 1400
            Socket socketServer = new Socket("localhost", Server.clientPort);
            InputStreamReader isr = new InputStreamReader(socketServer.getInputStream());
            BufferedReader in = new BufferedReader(isr);
            // es importante el segundo argumento (true) para que tenga autoflush al hacer print
            PrintWriter out = new PrintWriter(socketServer.getOutputStream(), true);

            //Se pide el usuario
            while(pedirUsuario){
                //Funcion del boton -> obtener los datos para pasarlos al protocolo
                b.addActionListener(e -> {
                    //Se obtienen los parametros
                    String user = textfieldUser.getText();
                    String password = String.valueOf(textfieldPassword.getPassword());
                    String server = textfieldServer.getText();
                    //Se regresa a blanco el cuadro (para que haya mejor UX)
                    textfieldPassword.setText("");
                    textfieldUser.setText("");
                    textfieldServer.setText("");
                    //Interactuando con protocolo y server
                    if (!user.equals("") && !password.equals("") && !server.equals("")) {     
                        out.println(protocol.checkServer(user+"@"+server, password));             
                        //Se manda usuario y contrasena para verificar server
                        out.println(protocol.getUser());
                        out.println(protocol.getServer());
                        out.println(protocol.getPassword());
                        b.setEnabled(false);
                    }
                });
                
                //Si no hay error con el server, se hace LOGIN
                if ((in.readLine()).equals("")){
                    out.println(protocol.LOGIN());
                    //Si no hay error, se deja de pedir usuario@server
                    if ((in.readLine()).equals("")) {
                        if ((in.readLine()).equals("OK LOGIN")) {
                            pedirUsuario = false;
                            window.setVisible(false);
                            window.dispose(); //Destruye el jframe para limpiar el buffer
                        }
                    }
                } 
                b.setEnabled(true);
            }
            window2.setVisible(true);

            //Seguido de ingresar la cuenta, el cliente pide el CLIST
            out.println(protocol.CLIST());
            //Seguido de ingresar la cuenta, el cliente pide los GETNEWMAILS
            out.println(protocol.GETNEWMAILS());

            //CLIST
            clist.addActionListener(e -> {
                out.println(protocol.CLIST()); //Se manda la senal al server
            });

            //GET NEW MAILS
            getMails.addActionListener(e -> {
                out.println(protocol.GETNEWMAILS());
            });

            //SEND MAIL
            sendMail.addActionListener(e -> {
                window2.setVisible(false);
                window3.setVisible(true);
                out.println(protocol.SENDMAIL()); //Se manda la senal al server

                sendNewMail.addActionListener(a -> { 
                    out.println("MAIL TO " + textfieldMail.getText()  + "*"); //Se manda la senal al server
                    out.println("MAIL SUBJECT " + textfieldMailSubject.getText()); //Se manda la senal al server
                    out.println("MAIL BODY " + textfieldMailBody.getText()); //Se manda la senal al server
                    out.println("END SEND MAIL"); //Se manda la senal de final al server
                    //Se regresa a blanco el cuadro (para que haya mejor UX)
                    textfieldMail.setText("");
                    textfieldMailSubject.setText("");
                    textfieldMailBody.setText("");

                    window3.setVisible(false);
                    window2.setVisible(true);
                }); 
            });


            //NEWCONT
            newCont.addActionListener(e -> {
                window2.setVisible(false);
                window4.setVisible(true);
                out.println(protocol.NEWCONT()); //Se manda la senal al server


                addContact.addActionListener(a -> {
                    out.println(textfieldContact.getText()); //Se manda la senal al server
                    //Se regresa a blanco el cuadro (para que haya mejor UX)
                    textfieldContact.setText("");

                    window4.setVisible(false);
                    window2.setVisible(true);
                });                 
            });


            //LOGOUT
            logout.addActionListener(e -> {
                //El cliente pide el CLIST
                out.println(protocol.LOGOUT()); //Se manda la senal al server
            });

            //SE MANTIENE REVISANDO EL CLIENTE SI SE HACE LOGOUT
            while(pedirFuncion){
                String msjDelServer = in.readLine();
                if (msjDelServer.equalsIgnoreCase("off")) {
                    pedirFuncion = false; //Si se cierra sesion, se dejan de pedir comandos
                    //Se borran las ventanas
                    window2.dispose();
                    window3.dispose();
                    window4.dispose();
                    //Se cierra la conexion
                    in.close();
                    out.close();
                    socketServer.close();

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}