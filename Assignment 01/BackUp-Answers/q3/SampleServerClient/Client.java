import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.*;
import java.awt.event.*;

public class Client 
{
	static JLabel rolllable;
	static JTextField rollnumber;
	static JLabel roomlabel;
	static JTextField roomnumber;
	static JLabel Tealabel;
	static JTextField Tea;
	static JLabel Coffeelabel;
	static JTextField Coffee;
	static JLabel Cookielabel;
	static JTextField Cookies;
	static JLabel Snackslabel;
	static JTextField Snacks;
	static JButton submit;
	static Socket socket = null;
	static DataInputStream input = null;
	static DataOutputStream out = null;

	public Client(String address,int port)
	{
		// establish a connection 
        try
        { 
            socket = new Socket(address, port); 
            System.out.println("Connected!!"); 
            // takes input from terminal 
            input  = new DataInputStream(socket.getInputStream()); 
            // sends output to the socket 
            out    = new DataOutputStream(socket.getOutputStream()); 
        } 
        catch(UnknownHostException u) 
        { 
        	u.printStackTrace();
        	System.out.println("UnknownHostException");
        } 
        catch(IOException e) 
        { 
        	e.printStackTrace();
            System.out.println("IO Exception"); 
        } 
	}
	private static void addcomponents(JPanel panel) {
		panel.setLayout(null);
	
		rolllable = new JLabel("Roll Number");
		rolllable.setBounds(10,10,100,25);
		panel.add(rolllable);

		rollnumber = new JTextField(20);
		rollnumber.setBounds(100,10,160,25);
		panel.add(rollnumber);

		roomlabel = new JLabel("Room Number");
		roomlabel.setBounds(10,40,100,25);
		panel.add(roomlabel);

		roomnumber = new JTextField(20);
		roomnumber.setBounds(100,40,160,25);
		panel.add(roomnumber);

		Tealabel = new JLabel("No.of Teas");
		Tealabel.setBounds(10,70,100,25);
		panel.add(Tealabel);

		Tea = new JTextField(20);
		Tea.setBounds(100,70,160,25);
		panel.add(Tea);

		Coffeelabel = new JLabel("No.of Coffees");
		Coffeelabel.setBounds(10,100,100,25);
		panel.add(Coffeelabel);

		Coffee = new JTextField(20);
		Coffee.setBounds(100,100,160,25);
		panel.add(Coffee);

		Cookielabel = new JLabel("No.of Cookies");
		Cookielabel.setBounds(10,130,100,25);
		panel.add(Cookielabel);

		Cookies = new JTextField(20);
		Cookies.setBounds(100,130,160,25);
		panel.add(Cookies);

		Snackslabel = new  JLabel("No.of Snacks");
		Snackslabel.setBounds(10,160,100,25);
		panel.add(Snackslabel);

		Snacks = new JTextField(20);
		Snacks.setBounds(100,160,160,25);
		panel.add(Snacks);

		submit = new JButton("Submit");
		submit.setBounds(160,200,100,25);
		panel.add(submit);
	}
	public static void main(String[] args) 
	{
		Client client = new Client("127.0.0.1",7);
		JFrame frame = new JFrame("Client");
		frame.setSize(500, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel();
		frame.add(panel);
		addcomponents(panel);
		frame.setVisible(true);
		try{
		buttonclick(frame);
		}
		catch(UnknownHostException e)
		{
			e.printStackTrace();
			System.out.println("UnknownHostException occurred");
		}
		catch(IOException i)
		{
			i.printStackTrace();
			System.out.println("IOException occurred");
		}
	}
	public static void buttonclick(JFrame frame) throws UnknownHostException, IOException{
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try{
					//get info of roll number, room number, No.of Teas, No.of Coffees, No.of Cookies and No.of Snacks 
					String srollnumber   =  getrollnumber();
					String sroomnumber = getroomnumber();
					String sTea = getTeanumber();
					String sCoffee = getCoffeenumber();
					String sCookie = getCookienumber();
					String sSnacks = getSnacksnumber();
 					String comb = srollnumber+" "+sroomnumber+" "+sTea+" "+sCoffee+" "+sCookie+" "+sSnacks;
					//out.writeUTF("helllo!!");
					out.writeUTF(comb);
					String response = input.readUTF();
					System.out.println("Client received "+response+" from Main server");
					// String response2 = input.readUTF();
					// System.out.println(response2);
				}
				catch(UnknownHostException u) 
		        { 
		        	u.printStackTrace();
		        	System.out.println("UnknownHostException");
		        } 
		        catch(IOException e) 
		        { 
		        	e.printStackTrace();
		            System.out.println("IO Exception"); 
		        } 
				finally{
					try{
		        		input.close();out.close();socket.close();
		        		frame.dispose();
		                System.out.println("Connection Closed");
		            }
		            catch(IOException e)
		            {
		            	e.printStackTrace();
		            	System.out.println("IOException occurred");
		            }
    			}
    		}
		});
	}
	//various functions to get the details which user has given input
	static String getrollnumber(){
		return rollnumber.getText().trim();
	}
	static String getroomnumber(){
		return roomnumber.getText().trim();
	}
	static String getTeanumber(){
		return Tea.getText().trim();
	}
	static String getCoffeenumber(){
		return Coffee.getText().trim();
	}
	static String getCookienumber(){
		return Cookies.getText().trim();
	}
	static String getSnacksnumber(){
		return Snacks.getText().trim();
	}
}