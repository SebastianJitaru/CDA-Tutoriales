
import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

// Servidor
public class BombillaRMIServer extends BombillaRMIServant
{
	public BombillaRMIServer() throws RemoteException {
	}

	public static void main(String args[])
	{
		System.out.println("Cargando Servicio RMI");
		
		try
		{
				//System.setSecurityManager(new SecurityManager());//Deprecated
				// Cargar el servicio.
				BombillaRMIServant servicioBombilla = new BombillaRMIServant();

				//Exportar el objeto de la implementacion stub del interfase
				BombillaRMI bombilla = (BombillaRMI) UnicastRemoteObject.exportObject(servicioBombilla,0); //Covertirlo a Unicast... pq ahora que es dinamico ya no extiende (mirar diapos)

				Registry registry = LocateRegistry.getRegistry();
				registry.rebind("BombillaRMI", bombilla);
				System.err.println("Server ready");
				Thread thread = new Thread (servicioBombilla);
				thread.start();

				// Comprobar si se ha expecificado un registro (arg[0])
				//String registry1 = "localhost";
				//if (args.length >= 1)
				//	registry = args[0];
				
				// Crear la URL del registro.
				//String registro ="rmi://" + registry + "/BombillaRMI";
				
				// Registrar el servicio
				//Naming.rebind(registry, servicioBombilla);
		}
		catch (RemoteException re)
		{
			System.err.println("Remote Error - " + re);
		}
		catch (Exception e)
		{
			System.err.println("Error - " + e);
		}
	}
}