import java.util.*;
import java.lang.*;
import java.net.ServerSocket;
import java.io.*;
import java.net.*;
import java.util.concurrent.*;
import java.util.Calendar;

public class MainServer
{

	public static Date d = new Date();
	public static Calendar calendar = Calendar.getInstance();
	//To print the estimated time of arrival of the order Calendar is used
	public static Semaphore sema = new Semaphore(1);
	//Semaphore that is used by all the threads in the system is declared as public static 
	public static void main(String[] args) {
		Shared s = new Shared(sema).getInstance();
		s.start();
		//The Thread which processes the requests/orders is started
		Server server = new Server();
		server.startServer();
		//Start the server to listen to the requests/orders
	}
}