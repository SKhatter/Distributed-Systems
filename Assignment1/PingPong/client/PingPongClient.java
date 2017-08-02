package PingPong.client;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

import PingPong.server.PingServerFactory;
import PingPong.server.PingServer;
import rmi.*;

public class PingPongClient {


	public static void main(String[] args)throws RMIException
	{

		InetSocketAddress address = new InetSocketAddress(args[0], Integer.parseInt(args[1]));
		PingServerFactory factory = Stub.create(PingServerFactory.class, address);
		PingServer stub;
		stub = factory.makePingServer();

		int fail = 0;
		int totalTests = 4;
		for (int i = 0; i < totalTests; i++) 
		{
			String result  = stub.pong(i);
			String expectedString = "Pong" + String.valueOf(i);
			
			if (!result.equals(expectedString)) {
				fail++;
			}
		}
		System.out.println(" Tests Completed: "+ totalTests+" Tests failed: "+fail);
	}



}
