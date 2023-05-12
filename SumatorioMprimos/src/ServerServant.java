import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Enumeration;
import java.util.Vector;

public class ServerServant implements ServerRMI {

    Vector<ClientRMI> listaListeners =  new Vector<>();
    private int partialResultsUploaded = 0;
    private static long TotalResult = 0;
    @Override
    public void uploadParcialResult(long result) throws RemoteException {

        TotalResult+=result;
    }
    public void publishWork(long begin, long end, int numProcesses, long intervalSize)throws RemoteException {
        String partialWork = "";
        for (int i = 0; i < numProcesses; i++) {
            long intervalBegin = begin + i * intervalSize;
            long intervalEnd = (i == numProcesses - 1) ? end : intervalBegin + intervalSize;
            String msg = intervalBegin + "-" + intervalEnd;
            partialWork = msg;
            listaListeners.get(i).onMessage(partialWork);
            System.out.println("Message sent to client "+ i + "with content" + partialWork);
        }
    }

    @Override
    public void addListner(ClientRMI listner) throws RemoteException {
        System.out.println("Adding listner : " + listner);
        listaListeners.add(listner);

    }

    @Override
    public void removeListner(ClientRMI listner) throws RemoteException{
        System.out.println("Adding listner : " + listner);
        listaListeners.remove(listner);
    }
    public void notificarListners() throws RemoteException {
        for (Enumeration e = listaListeners.elements(); e.hasMoreElements();){
            ClientRMI listener = (ClientRMI) e.nextElement();
            listener.notifyResultChangedClient(TotalResult);
        }
    }
}
