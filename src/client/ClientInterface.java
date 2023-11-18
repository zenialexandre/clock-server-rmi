/**
 *
 * @author Alexandre Zeni
 * @author Leonardo Oliani Fernandes
 */

package client;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalTime;

public interface ClientInterface extends Remote {

    public LocalTime getClientTime() throws RemoteException;

    public void setClientTime(final LocalTime clientTime) throws RemoteException;

}
