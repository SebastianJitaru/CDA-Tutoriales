
import java.rmi.*;
import java.rmi.server.RemoteRef;

public interface BombillaRMI extends java.rmi.Remote
{
	public void on() throws RemoteException;
	public void off() throws RemoteException;
	public boolean isOn() throws RemoteException;
	public void setTemperature(int temperature) throws RemoteException;
	public int getTemperature()  throws RemoteException;
	public void setConsume(int waste)  throws RemoteException;
	public int getConsumo()  throws RemoteException;
	//Metodos para que los clientes puedan darse de alta y de baja
	public void addTemperaturaListner(TemperatureListner listner) throws RemoteException;
	public void removeTemperatureListner(TemperatureListner listner) throws RemoteException;
}
