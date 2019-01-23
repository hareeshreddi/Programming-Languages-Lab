import java.util.concurrent.locks.ReentrantLock;
import java.util.*;
import java.lang.*;
import java.io.*;

class Sock {
  int collected;
  int color;
  int index;
  ReentrantLock l = new ReentrantLock();
  Sock(int a, int index){
    this.color =a;
    collected = 0;
    this.index = index;
  }
  Sock(Sock x){
    //Constructor overloading to get a copy of the sock
    this.color = x.color;
    this.collected = x.collected;
  }
    public boolean getLock(){
      //Function to get the lock on a sock
      if(l.tryLock()){
        return true;
      }else{
        return false;
      }
    }
    public void unLock(){
      l.unlock();
    }
}


