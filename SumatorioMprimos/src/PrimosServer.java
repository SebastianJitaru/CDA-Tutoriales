import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class PrimosServer extends ServerServant {
    public static void main(String args[]) {
        if (args.length < 2) {
            System.out.println("Uso: PrimosServer <intervalo_final_sum> <#trabajos>");
            System.exit(1);
        }
        long begin = 0;
        long end = Integer.parseInt(args[0]); //limite superior del rango a sumar
        int numProcesses = Integer.parseInt(args[1]); //Numero de tareas a generar
        long intervalSize = (end-begin) / numProcesses;

        //wait for a input enter
        try {
        ServerServant ServerService =  new ServerServant();

        ServerRMI server =  (ServerRMI) UnicastRemoteObject.exportObject(ServerService,0);

        Registry registry = LocateRegistry.getRegistry();
        registry.rebind("ServerPrimos", server);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Press enter to continue when all clients ready...");
        scanner.nextLine();
        server.publishWork(begin,end,numProcesses,intervalSize);
        System.out.println("Press enter to notify clients of Final Result");
        scanner.nextLine();
        server.notificarListners();
        } catch (RemoteException re) {
            System.out.println("RMI Error - " + re);
        } catch (Exception e) {
            System.out.println("Error - " + e);
        }
    }
}
