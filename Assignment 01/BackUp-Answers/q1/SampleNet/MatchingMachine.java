import java.util.*; 
import java.lang.*;
import java.io.*;

class MatchingMachine extends Thread{
  
  int color1;
  int color2;
  int color3;
  int color4;
  
  public static MatchingMachine m ;
  public static LinkedList<Sock> q;
  public static int matchedSocks;
  public MatchingMachine()
  {
    color1 = -1;
    color2 = -1;
    color3 = -1;
    color4 = -1;
    matchedSocks = 0;
    q = new LinkedList<Sock>();
  }

  public static MatchingMachine getInstance()
  {
    //To get the instance of the machine 
    //Get a static instance since it is to be accessed by all the robotic arms
    if(m == null){
      m = new MatchingMachine();
      matchedSocks = 0;
      return m;
    }else{
      return m;
    }
  }

  public void run()
  {

    while(MatchingMachine.matchedSocks <= 10)
    {
      if(q.size() != 0){
        Random rand = new Random();
        int x = rand.nextInt(q.size());
        //Match the sock when a sock of one color gets in to the matching machine and we already have one ock of same color
        if(q.get(x).collected == 0)
        {
          MatchingMachine.matchedSocks++;
          int color = q.get(x).color;
          switch(color)
          {
            case 1:
              if(color1 != -1){
                System.out.println("Matcher matched socks "+q.get(color1).index+" and "+q.get(x).index);
                color1  = -1;
                q.get(x).collected = 1;
              }else{
                color1 = x;
                q.get(x).collected = 1;
              }
              break;
            case 2:
              if(color2 != -1){
                System.out.println("Matcher matched socks "+q.get(color2).index+" and "+q.get(x).index);
                q.get(x).collected = 1;
                color2 = -1;
              }else{
                color2 = x;
                q.get(x).collected = 1;
              }
              break;
            case 3:
              if(color3 != -1){
                System.out.println("Matcher matched socks "+q.get(color3).index+" and "+q.get(x).index);
                q.get(x).collected = 1;
                color3  = -1;
              }else{
                color3 = x;
                q.get(x).collected = 1;
              }
              break;
            case 4:
              if(color4 != -1){
                System.out.println("Matcher matched socks "+q.get(color4).index+" and "+q.get(x).index);
                q.get(x).collected = 1;
                color4  = -1;
              }else{
                color4 = x;
                q.get(x).collected = 1;
              }
              break;
            default:
              System.out.println("This case never comes");
          }
        }
        if(MatchingMachine.matchedSocks == 10)
          break;
      }
    }
  }

  public synchronized void Push(Sock s)
  {
    //Add the sock to the shared heap in a synchronized way
    q.add(s);
  }
  
  
}