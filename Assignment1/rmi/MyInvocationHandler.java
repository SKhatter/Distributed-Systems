package rmi;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Arrays;

public class MyInvocationHandler<T> implements InvocationHandler, Serializable {

	Class<T> interfaceType;
	InetSocketAddress inetAddress = null;


	public MyInvocationHandler(Class<T> interfaceType, InetSocketAddress inetAddress) {

		this.interfaceType = interfaceType;
		this.inetAddress = inetAddress;
	}

	public InetSocketAddress getInetAddress() {
		return inetAddress;
	}

	public Class<T> getInterfaceType() {
		return interfaceType;
	}


	public static boolean checkIfMethodIsRemote(Method method){

		Class[] exceptiontypes = method.getExceptionTypes();

		for(Class e : exceptiontypes) {
			if(e.equals(RMIException.class)) {
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings("resource")
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable 
			{

		//if method is not remote
		if(!checkIfMethodIsRemote(method)){

			if (method.equals(Object.class.getMethod("equals", Object.class))) 
			{
				if(args.length == 0) return false;

				Object obj = args[0];

				if(null == obj || !proxy.getClass().equals(obj.getClass()))
					return false;

				InvocationHandler handler = Proxy.getInvocationHandler(obj);
				if(!(handler instanceof MyInvocationHandler) || !Proxy.isProxyClass(obj.getClass()) || !inetAddress.equals(((MyInvocationHandler) handler).inetAddress)) {
					return false;
				}

				return true;
			}


			if(method.getName().equals("hashCode")){

				return interfaceType.hashCode() * inetAddress.hashCode();
			}

			if(method.getName().equals("toString")){
				return "Class Name "+ interfaceType.getCanonicalName()+"Hostname " + inetAddress.getHostName() + "Port Number" + inetAddress.getPort();
			}

			//call local invoke
			return method.invoke(proxy, args);

		}

		//if the method is remote
		else{

			try{
				
				Socket clientSocket = new Socket(inetAddress.getHostName(), inetAddress.getPort());
				ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
				out.flush();

				out.writeObject(method.getName());
				out.writeObject(method.getParameterTypes());
				out.writeObject(args);
				out.flush();


				ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
				Object status = in.readObject();

				if(status.equals("Not Accomplished"))
				{
					Object error = in.readObject();
					throw (Exception) error;

				}

				if(method.getReturnType().equals(Void.TYPE))
					return null;


				Object  r = in.readObject();
				in.close();
				out.close();
				clientSocket.close();

				return r;

			}
			catch(Exception e){
				if (Arrays.asList(method.getExceptionTypes()).contains(e.getClass())){
					throw e;
				}
				else
					throw new RMIException(e);
			}

		}
	}

}
