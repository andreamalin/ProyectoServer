public class Protocolo{
	private String user;
	private String serverName;
	private String password;

	//Funcion para cliente
	public String LOGIN(String ingreso, String password){ //Recibe usuario@servidor
		this.user = ingreso.split("@")[0];
		this.serverName = ingreso.split("@")[1];
		this.password = password;
		
		return checkServer(); //Se revisa si existe el server
	}
	//Funcion para la DB en servidor
	public String checkServer(){
		//Se verifica que el server este en la db
		if (true) {
			return "LOGIN " + user + " password"; 
		} 
		return "SEND ERROR 104 " + user + "@" + serverName; //Esto lo retorna el server si no existe
	}
	//Funcion para la DB en servidor
	public String checkAccount(){
		//Se revisa si el usuario esta en la db
		//Retorna warning o lo deja entrar
		//El OK Login lo debe de retornar el server
		if (true) {
			return "OK LOGIN"; //Se marca como Logged In en la db 
		} else {
			return "WARNING";
		}
		
	}
	//Funcion para cliente
	public String CLIST(){
		return "CLIST "+user;
	}
	//Funcion para la DB en servidor
	public String showCLIST(){
		//Me retornas todos los contactos Server: info
		return "andrea@server1 *";
	}
	//Funcion para cliente
	public String GETNEWMAILS(){
		return "GETNEWMAILS "+user;
	}
	//Funcion para la DB en servidor
	public String showNewMails(){
		//Me retornas todos los correos Server: info
		return "usuario@server asunto cuerpo";
	}
}