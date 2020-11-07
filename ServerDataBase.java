import java.sql.*;
import java.util.ArrayList;
/**
 *
 */
public class ServerDataBase {

    // Variables relacionadas al la base de datos
    private Connection dataBase;
    private PreparedStatement prepared;
    private ResultSet result;

    // Variables de acceso a la base de datos
    private final String url, username, password;

    /**
     * Constructor de la base de datos
     * @param url la dirección de la base de datos
     * @param username el nombre de usuario
     * @param password la contraseña del usuario
     */
    public ServerDataBase(String url, String username, String password){
        this.url = url;
        this.username = username;
        this.password = password;
    }

    // SENTENCIAS PARA OBTENER DE LA BASE DE DATOS

    /**
     * Se le manda el user que se esta buscando el base de datos
     * @param username el nombre del usuario
     * @return devuelve al usuario, si es null, entonces no se encontro
     */
    public User getUserData(String username){
        User user = null;

        try{
            getConnection();
            prepared = this.dataBase.prepareStatement("SELECT * FROM users WHERE username = ?");
            prepared.setString(1, username);

            result = prepared.executeQuery();

            if(result.next()){
                user = new User(result.getString("iduser"));
                user.setUsername(username);
                user.setPassword(result.getString("password"));
                user.setStatus(result.getString("status"));
            }

            this.dataBase.close();

        }catch (Exception ignored){ }

        return user;
    }

    /**
     * Se encarga de obtener todos los usuarios que se encuentran en la base de datos
     * @return una lista de los usuarios registrados
     */
    public ArrayList<User> getUsers(){
        ArrayList<User> users = new ArrayList<>();
        User temp;

        try{

            // Obteniendo todos los mails
            getConnection();

            // Mandando el query para obtener los ids de los mails relacionados con el usuario
            prepared = this.dataBase.prepareStatement("SELECT * FROM users");
            result = prepared.executeQuery();

            // Creando los mails con sus ids
            while (result.next()){

                // Llenando la informacion de cada server
                temp = new User(result.getString("idUser"));
                temp.setUsername(result.getString("username"));
                temp.setStatus(result.getString("status"));

                // Metiendolo a la estructura
                users.add(temp);

            }

            this.dataBase.close();

        }catch (Exception e){
            System.out.println(e);
        }

        return users;
    }

    /**
     * Obtiene los contactos del usuario
     * @param id el id del usuario que lo solicita
     * @return devuelve los contactos del usuario
     */
    public ArrayList<Contact> getUserContacts(String id){
        ArrayList<Contact> contacts = new ArrayList<>();
        Contact temp;
        String aux;

        PreparedStatement tempPrepare;
        ResultSet tempResult;

        try{

            // Obteniendo todos los mails
            getConnection();

            // Mandando el query para obtener los ids de los mails relacionados con el usuario
            prepared = this.dataBase.prepareStatement("SELECT * FROM users_contacts WHERE idUser = ?");
            prepared.setString(1, id);

            result = prepared.executeQuery();

            // Creando los mails con sus ids
            while (result.next()){
                aux = result.getString("idContact");


                // Mandando el query para obtener los mails relacionados con el usuario
                tempPrepare = this.dataBase.prepareStatement("SELECT * FROM contacts WHERE idContact = ?");
                tempPrepare.setString(1, aux);

                tempResult = tempPrepare.executeQuery();

                if (tempResult.next()){

                    // Llenando al mail y luego metiendolo en el arraylist
                    temp = new Contact(id);
                    temp.setUsername(tempResult.getString("username"));
                    temp.setServer(tempResult.getString("server"));

                    // Metiendolo a la estructura
                    contacts.add(temp);
                }

            }

            this.dataBase.close();

        }catch (Exception ignored){ }

        return contacts;
    }

    /**
     * Obtiene los mails del usuario
     * @param id el id del usuario que lo solicita
     * @return devuelve los mails que se encuentran en la base de datos
     */
    public ArrayList<Mail> getUserMails(String id){
        ArrayList<Mail> mails = new ArrayList<>();
        Mail temp;
        String aux;

        PreparedStatement tempPrepare;
        ResultSet tempResult;

        try{

            // Obteniendo todos los mails
            getConnection();

            // Mandando el query para obtener los ids de los mails relacionados con el usuario
            prepared = this.dataBase.prepareStatement("SELECT * FROM users_mails WHERE idUser = ?");
            prepared.setString(1, id);

            result = prepared.executeQuery();

            // Creando los mails con sus ids
            while (result.next()){
                aux = result.getString("idMail");


                // Mandando el query para obtener los mails relacionados con el usuario
                tempPrepare = this.dataBase.prepareStatement("SELECT * FROM mails WHERE idMail = ?");
                tempPrepare.setString(1, aux);

                tempResult = tempPrepare.executeQuery();

                if (tempResult.next()){

                    // Llenando al mail y luego metiendolo en el arraylist
                    temp = new Mail(id);
                    temp.setAuthor(tempResult.getString("author"));
                    temp.setServer(tempResult.getString("server"));
                    temp.setMatter(tempResult.getString("matter"));
                    temp.setBody(tempResult.getString("body"));

                    // Metiendolo a la estructura
                    mails.add(temp);
                }

            }

            this.dataBase.close();

        }catch (Exception ignored){ }

        return mails;
    }

    /**
     * Se encarga de obtener todos los servidores que se encuentran en la base de datos
     * @return una lista de los servidores
     */
    public ArrayList<ServerIp> getServers(){
        ArrayList<ServerIp> servers = new ArrayList<>();
        ServerIp temp;

        try{

            // Obteniendo todos los mails
            getConnection();

            // Mandando el query para obtener los ids de los mails relacionados con el usuario
            prepared = this.dataBase.prepareStatement("SELECT * FROM ips");
            result = prepared.executeQuery();

            // Creando los mails con sus ids
            while (result.next()){

                // Llenando la informacion de cada server
                temp = new ServerIp(result.getString("idIPs"));
                temp.setServerName(result.getString("serverName"));
                temp.setIp(result.getString("ip"));

                // Metiendolo a la estructura
                servers.add(temp);

            }

            this.dataBase.close();

        }catch (Exception ignored){ }

        return servers;
    }

    // SENTENCIAS PARA AGREGAR A LA BASE DE DATOS

    /**
     * Se encarga de agregar un nuevo usuario a la base de datos
     * @param newUser el nuevo usuario con todos sus atributos bien definidos
     * @return 0 si hubo un error y 1 si se logro completar
     */
    public Integer addUser(User newUser){
        int result = 0;

        try{
            getConnection();
            prepared = this.dataBase.prepareStatement("INSERT INTO users (username, password, status)" +
                    " VALUES(?,?,?)");
            prepared.setString(1, newUser.getUsername());
            prepared.setString(2, newUser.getPassword());
            prepared.setString(3, "off");

             result = prepared.executeUpdate();

            this.dataBase.close();

        }catch (Exception ignored){ }

        return result;
    }

    /**
     * Se encarga de meter a la base de datos el mail
     * @param newMail es el mail que se le mando a uno de nuestros usuarios
     * @param users una lista de los usuarios a quienes le mandaron
     * @return 0 si hubo un error y 1 si se logro completar
     */
    public Integer addMail(Mail newMail, ArrayList<User> users){
        int result = 0;

        try{
            getConnection();

            prepared = this.dataBase.prepareStatement("INSERT INTO mails (author, server, matter, body)" +
                    " VALUES(?,?,?,?)");
            prepared.setString(1, newMail.getAuthor());
            prepared.setString(2, newMail.getServer());
            prepared.setString(3, newMail.getMatter());
            prepared.setString(4, newMail.getBody());
            prepared.executeUpdate();


            for (User user : users) {
                prepared = this.dataBase.prepareStatement("INSERT INTO users_mails (idUser, idMail) VALUES(?,?)");
                prepared.setInt(1, Integer.parseInt(user.id));
                prepared.setInt(2, Integer.parseInt(newMail.id));
                result = prepared.executeUpdate();
            }

            this.dataBase.close();

        }catch (Exception e){
            System.out.println(e);
        }

        return result;
    }

    /**
     * Se encarga de agregar un nuevo contacto a un usuario
     * @param newContact es el contacto que se desea agregar
     * @param user es al usuario que se desea relacionar
     * @return 0 si hubo un error y 1 si se logro completar
     */
    public Integer addContact(Contact newContact, User user){
        int result = 0;

        try{
            getConnection();
            // Metiendo en la tabla de contactos
            prepared = this.dataBase.prepareStatement("INSERT INTO contacts (username, server) VALUES(?,?)");
            prepared.setString(1, newContact.getUsername());
            prepared.setString(2, newContact.getServer());
            result = prepared.executeUpdate();


            // Metiendolo en la tabla relacional de user y contacts
            prepared = this.dataBase.prepareStatement("INSERT INTO users_contacts (idUser, idContact) VALUES(?,?)");
            prepared.setInt(1, Integer.parseInt(user.id));
            prepared.setInt(2, Integer.parseInt(newContact.id));
            result = prepared.executeUpdate();

            this.dataBase.close();

        }catch (Exception ignored){ }

        return result;
    }

    /**
     * Se encarga de agregar un nuevo server a la base de datos
     * @param newServer un objeto tipo server
     * @return 0 si hubo un error y 1 se se logro completar
     */
    public Integer addServerIP(ServerIp newServer){
        int result = 0;

        try{
            getConnection();
            prepared = this.dataBase.prepareStatement("INSERT INTO ips (serverName, ip)" +
                    " VALUES(?,?)");
            prepared.setString(1, newServer.getServerName());
            prepared.setString(2, newServer.getIp());

            result = prepared.executeUpdate();

            this.dataBase.close();

        }catch (Exception ignored){ }

        return result;
    }

    // SENTENCIAS PARA HACER UPDATE

    /**
     * Se encarga de cambiar el estatus de un usuario de off a on o al reves
     * @param user el usuario al que se le quiere cambiar
     * @return 0 si hubo un error y 1 si se logro completar
     */
    public Integer updateUser(User user){
        int result = 0;

        try{
            getConnection();
            prepared = this.dataBase.prepareStatement("UPDATE users SET status=? WHERE idUser=?");

            // Verificando si esta apagado o encendido
            if (user.getStatus().equals("off")) {
                user.setStatus("on");
            } else {
                user.setStatus("off");
            }

            prepared.setString(1, user.getStatus());
            prepared.setString(2, user.id);
            result = prepared.executeUpdate();
            this.dataBase.close();

        }catch (Exception ignored){  }

        return result;
    }


    // SENTENCIAS PARA SABER EL LARGO DE UNA TABLA

    /**
     * Se encarga de obtener el tamaño de una tabla
     * @param tableName el nombre de la tabla que se desea consultar
     * @return el tamaño en string de la tabla
     */
    public String tableSize(String tableName){
        String size = "0";

        try{
            getConnection();
            prepared = this.dataBase.prepareStatement("SELECT COUNT(*) FROM " + tableName);
            result = prepared.executeQuery();

            if(result.next())
                size = result.getString(1);

            this.dataBase.close();

        }catch (Exception e){
            System.out.println(e);
        }

        return size;
    }

    /**
     * Realiza la conexión de la base de datos MySQL
     */
    private void getConnection(){

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.dataBase = DriverManager.getConnection(this.url, this.username, this.password);

        }catch (Exception ignored){ }

    }

}
