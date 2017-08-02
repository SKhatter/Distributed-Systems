package rmi;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Set;

public class ActiveThread<T>  extends Thread{

	ServerSocket serverSocket;

	private T server;
	private Class<T> c;
	Set<RMIClientThread> set;
	Skeleton<T> skeleton;


	public ActiveThread(ServerSocket serverSocket,  T server, Class<T> c, Set<RMIClientThread>set, Skeleton<T> skeleton){
		this.serverSocket = serverSocket;
		this.server = server;
		this.c = c;
		this.set = set;
		this.skeleton = skeleton;
	}


	private boolean stop = false;
	
	public boolean isStop() {
		return stop;
	}

	public void setStop(boolean stop) {
		this.stop = stop;
	}

	public void run(){

		try{
			while(true){


				try {

					RMIClientThread r = new RMIClientThread(serverSocket.accept(), server, c, set, skeleton);
					r.start();

				} catch (IOException e) {
					// TODO Auto-generated catch bloc

					if(stop){
						break;
					}			
				}
			}
		}
		finally{

			try {

				serverSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}



	}




}
