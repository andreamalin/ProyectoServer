public class Pr {

    public static void main(String[] args){
        ServerDataBase data = Dao.getServerDataBase();
        System.out.println(data.deleteMails("1"));

    }

}
