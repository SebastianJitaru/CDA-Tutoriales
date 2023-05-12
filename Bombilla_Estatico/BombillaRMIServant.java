
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.Enumeration;
import java.util.Random;
import java.util.Vector;


// Implementacion Servidor
public class BombillaRMIServant implements BombillaRMI, Runnable
{
	private static final long serialVersionUID = 1;
	
	private boolean luzOn;
	private int temperature;
	private Vector<TemperatureListner> listaListeners = new Vector<TemperatureListner>();
	private int waste;
	
	// Constructor.
	public BombillaRMIServant() throws RemoteException
	{
		// Asignar valor por defecto = off
		setBombilla(false);
		// Assignar valor por defecto a 0
		setTemperature(0);
		// Assignar valor por defecto a 0
		setConsume(0);
	}

	// Metodo remoto -> Enciende la Bombilla.
	public void on() throws RemoteException
	{
		// Encender Bombilla.
		setBombilla(true);
	}

	// Metodo remoto -> Apagar la Bombilla.	
	public void off() throws RemoteException
	{
		// Apagar Bombilla.
		setBombilla(false);
	}

	// Metodo remoto -> Devuelve el estado de la Bombilla.	
	public boolean isOn() throws RemoteException
	{
		return getBombilla();
	}
	
	// Metodo local -> Modificar el estado de la bombilla.
	public void setBombilla(boolean valor)
	{
		luzOn = valor;
	}
	
	// Metodo local -> Devovler el estado de la bombilla.
	public boolean getBombilla()
	{
		return(luzOn);
	}

	public void setTemperature(int newtemperature){
		temperature = newtemperature;
	}

	@Override
	public int getTemperature() {
		return this.temperature;
	}

	@Override
	public void setConsume(int waste) {
		this.waste = waste;
	}

	@Override
	public int getConsumo() {
		return this.waste;
	}

	@Override
	public void addTemperaturaListner(TemperatureListner listner) throws RemoteException {
		System.out.println("Adding listner : " + listner);
		listaListeners.add(listner);

	}

	@Override
	public void removeTemperatureListner(TemperatureListner listner) throws RemoteException {
		System.out.println("Removing listner : " + listner);
		listaListeners.remove(listner);

	}

	private void notificarListners(){
		for (Enumeration e = listaListeners.elements(); e.hasMoreElements();){
			TemperatureListner listener = (TemperatureListner) e.nextElement();
			//Intentar notificar al listner
			try{
				listener.temperatureChanged(temperature);
			}catch (RemoteException re){
				System.out.println (" Listener not accessible, removing listener -" + listener);
				// Listener no accesible -> Eliminar el listener de la lista.
				listaListeners.remove( listener );
			}
		}
	}

	@Override
	public void run() {
		Random r = new Random();
		for(;;){
			try{
				int duration = r.nextInt() %1000 +2000;
				if(duration <0) duration = duration *-1;
				Thread.sleep(duration);
			}catch (InterruptedException ie){}
			int num = r.nextInt();
			System.out.println("Changing Temperature:" + num);
			if(num < 0 ) {temperature += 100;}
			else{temperature -= 100;}
			notificarListners();
		}
	}
}
