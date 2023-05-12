import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class PrimosClient implements ClientRMI {

    private static ServerRMI server;

    public PrimosClient() {
    }
    public static void main(String args[]){
        try{
            String registry = "localhost";
            if(args.length >= 1){registry = args[0];}
            String registration = "rmi://" + registry + "/ServerPrimos";
// Localizar el servicio en el registro y obtener el servicio remoto.
            Remote remoteService = Naming.lookup(registration);
            server = (ServerRMI) remoteService;
// Crear un cliente y registrarlo
            PrimosClient client = new PrimosClient();
            ClientRMI remoteClient = (ClientRMI) UnicastRemoteObject.exportObject(client, 0);
            server.addListner(remoteClient);
        }catch (NotBoundException nbe) {
            System.out.println ("No sensors available");
        } catch (RemoteException re) {
            System.out.println ("RMI Error - " + re);
        } catch (Exception e) {
            System.out.println ("Error - " + e);
        }
    }
    @Override
    public void onMessage(String message) throws RemoteException {
        System.out.println("This is the message recieved: " + message);
        String[] parts = message.split("-");
        long lower = Integer.parseInt(parts[0]);
        long higher = Integer.parseInt(parts[1]);
        server.uploadParcialResult(SumadorPrimos.calcularSumaPrimos(lower,higher));
    }

    @Override
    public void notifyResultChangedClient(long FinalResult) throws RemoteException{
        System.out.println("Final result is : " + FinalResult);
    }
}
