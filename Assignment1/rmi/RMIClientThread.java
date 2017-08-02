package rmi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Arrays;
import java.util.Set;

public class RMIClientThread<T> extends Thread {

	private Socket clientSocket = null;
	private T server;
	private Class<T> c;
	private Set<RMIClientThread> set;
	private Skeleton<T> skeleton;


	public RMIClientThread(Socket socket, T server, Class<T> c,  Set<RMIClientThread> set, Skeleton<T> skeleton){

		this.clientSocket = socket;
		this.server = server;
		this.c = c;
		this.set = set;
		this.skeleton = skeleton;
	}


	public  boolean representRemoteInterface(Class returnType){

		Class[] interfaceList = c.getInterfaces();

		if (returnType.equals(c.getName()))
			return true;
		for (Class c : interfaceList) {
			if (returnType.equals(c.getName()))
				return true;
		}
		return false;
	}



	public void run(){

		set.add(this);
		ObjectOutputStream out = null;
		ObjectInputStream in = null;

		
		try	{	
			out = new ObjectOutputStream(clientSocket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(clientSocket.getInputStream());
			
			String methodName = (String) in.readObject();
			Class paramsType [] = (Class []) in.readObject();
			Object args[] = (Object []) in.readObject();

			Method m = c.getMethod(methodName, paramsType);
			Object result = null;

			try{
				result = m.invoke(server, args);

				out.writeObject("Accomplished");

				Class returnType = m.getReturnType();

				if(!returnType.equals(Void.TYPE) && !representRemoteInterface(returnType)){

					out.writeObject(result);
				}
			}


			catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				try {
					out.writeObject("Not Accomplished");
					out.writeObject(e.getTargetException());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}

		}	catch(Exception e){

			skeleton.service_error(new RMIException(e));

		}

		finally{
			
			set.remove(this);

			try{
				if(out!= null){
					out.flush();
					out.close();	
				}
				if(in != null){
					in.close();
				}

				clientSocket.close();
			}
			catch(IOException e){

				e.printStackTrace();
			}


		}


	}
}



