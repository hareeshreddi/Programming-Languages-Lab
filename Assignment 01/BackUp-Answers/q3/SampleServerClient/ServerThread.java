import java.util.*;
import java.util.concurrent.Semaphore;
import java.lang.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.io.*;
import java.net.*;
import java.util.concurrent.*;

class ServerThread extends Thread
{
    Socket s;
    Server server;
    DataInputStream input;
    DataOutputStream output;
    Semaphore sema;
    ServerThread(Semaphore sema, Socket s, Server server)
    {
        this.s = s;
        this.sema = sema;
        this.server = server;
    }

    public void run()
    {
        try{
            input = new DataInputStream(s.getInputStream());
            output = new DataOutputStream(s.getOutputStream());
            //Input and output to/from the server from/to the client 
            String sentence = input.readUTF();
            String a[] = sentence.split(" ");
            //Initialize a new object to store the client requests/orders in an object
            ClientInfo new_client = new ClientInfo(Integer.parseInt(a[0]), Integer.parseInt(a[1]), Integer.parseInt(a[2]), Integer.parseInt(a[3]), Integer.parseInt(a[4]), Integer.parseInt(a[5]));
            //Acquire the semaphore to insert it in to the queue 
            sema.acquire();
            int flag = 0;
            //check the possibility of processing the request
            if(new_client.cookies <= Shared.cookies && new_client.snacks <= Shared.snacks)
                flag = 1;
            if(flag == 1)
            {
                //Decrease the requested value from the shared variables that is the total stock
                Shared.cookies -= new_client.cookies;
                Shared.snacks -= new_client.snacks;
                
                if(Shared.cookies <= Shared.cookies_threshold){
                    Shared.Purchase_List.remove(0);
                    Shared.Purchase_List.add(0, 1);
                    System.out.println("Cookies added into Purchase List");
                }
                if(Shared.snacks <= Shared.snacks_threshold){
                    Shared.Purchase_List.remove(1);
                    Shared.Purchase_List.add(1, 1);
                    System.out.println("Snacks added into Purchase List");
                }
                Shared.getInstance().q.add(new_client);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd HH:mm:ss");
                //Calculate the estimated time
                new_client.estimated_time = (MainServer.calendar.compareTo(new_client.estimated_time.getInstance()) > 0)? MainServer.calendar : new_client.estimated_time.getInstance(); 
                MainServer.calendar = (Calendar)new_client.estimated_time.clone();
                new_client.estimated_time.add(Calendar.MINUTE, 2);
                MainServer.calendar.add(Calendar.MINUTE, new_client.processing_time);
                new_client.estimated_time.add(Calendar.MINUTE, new_client.processing_time);
                //Write the esetimated time back to the client
                output.writeUTF(sdf.format(new_client.estimated_time.getTime())+" is the estimated time at which it will be received");
            }else{
                output.writeUTF("The order is not possible");
            }
            
            sema.release();

        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Exception occured");
        }finally{
            try{
                input.close();
                output.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }

    }
}