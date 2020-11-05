public class Protocolo{
	String user;
	String serverName;
	String password;

	public String checkServer(String server){
	//if server in db 
		if (true) {
			return "true"; //Se marca como Logged In en la db 
		} 
		return "false";
	}

	public String checkAccount(String username, String password){
		//Se revisa si el usuario esta en la db
		//Retorna warning o lo deja entrar
		//El OK Login lo debe de retornar el server
		if (true) {
			return "OK LOGIN"; //Se marca como Logged In en la db 
		} else {
			return "WANING";
		}
		
	}

	public String getContactList(){
		//Me retornas todos los contactos
		return "";
	}


}