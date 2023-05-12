
import java.rmi.*;

public class BombillaRMIClient
{
	public static void main(String args[])
	{
		System.out.println("Buscar el servicio BombillaRMI");
		
		try
		{
			// Comprobar si se ha especificado la direccion del servicio de registros
			String registry = "localhost";
			if (args.length >=1)
				registry = args[0];
				
			// Formatear la url del registro
			String registro ="rmi://" + registry + "/BombillaRMI";
			
			// Buscar el servicio en el registro.
			Remote servicioRemoto = Naming.lookup(registro);
			
			// Convertir a un interfaz
			BombillaRMI servicioBombilla = (BombillaRMI) servicioRemoto;
			
			// Encender la bombilla
			System.out.println("Invocando servicioBombilla.on()");
			servicioBombilla.on();
			
			// Mirar si el estado ha cambiado
			System.out.println("Estado bombilla: " + servicioBombilla.isOn() );
			
			// Ahorrar energica -> Apagar la bombilla
			System.out.println("Invocando servicioBombilla.off()");
			servicioBombilla.off();
			
			// Mirar si el estado ha cambiado
			System.out.println("Estado bombilla: " + servicioBombilla.isOn() );

			//Miramos la temperatura inicial de la bombilla
			System.out.println("Temperatura inicial bombilla: " + servicioBombilla.getTemperature() );

			//Seteamos la temperatura a 100
			System.out.println("Invocando servicioBombilla.setTemperature");
			servicioBombilla.setTemperature(100);

			//Miramos la temperatura de la bombilla
			System.out.println("Temperatura final bombilla: " + servicioBombilla.getTemperature() );

			//Miramos el consumo incial
			System.out.println("Consumo inicial " + servicioBombilla.getConsumo());

			//Cambio el comsumo
			System.out.println("Invocando servicioBombilla.setTemperature ");
			servicioBombilla.setConsume(25);

			//Miramos el consumo final
			System.out.println("Consumo final " + servicioBombilla.getConsumo());

		}
		catch (NotBoundException nbe)
		{
			System.err.println("No existe el servicio de bombilla en el registro!");
		}
		catch (RemoteException re)
		{
			System.err.println("Error Remoto - " + re);
		}
		catch (Exception e)
		{
			System.err.println("Error - " + e);
		}		
	}
}