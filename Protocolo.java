import java.util.ArrayList; 

public class Protocolo{
	private ArrayList<String> contactos = new ArrayList<String>();
	private ArrayList<String> emails = new ArrayList<String>();
	private String user;
	private String serverName;
	private String password;

	//SIMULANDO LA BASE DE DATOS
	private void simularDB(){
		contactos.add("pepito@115.01.3");
		contactos.add("juan@101.80.9");
		contactos.add("pablito@115.01.3");

		emails.add("pablito@115.01.3 Hola Hola, como estas? Nos vemos pronto");
		emails.add("juan@101.80.9 Salgamos Hola, te invito a mi fiesta el viernes");
	}

	//Funcion para cliente
	public String LOGIN(String ingreso, String password){ //Recibe usuario@servidor
		simularDB(); //Simulando base de datos
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
		//Me retorna todos los contactos Server: info
		if (contactos.size() > 0) {
			return contactos.remove(0);
		} else {
			return contactos.remove(0) + " *";
		}
	}
	//Funcion para cliente
	public String GETNEWMAILS(){
		return "GETNEWMAILS "+user;
	}
	//Funcion para la DB en servidor
	public String showNewMails(){
		//Me retorna todos los correos Server: info
		if (correos.size() > 0) {
			return correos.remove(0);
		} else {
			return correos.remove(0) + " *";
		}
	}
}