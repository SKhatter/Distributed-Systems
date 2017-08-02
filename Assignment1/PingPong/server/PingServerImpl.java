package PingPong.server;

import java.io.Serializable;

import rmi.RMIException;

public class PingServerImpl implements PingServer, Serializable{

	@Override
	public String pong(int num) throws RMIException {
		// TODO Auto-generated method stub
		return "Pong" + String.valueOf(num);
	}

}
