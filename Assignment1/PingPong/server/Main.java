package PingPong.server;

import java.net.InetSocketAddress;
import java.net.UnknownHostException;

import rmi.RMIException;
import rmi.Skeleton;

public class Main {

	 public static void main(String[] args) throws RMIException, UnknownHostException {
		 
	        PingServerFactory pingServerFactory = new PingServerFactoryImpl();
	        InetSocketAddress inetSocketAddress = new InetSocketAddress(Integer.parseInt(args[0]));
	        
	        Skeleton<PingServerFactory> skeleton = new Skeleton<>(PingServerFactory.class, pingServerFactory, inetSocketAddress);
	        
	        skeleton.start();
	        
	        System.out.println("The Skeleton with host name '" + skeleton.getAddress().getHostName() +"' is running on port Number "+ skeleton.getAddress().getPort());
	    }
}
