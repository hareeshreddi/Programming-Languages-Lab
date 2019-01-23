import java.util.*;
import java.lang.*;
import java.net.ServerSocket;
import java.text.SimpleDateFormat;
import java.io.*;
import java.net.*;
import java.util.concurrent.*;


class Shared extends Thread
{
    public static int cookies_threshold = 10, snacks_threshold = 10;
	public static Shared s;
	public static int cookies = 100, snacks = 100;
    public static ArrayList<ClientInfo> q;
    public static Semaphore sema;
    public static ArrayList<Integer> Purchase_List;
	Shared(Semaphore sema)
	{
        this.sema = sema;
        q = new ArrayList<ClientInfo>();
        Purchase_List = new ArrayList<Integer>(2);
        Purchase_List.add(0);
        Purchase_List.add(0);
	}
	public static Shared getInstance(){
		if(s == null){
			s = new Shared(MainServer.sema);
			return s;
		}else{
			return s;
		}
    }
    
    public void run()
    {
        while(true)
        {
            try{
                //Process the request using the semaphore 
                MainServer.sema.acquire();
                if(!Shared.getInstance().q.isEmpty()){
                //Get the client info and make a copy of the object and use this object to process the order
                //System.out.println(" Hello acquired!! ");
                ClientInfo cl = new ClientInfo(Shared.getInstance().q.get(0));
                MainServer.sema.release();
                //Release the semaphore
                //Processing the order is shown here as waiting. Here for the sake of simplicity the sleep time is chosen in seconds because minutes would be too long to wait
                //while we are checking the functioning of the code
                Thread.sleep((cl.processing_time)*1000);
                //use sema to get the queue again to delete the completed order
                MainServer.sema.acquire();
                //Remove the order from the queue
                Shared.getInstance().q.remove(0);
                //Releae the semaphore
                MainServer.sema.release();
                //Print the delivered statement
                System.out.print(cl.rollno + " order is delivered at ");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd HH:mm:ss");
                System.out.println(sdf.format(cl.estimated_time.getTime()));
                //System.out.println(sdf.format(Calendar.getInstance()));
                }else{
                    //If the order is not possible then just release the semaphore
                    MainServer.sema.release();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
