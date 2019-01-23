import java.util.*;
import java.lang.*;

class Main {
  
  	public int collectedSocks;
  	
  	public static void main(String[] args) throws Exception{
    
    Sock[] listofsocks = new Sock[10];
    //Give the socks ransom colors and print them 
    //Here the sock index is given from 0 to 9 for 10 socks
    //and also for 4 threads the indexing is given to be 0-indexing that is from 0 to 3
    for(int i=0;i<10;i++)
    {
        Random rand = new Random();
        int x = rand.nextInt(4)+1;
        listofsocks[i] = new Sock(x, i);
        System.out.println("color of "+i+" is "+x+" with index "+listofsocks[i].index);
    }
    
    Robot[] robots = new Robot[4];
   	//Initialize the machine 
    MatchingMachine machine = new MatchingMachine().getInstance();
    
      for(int i=0;i<4;i++)
      {
        if(machine.q.size() < 10)
        {
          //Initialie the robotic arms and start the threads
          robots[i] = new Robot(listofsocks, i);
          robots[i].start();
        }
      }
      //Start the machine thread
      machine.start();
  }
}