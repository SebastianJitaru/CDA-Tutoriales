import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
//Clase que implementa los metodos que seran remotos del cliente

public class TemperatureMonitor implements TemperatureListner{
    private static BombillaRMI bombilla; // Objeto remoto.
    // Constructor por defecto para el objeto remoto (TemperaturaMonitor).
    public TemperatureMonitor() throws RemoteException { }// Sin código }
    //en la main el cliente se da de alta
    //busca referncia al objeto remoto del servidor que tiene el metodo para darse de alta
    //de da de alta pasandole al metodo la referencia al objeto que tiene los metodos remotos del CLIENTE
    public static void main(String args[]) {
        try {
            //System.setSecurityManager(new SecurityManager());//Deprecated
            String registry = "localhost";
            if (args.length >=1) { registry = args[0]; }
            String registration = "rmi://" + registry + "/BombillaRMI";
// Localizar el servicio en el registro y obtener el servicio remoto.
            Remote remoteService = Naming.lookup ( registration );
            bombilla = (BombillaRMI) remoteService;
            double reading = bombilla.getTemperature();
            System.out.println ("Temperatura Original: " + reading);
            System.setProperty("java.rmi.server.hostname", registry);
// Crear un monitor y registrarlo como listener del objeto remoto.
            TemperatureMonitor monitor = new TemperatureMonitor();
            TemperatureListner listner = (TemperatureListner) UnicastRemoteObject.exportObject(monitor, 0);
            System.out.println("Invocando servicioBombilla.on()");
            bombilla.on();
            bombilla.addTemperaturaListner(listner);
        } catch (NotBoundException nbe) {
            System.out.println ("No sensors available");
        } catch (RemoteException re) {
            System.out.println ("RMI Error - " + re);
        } catch (Exception e) {
            System.out.println ("Error - " + e);
        }
    }

    @Override
    public void temperatureChanged(int temperature) throws RemoteException {
        System.out.println ("Evento Cambio Temperature: " + temperature);
        System.out.println ("Bombilla Encendida: " + bombilla.isOn());
    }
}
