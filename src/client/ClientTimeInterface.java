/**
 *
 * @author Alexandre Zeni
 * @author Leonardo Oliani Fernandes
 */

package client;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDateTime;

public interface ClientTimeInterface extends Remote {

    public LocalDateTime getTime() throws RemoteException;

    public void setTime(final LocalDateTime time) throws RemoteException;

}
