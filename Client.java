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
        boolean pedirRecibidor = true;

        try {
            //el cliente siempre estara escuchando el puerto 1400
            Socket socketServer = new Socket("localhost", Server.clientPort);
            InputStreamReader isr = new InputStreamReader(socketServer.getInputStream());
            BufferedReader in = new BufferedReader(isr);
            // es importante el segundo argumento (true) para que tenga autoflush al hacer print
            PrintWriter out = new PrintWriter(socketServer.getOutputStream(), true);


            //Se manda a llamar el GUI
            JFrame window = new JFrame("Ingrese su cuenta");
            //Texto pidiendo usuario
            JLabel textUser = new JLabel();        
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
            JTextField textfieldPassword= new JTextField();
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


            //Se pide el usuario
            while(pedirUsuario){
                //Funcion del boton -> obtener los datos para pasarlos al protocolo
                b.addActionListener(e -> {
                    //Intent intent = new Intent(this,Client.class);
                    //Se obtienen los parametros
                    String user = textfieldUser.getText();
                    String password = textfieldPassword.getText();
                    String server = textfieldServer.getText();
                    //Se regresa a blanco el cuadro (para que haya mejor UX)
                    textfieldPassword.setText("");
                    textfieldUser.setText("");
                    textfieldServer.setText("");
                    //Interactuando con protocolo y server
                    out.println(protocol.checkServer(user+"@"+server, password));             
                    //Se manda usuario y contrasena para verificar server
                    out.println(protocol.getUser());
                    out.println(protocol.getServer());
                    out.println(protocol.getPassword());
                });
                //Si no hay error con el server, se hace LOGIN
                
                if ((in.readLine()).equals("")){
                    out.println(protocol.LOGIN());
                    //Si no hay error, se deja de pedir usuario@server
                    if ((in.readLine()).equals("")) {
                        if ((in.readLine()).equals("OK LOGIN")) {
                            pedirUsuario = false;
                            window.setVisible(false);
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
                //Si es mandar mail se piden las demas cosas al usuario
                if (mensaje.equalsIgnoreCase("SEND MAIL")) {
                    //Se piden los remitentes
                    while(pedirRecibidor){ 
                        System.out.print("Mail recipient contact@server: "); //Se pide al usuario que ingrese contact@server
                        String contact = scan.nextLine();
                        out.println("MAIL TO " + contact); //Se manda la senal al server
                        if (contact.contains("*")) { //Es el ultimo
                            pedirRecibidor = false;                            
                        }
                    }
                    pedirRecibidor = true; //Se regresa a true por si manda otro mensaje
                    System.out.print("Mail subject: "); //Se pide al usuario que ingrese asunto del correo
                    String subject = scan.nextLine();
                    out.println("MAIL SUBJECT " + subject); //Se manda la senal al server
                    System.out.print("Mail body: "); //Se pide al usuario que ingrese asunto del correo
                    String body = scan.nextLine();
                    out.println("MAIL BODY " + body); //Se manda la senal al server
                    out.println("END SEND MAIL"); //Se manda la senal de final al server
                } else if (mensaje.equalsIgnoreCase("NEWCONT")) {
                    System.out.print("New contact contact@server: "); //Se pide al usuario que ingrese contact@server    
                    String contacto = scan.nextLine();
                    out.println(contacto); //Se manda la senal al server
                    
                } else if (mensaje.equalsIgnoreCase("LOGOUT")) {
                    out.println(mensaje); //Se manda la senal al server
                } else { //Si no es ninguna de las anteriores, es error
                    System.out.println("INVALID COMMAND ERROR");
                }
                //Se lee la senal del server
                String msjDelServer = in.readLine();
                if (msjDelServer.equalsIgnoreCase("off")) {
                    pedirFuncion = false; //Si se cierra sesion, se dejan de pedir comandos
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