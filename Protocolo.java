import java.util.ArrayList; 

public class Protocolo{
	public boolean checkServer(String server){
	//if server in db 
		return true;
	}

	public String checkAccount(String username, String password){
		//Se revisa si el usuario esta en la db
		//Retorna warning o lo deja entrar
		//El OK Login lo debe de retornar el server
		return "OK LOGIN"; //Se marca como Logged In en la db 
	}

	public ArrayList<String> getContactList(){
		//Me retornas todos los contactos
		return ArrayList<String> vacio = new ArrayList<String>();
	}


}