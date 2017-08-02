package PingPong.server;

import rmi.RMIException;

public interface PingServerFactory {

	public PingServer makePingServer() throws RMIException;
		

}
