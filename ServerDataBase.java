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

    public ServerDataBase(String url, String username, String password){
        this.url = "jdbc:mysql://localhost:3306/ProyectoServer?useTimezone=true&serverTimezone=UTC";
        this.username = "root";
        this.password = "A2020C#_mP#tq";
    }

    /**
     * Se le manda el user que se esta buscando el base de datos
     * @param username el nombre del usuario
     * @return 'ERROR' si no lo encuentra o la contraseña e id si lo encuentra
     */
    public String[] getUserData(String username){
        String[] info = new String[2];
        info[0] = "ERROR";

        try{
            getConnection();
            prepared = this.dataBase.prepareStatement("SELECT * FROM users WHERE username = ?");
            prepared.setString(1, username);

            result = prepared.executeQuery();

            if(result.next()){
                info[0] = result.getString("password");
                info[1] = result.getString("iduser");
            }

            this.dataBase.close();

        }catch (Exception ignored){ }

        return info;
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
     * Obtiene los contactos del usuario
     * @param id el id del usuario que lo solicita
     * @return devuelve los contactos del usuario
     */
    public void getUserContacts(String id){

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
