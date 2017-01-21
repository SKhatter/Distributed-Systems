import java.net.*;
import java.util.Scanner;
import java.io.*;

public class Catclient {

   public static void main(String [] args) throws InterruptedException {
	   
	   String serverName = "Catserver";
       String fileName = args[0];
       int port = Integer.parseInt(args[1]);
       //"/home/sumedha/wokspace_new/ServerSocketProgramming/src/String.txt";
       
       try 
       (
    		  Socket client = new Socket(serverName, port);
    		  PrintWriter out = new PrintWriter(client.getOutputStream(), true);
    		  BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
    		  
       )
       
        { 
    	           
              Scanner scanner = new Scanner(new FileReader(fileName));
             
              Long timeStart  = System.currentTimeMillis();
              
              while(System.currentTimeMillis() - timeStart <= 30000){
            	  
            	  out.println("LINE");
            	  
            	     if(scanner.hasNext())   
            	     {
            		  String tempLine = scanner.nextLine();
            		  
            		  if(tempLine.toUpperCase().equals(in.readLine())){
            			  System.out.println("OK");
            		  }
            		  else 
            			  System.out.println("MISSING");
            	     }
            	     
            	     if(!scanner.hasNext())
           			 scanner = new Scanner(new FileReader(fileName));
            	     
            	     Thread.sleep(3000);
            	     
              }
              
        }
                
       catch (UnknownHostException e) {
              System.err.println("Unknown host " + serverName);
              System.exit(1);
          } catch (IOException e) {
              System.err.println("Couldn't get I/O for the connection to " +
                  serverName);
              System.exit(1);
          }
   }
   
}
