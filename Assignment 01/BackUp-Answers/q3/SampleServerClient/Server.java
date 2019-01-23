import java.util.*;
import java.lang.*;
import java.net.ServerSocket;
import java.io.*;
import java.net.*;
import java.util.concurrent.*;
class Server
{
    
    public static Semaphore sema;
	public void startServer()
	{
		Socket s = null;
		ServerSocket ss = null;
		System.out.println("Server Listening ........");
		// Start the server socket
		try{
			ss = new ServerSocket(7);
		}catch(Exception e){
      		System.out.println("Server error");
		}
        Semaphore sema = MainServer.sema;
        
		while(true)
		{
			//The server needs to keep listening to the orders made by the clients all the time. So it is done using a while loop
			try{
                s = ss.accept();
				System.out.println("Connection Established");
                ServerThread st = new ServerThread(sema, s, this);
                st.start();
			}catch(Exception e){
                //e.printStackTrace();
                //System.out.println("Exception caught");
          }
		}

	}
}