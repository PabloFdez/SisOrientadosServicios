package es.upm.fi.sos.t3.usermanagement.client;
import es.upm.fi.sos.t3.usermanagement.client.UserManagementWSStub.*;

public class UserManagementClient {
	public static void main(String[] args) throws Exception {
		
		int aux = 0;
		
		//Creamos entidades del servicio
		UserManagementWSStub man1 = new UserManagementWSStub();
		UserManagementWSStub man2 = new UserManagementWSStub();
		System.out.print("\n");
		System.out.print("  ------  Inicio sistema de pruebas del sistema  ------  \n");
		System.out.print("\n");
		
		//Arrancamos las entidades
		man1._getServiceClient().engageModule("addressing");
		man1._getServiceClient().getOptions().setManageSession(true);
		
		man2._getServiceClient().engageModule("addressing");
		man2._getServiceClient().getOptions().setManageSession(true);
		
		//Creamos los usuarios que interactuan con el servicio
		User adm = new User();
		User us1 = new User();
		User us2 = new User();
		User us3 = new User();
		User us4 = new User();
		User us6 = new User();
		
		System.out.print(" Creando usuarios de prueba: \n");
		adm.setName("admin");
		System.out.print(" Usuario: admin [Creado] \n");
		adm.setPwd("admin");
		us1.setName("pepe");
		System.out.print(" Usuario: pepe [Creado] \n");
		us1.setPwd("pepe");
		us2.setName("paco");
		System.out.print(" Usuario: paco [Creado] \n");
		us2.setPwd("paco");
		us3.setName("pepa");
		System.out.print(" Usuario: pepa [Creado] \n");
		us3.setPwd("pepa");
		us4.setName("paca");
		System.out.print(" Usuario: paca [Creado] \n");
		us4.setPwd("paca");
		us6.setName("aux");
		System.out.print(" Usuario: aux [Creado] \n");
		us6.setPwd("aux");
		
		System.out.print("\n");
		System.out.print("\n");
		System.out.print("  ------  Ejecución de las pruebas  ------  \n");
		System.out.print("          Numero de pruebas: 11    \n");
		System.out.print("\n");
		
		//Prueba login admin
		if(man1.login(adm).getResponse()){
			System.out.print("1.- Admin hace login: OK\n");
			aux++;
		}
		
		//Prueba loging de una cuenta ya logeada
		if(man1.login(adm).getResponse()){
			System.out.print("2.- Login de la misma cuenta ya logeada: OK\n");
			aux++;
		}
		
		//Prueba login cuando ya hay un usuario
		if(man1.addUser(us1).getResponse()){
			if(!man1.login(us1).getResponse()){
				System.out.print("3.- Impide login de otro usuario cuando el Admin está conectado: OK\n");
				aux++;
			}
		}
		
		//Prueba un mismo usuario con varias sesiones concurrentes
		if(man2.login(adm).getResponse()){
			System.out.print("4.- Un mismo usuario tiene varias sesiones activas concurrentemente: OK\n");
			aux++;
		}
		man2.logout();
		
		//Prueba añadir N usuarios
		int nUs=0;
		
		System.out.print("5.- Añadir nuevos usuarios al sistema:\n");
		while(nUs <= 9){
			String nombrePrueba = String.valueOf(nUs);
			String pwdPrueba = String.valueOf(nUs);
			
			User us5 = new User();
			us5.setName(nombrePrueba);
			us5.setPwd(pwdPrueba);
			
			if(man1.addUser(us5).getResponse()){
				
				System.out.print("Usuario numero "+(nUs+1)+" añadido: OK\n");
			
				}
			nUs++;
			
		}aux++;
		
		//Un usuario NO admin intenta añadir un usuario
		man1.logout();
		man1.login(us2);
			if(!man1.addUser(us3).getResponse()){
				System.out.print("6.- Usuario NO admin no puede añadir a un usuario: OK\n");
				aux++;
			}
		man1.logout();
		man1.login(adm);
		
		//Prueba añadir un usuario ya añadido
		if(!man1.addUser(us1).getResponse()){
			System.out.print("7.- Admin no puede añadir un usuario ya registrado : OK\n");
			aux++;
		}
		
		//Prueba borrar un usuario del sistema
		if(man1.addUser(us2).getResponse()){
			//Primero añadimos a paco y guardamos su nombre
			Username nomAux = new Username();
			nomAux.setUsername(us2.getName());
			if(man1.removeUser(nomAux).getResponse()){
				System.out.print("8.- Admin elimina a un usuario: OK\n");
				aux++;
			}
		}
		
		//Prueba borrar a un usuario no registrado
		Username nomAux2 = new Username();
		nomAux2.setUsername(us4.getName());
		if(!man1.removeUser(nomAux2).getResponse()){
			System.out.print("9.- No se permite borrar a un usuario no registrado: OK\n");
			aux++;
		}
		
		//Prueba admin autoborrarse
		Username nomAux3 = new Username();
		nomAux3.setUsername(adm.getName());
		if(!man1.removeUser(nomAux3).getResponse()){
			System.out.print("10.- Admin no puede auto-borrarse: OK\n");
			aux++;
		}
				
		//Prueba cambiar contraseña
		PasswordPair pwd = new PasswordPair();
		pwd.setOldpwd(us1.getPwd());
		pwd.setNewpwd("nueva");
		man1.logout();
		man1.login(us1);
		if(man1.changePassword(pwd).getResponse()){
			System.out.print("11.- Cambio de contraseña efectuado: OK\n");
			aux++;
		}
		
		man1.logout();
		man2.logout();
		System.out.print("\n");
		System.out.print("    Número de pruebas superadas: "+aux+"\n");
		System.out.print("  ------  ¡TEST FINALIZADO!  ------  \n");

		
	}
}
