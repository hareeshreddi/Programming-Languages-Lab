import java.util.*;
import java.lang.*;
import java.net.ServerSocket;
import java.io.*;
import java.net.*;
import java.util.concurrent.*;

class ClientInfo
{
	//Each attributes show the values given by the client
	public int rollno, roomno, tea, coffee, cookies, snacks;
	public Calendar estimated_time;
	public int processing_time;
	//processing time the time taken to process the request
	//Estimated time is the etimated arrival time
	public ClientInfo(int rollno, int roomno, int tea, int coffee, int cookies, int snacks){
		this.rollno = rollno;
		this.roomno = roomno;
		this.tea = tea;
		this.coffee = coffee;
		this.cookies = cookies;
		this.snacks = snacks;
		processing_time = tea + coffee;
		estimated_time = Calendar.getInstance() ;
	}
	//Constructor overloading to create a copy of the same client info
	public ClientInfo(ClientInfo c){
		this.rollno = c.rollno;
		this.roomno = c.roomno;
		this.tea = c.tea;
		this.coffee = c.coffee;
		this.cookies = c.cookies;
		this.snacks = c.snacks;
		this.estimated_time = c.estimated_time;
		this.processing_time = c.processing_time;
	}
    
}