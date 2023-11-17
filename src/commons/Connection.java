/**
 *
 * @author Alexandre Zeni
 * @author Leonardo Oliani Fernandes
 */

package commons;

public class Connection {

    private String ipAddress;
    private Integer port;

    public Connection(final String ipAddress, final Integer port) {
        setIpAddress(ipAddress);
        setPort(port);
    }

    private void setIpAddress(final String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    private void setPort(final Integer port) {
        this.port = port;
    }

    public Integer getPort() {
        return port;
    }

}
