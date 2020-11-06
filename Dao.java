import java.io.*;
/**
 *
 */
public class Dao {

    private static ServerDataBase dataBase = null;
    private static String url, username, password;

    /**
     * Se encarga de instanciar la base de datos
     * @return una referencia de la base de datos
     */
    public static ServerDataBase getDataBase(){

        // Obtiene la referencia si no hay errores
        if(dataBase == null){
            try {
                readFile();
                dataBase = new ServerDataBase(url, username, password);
            } catch (Exception e){
                dataBase = null;
            }
        }

        return dataBase;
    }

    /**
     * Se encarga de leer el archivo con los datos de la base de datos, el archivo se debe de llamar
     * 'DB_info.txt', debe de contar con tres lineas.
     * La primera linea es el nombre del Schema de la base de datos. La segunda linea es el usuario
     * que administra esta base de datos, de preferencia que sea el 'root' por comdida. Para finalizar
     * se debe de colocar la contraseña del usuario.
     */
    private static void readFile(){
        String[] aux = new String[3];
        url = "jdbc:mysql://localhost:3306/";
        try{
            FileReader file = new FileReader("DB_info.txt");
            BufferedReader buffer = new BufferedReader(file);
            for (int i = 0; i < 3; i++)
                aux[i] = buffer.readLine();

            url += aux[0] + "?useTimezone=true&serverTimezone=UTC";
            username = aux[1];
            password = aux[2];

        } catch (Exception ignored){  }

    }

}
