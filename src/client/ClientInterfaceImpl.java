/**
 *
 * @author Alexandre Zeni
 * @author Leonardo Oliani Fernandes
 */

package client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ClientInterfaceImpl extends UnicastRemoteObject implements ClientInterface {

    private LocalTime clientTime;

    public ClientInterfaceImpl() throws RemoteException {}

    @Override
    public LocalTime getClientTime() throws RemoteException {
        return clientTime;
    }

    @Override
    public void setClientTime(final LocalTime clientTime) throws RemoteException {
        final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        System.out.println("Time updated: " + dateTimeFormatter.format(clientTime));
        this.clientTime = clientTime;
    }

}
