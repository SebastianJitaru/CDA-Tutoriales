import java.rmi.RemoteException;

public interface TemperatureListner extends java.rmi.Remote{
//Estos son los metodos a los cuales el servidor pude acceder del cliente1
    public void temperatureChanged (int temperature) throws RemoteException;

}
