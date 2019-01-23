import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.*;

class Robot extends Thread {
  	Sock sock;
    int sockIndex;
    Sock[] a;
    int index;
  	@Override
  	public void run()
    {
      	
          try {
            selectSock();
          }catch(Exception e){
            e.printStackTrace();
          }finally{
          }
      	
    }
  
  Robot(Sock a[], int i)
  {
    this.a = a;
    sock = null;
    sockIndex = -1;
    index = i;
  }
  public void selectSock() throws Exception
  {

    while(MatchingMachine.q.size() < Main.noOfSocks)
    {
      Random rand = new Random();
      int x = rand.nextInt(Main.noOfSocks);
      ReentrantLock l = new ReentrantLock();
      //Select a sock if it is not accessed already and there is no lock on the sock object
      if(a[x].getLock())
      {
        try{
          if(a[x].collected == 0)
          {
            sock = new Sock(a[x]);
            sockIndex = x;
            a[x].collected = 1;
            sock.collected = 0;
            sock.index = x;
            System.out.println(this.index+" Thread selected sock "+x);
            //Add the sock in to the machine
            MatchingMachine.getInstance().Push(sock);
          }else{
            System.out.println(this.index+" Thread tried to access "+x+" but access denied");
          }
        }catch(Exception e){
          e.printStackTrace();
        }finally{
          //After accessing the sock and adding the sock, release the lock
          a[x].unLock();
        }
      }
    }
  }

}
