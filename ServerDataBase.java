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
            }

            this.dataBase.close();

        }catch (Exception ignored){ }

        return user;
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
