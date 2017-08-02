package PingPong.server;

import java.io.Serializable;

import rmi.RMIException;

public interface PingServer extends Serializable{
	
	public String pong(int num) throws RMIException;

}
