import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientRMI extends Remote {
    void onMessage(String message) throws RemoteException;
    void notifyResultChangedClient(long FinalResult) throws RemoteException;
}
