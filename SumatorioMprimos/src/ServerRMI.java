import java.rmi.RemoteException;

public interface ServerRMI extends java.rmi.Remote{
    //metodes als quals podran accedir els clients
    public void uploadParcialResult(long result) throws RemoteException;
    public void publishWork(long begin, long end, int numProcesses, long intervalSize) throws RemoteException;

    public void addListner(ClientRMI client) throws RemoteException;

    public void removeListner(ClientRMI client) throws RemoteException;

    public void notificarListners() throws RemoteException;

}
