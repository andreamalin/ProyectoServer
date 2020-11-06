/**
 *
 */
public class Dao {

    private static ServerDataBase dataBase = null;

    /**
     * Se encarga de instanciar la base de datos
     * @return
     */
    public static ServerDataBase getDataBase(){

        if(dataBase == null){
            dataBase = new ServerDataBase("3", "4k", "kj");
        }

        return dataBase;
    }

}
