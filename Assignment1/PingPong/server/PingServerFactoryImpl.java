package PingPong.server;

import java.io.Serializable;

import rmi.RMIException;

public class PingServerFactoryImpl implements PingServerFactory, Serializable{

	@Override
	public PingServer makePingServer() throws RMIException {
		// TODO Auto-generated method stub
		return new PingServerImpl();
	}

}
