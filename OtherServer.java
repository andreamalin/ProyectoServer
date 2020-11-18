public class OtherServer {

    public static void main(String[] args){
        OtherDataBase dataBase = Dao.getOtherDataBase();

        String message = "", response = "";
        User temp;

        // Obitiene el nombre del server al cual se quiere buscar en el dns
        temp = dataBase.getUser(message);

        if(temp != null){
            response = "FOUND";
            // Manda que lo encontro y espera a que le manden el correo

            // Lo agrega a esta base de datos
        }
        else
            response = "NOT FOUND";
    }

}
