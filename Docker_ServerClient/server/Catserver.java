import java.net.*;
import java.util.Scanner;

import java.io.*;
public class Catserver extends Thread {
   
   private ServerSocket serverSocket;
   private String fileName;
   
   public Catserver(String fileName,  int port) throws IOException {
      serverSocket = new ServerSocket(port);  
      this.fileName = fileName;
   }

   public void run() {
      while(true) {
         try(
                 Socket clientSocket = serverSocket.accept();
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             )
   
             {
       
        	 System.out.println("Waiting for client on port " + 
            serverSocket.getLocalPort() + "...");
            
            System.out.println("Connected to " + clientSocket.getRemoteSocketAddress());
            
            Scanner scanner = new Scanner(new FileReader(fileName));
             
            String outputLine;
            
            String inputLine = in.readLine();
            
            	while(inputLine != null && inputLine.equals("LINE") && scanner.hasNext()){    		
            	
                    outputLine = scanner.nextLine();
                    out.println(outputLine.toUpperCase());
              	    
                    if(!scanner.hasNext()){
                    	scanner = new Scanner(new FileReader(fileName));
                    }
                    
                    inputLine = in.readLine();
                    
            	}	
            	
            	
            clientSocket.close();
            System.out.println("Client Socket closed");
            
         }catch(SocketTimeoutException s) {
            System.out.println("Socket timed out!");
            break;
         }catch(IOException e) {
            e.printStackTrace();
            break;
         }
         
      }
   }
   
   public static void main(String [] args) {
      int port = Integer.parseInt(args[1]);
      String fileName =  args[0]; 
    		  //"/home/sumedha/wokspace_new/ServerSocketProgramming/src/String.txt";
    
      try {
         Thread t = new Catserver(fileName, port);
         t.start();
      }catch(IOException e) {
         e.printStackTrace();
      }
   }
}
