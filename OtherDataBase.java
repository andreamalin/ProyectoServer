public class OtherDataBase extends DataBase{

    /**
     * Constructor de la base de datos
     * @param url la dirección de la base de datos
     * @param username el nombre de usuario
     * @param password la contraseña del usuario
     */
    public OtherDataBase(String url, String username, String password){
        super(url, username, password);
    }


    /**
     * Se le manda el user que se esta buscando el base de datos
     * @param username el nombre del usuario
     * @return devuelve al usuario, si es null, entonces no se encontro
     */
    public User getUser(String username){
        User user = null;

        try{
            getConnection();
            prepared = this.dataBase.prepareStatement("SELECT * FROM users WHERE username = ?");
            prepared.setString(1, username);

            result = prepared.executeQuery();

            if(result.next()){
                user = new User(result.getString("iduser"));
                user.setUsername(username);
                user.setStatus(result.getString("status"));
                user.setServer(result.getString("server"));
            }

            this.dataBase.close();

        }catch (Exception ignored){ }

        return user;
    }
    
}
