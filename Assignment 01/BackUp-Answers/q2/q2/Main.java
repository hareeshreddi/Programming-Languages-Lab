import java.util.*; 
import java.lang.*; 
import java.io.*; 
class rollnumberCompare implements Comparator<student>
{
	@Override
	public int compare(student s1,student s2)
	{
		return s1.getrollnumber()-s2.getrollnumber();//assuming file is sorted in ascending order of student roll numbers
	}
}
class nameCompare implements Comparator<student>
{
	@Override
	public int compare(student s1,student s2)
	{
		return (s1.getname()).compareTo(s2.getname());//assuming file is sorted in ascending order of student names
	}
}
class student
{
	private int rollnumber;
	private String name;
	private String 	mail_id;
	private int marks;
	private String teacher;
	public student(int rollnumber,String name,String mail_id,int marks,String teacher)
	{
		this.rollnumber=rollnumber;
		this.name=name;
		this.mail_id=mail_id;
		this.marks=marks;
		this.teacher=teacher;
	}
	public void update_teacher_marks(String teacher,int flag,int marks,int sync)
	{
		//System.out.println(this.teacher);
		if(this.teacher.equals("CC"))//if the student was previously corrected by CC
		{
			if(teacher.equals("TA1") || teacher.equals("TA2"))//current teacher is TA1/TA2
			{
				//with (or) without synchronization
				//marks cannot be changed by TA1/TA2 because the student was previously corrected by CC
				System.out.println("Marks of "+rollnumber+" were not changed because of lesser priority of current executing thread");
			}
			else//current teacher is again CC
			{
				if(flag==1)
				{
					int temp=this.marks;
					this.marks+=marks;
					if(sync==2)//with synchronization
						System.out.println("Marks of "+rollnumber+ " were incremented from "+temp+" to "
							+this.marks+" by "+this.teacher+"\n");
					else//without synchronization
						System.out.println("Marks of "+rollnumber+ " were incremented");
				}				
				else
				{
					int temp=this.marks;
					this.marks-=marks;
					if(sync==2)
						System.out.println("Marks of "+rollnumber+ " were decremented from "+temp+" to "
							+this.marks+" by "+this.teacher+"\n");
					else
						System.out.println("Marks of "+rollnumber+ " were decremented\n");
				}
			}	
		}
		else//if the student was previously corrected by TA1/TA2
		{
			if(teacher.equals("CC"))//current teacher is CC just overwrite CC(as it is having highest priority)
			{
				this.teacher="CC";
			}
			else if(!this.teacher.equals(teacher))//may be previously TA1 and now its TA2 (or) previously TA2 now its TA1
			{
				this.teacher=teacher;//need to update teacher
			}
			// else if(this.teacher.equals(teacher))//if previous teacher and current teacher are same i.e. 
			// {
				//may be previously TA1 and now again TA1 (or) previously TA2 now again TA2
				//no need to change teacher
			//}
			if(flag==1)//when flag is 1 it indicates increment in marks
			{
					int temp=this.marks;
					this.marks+=marks;
					if(sync==2)
						System.out.println("Marks of "+rollnumber+ " were incremented from "+temp+" to "
							+this.marks+" by "+this.teacher+"\n");
					else
						System.out.println("Marks of "+rollnumber+ " were incremented");
			}	
			else
			{
					int temp=this.marks;
					this.marks-=marks;
					if(sync==2)
						System.out.println("Marks of "+rollnumber+ " were decremented from "+temp+" to "
							+this.marks+" by "+this.teacher+"\n");
					else
						System.out.println("Marks of "+rollnumber+ " were decremented");

			}
		}
	}
	public int getrollnumber()  {  return rollnumber;  }
	public String getname()     {  return name; 	   }
	public String getmail_id()  {  return mail_id;	   }
	public int getmarks()		{  return marks; 	   }
	public String getteacher()  {  return teacher; 	   }
}
//For maintaing in Record Level, Updater class is used
class Updater extends Thread
{
	int index,rollnumber,marks,flag,sync;
	String teacher;
	student stud;//current student object
	int record_or_file;//assume 1 for record level and 2 for file level
	public Updater(int index,int marks,int flag,String teacher,int sync,int record_or_file)//record level modification
	{
		this.index=index;
		stud=Main.ar.get(index);
		this.rollnumber=stud.getrollnumber();
		this.marks=marks;
		this.teacher=teacher;
		this.flag=flag;
		this.sync=sync;
		this.record_or_file=record_or_file;
		//start();//Thread is going to run
	}
	public void run()
	{
		if(sync==2)//with synchronization as sync value is 2 
		{
			if(record_or_file==1)//record level
			{
				//synchronized block on current student object is used here
				synchronized(stud)
				{
					System.out.println (Thread.currentThread().getName()+" acquired the lock on student record object with RollNumber "+ 
					rollnumber+ " and running ");
					stud.update_teacher_marks(teacher,flag,marks,sync);
				}
			}
			else//file level
			{
				//synchronized block on entire file is used here
				synchronized(student.class)
				{
					System.out.println (Thread.currentThread().getName()+" acquired the lock on entire file records "+ 
					" and running ");
					stud.update_teacher_marks(teacher,flag,marks,sync);
				}
			}
		}
		else//without synchronization as sync value is 1
		{
			//just call the method without acquiring any lock
			stud.update_teacher_marks(teacher,flag,marks,sync);
		}
		
	}
}

public class Main
{
	private BufferedReader br;//using BufferReader to read the data from file
	static ArrayList<student> ar = new ArrayList<student>();//our data structre
	private int flag=0;//to check whether data is properly loaded from the input file
	//cont denotes whether user presses yes/no in the console
	static String teacher1,teacher2,cont;//to take input from console
	//flag denotes increment/decrement
	static int rollnumber1,flag1,mark1;//to take input from console
	static int rollnumber2,flag2,mark2;
	//sync denotes whether with synchronization or without
	static int sync;

	public static void main(String[] args){
		Main obj = new Main(); 
		obj.loaddata();
		if(obj.flag==1)//Details were properly inserted
		{
			obj.takeinput();//takes and stores the input
			Scanner in = new Scanner(System.in);
    		while(true)
    		{
    			System.out.print("Choose One:: 1.Without Synchronization 2.With Synchronization(Enter 1/2):");
    			sync=in.nextInt();
    			in.nextLine();
    			//now update/process the input
    			if(rollnumber1==rollnumber2)//record level modification
    				obj.record_level_modification();
    			else//with synchronization
    				obj.file_level_modification();
    			System.out.print("Do You Want to continue?(Enter yes/no):");
    			cont=in.nextLine();
    			if(cont.equals("yes")||cont.equals("YES"))
    				continue;
    			else break;
    		}
		}
	}
	void record_level_modification()
	{
		//get the student object index in ArrayList
		System.out.println("\n---Record Level Modification---");
		int listlength=ar.size(),index=-1;
		for(int i=0;i<listlength;i++)
		{
			if(ar.get(i).getrollnumber() == rollnumber1)
			{
				//ar.get(i).updateteacherwithmarks(teacher1,flag1,mark1);
				index=i;
				break;
			}
		}
		if(index!=-1){
			int record_or_file=1;
			//if(sync==1){
				//System.out.println("---No Synchronization does not guarantee any specific order");
			//}
			//Create two threads by calling Updater constructor
			Updater u1=new Updater(index,mark1,flag1,teacher1,sync,record_or_file);//send first student details
			Updater u2=new Updater(index,mark2,flag2,teacher2,sync,record_or_file);//send second studnet details
			u1.setName("First Thread"); u2.setName("Second Thread");
			u1.start();
			u2.start();
			//finally files are created after both threads are completed
			//WAIT for threads to end
			try
			{ 
			u1.join(); 
			u2.join(); 
			createnewfiles.create_files();
			updateoldfile.updatefile_Stud_Info();
			System.out.println("\n"+"Current marks of "+rollnumber1+" are changed to "+ar.get(index).getmarks()+"\n");
			} 
			catch(Exception e) 
			{ 
			System.out.println("---Interrupted---"); 
			} 
			
		}
		else
		{ 
			System.out.println("Roll Number "+rollnumber1+" was not found in the file");
			System.out.println("So please Exit and Try again by inputting correct RollNumber");
		}
	}
	void file_level_modification()
	{
		System.out.println("---File Level Modification---");
		//firstly get the both students objects indices in ArrayList
		int index1=-1,index2=-1,listlength=ar.size();
		for(int i=0;i<listlength;i++)
		{
			if(ar.get(i).getrollnumber() == rollnumber1)
				index1=i;
			else if(ar.get(i).getrollnumber() == rollnumber2)
				index2=i;
		}
		if(index1!=-1 && index2!=-1)
		{
			//Create two threads by calling Updater constructor
			int record_or_file=2;
			Updater u1=new Updater(index1,mark1,flag1,teacher1,sync,record_or_file);//send first student details
			Updater u2=new Updater(index2,mark2,flag2,teacher2,sync,record_or_file);//send second studnet details
			u1.setName("First Thread"); u2.setName("Second Thread");
			u1.start();
			u2.start();
			//finally files are created after both threads are completed
			//WAIT for threads to end
			try
			{ 
			u1.join(); 
			u2.join(); 
			createnewfiles.create_files();
			updateoldfile.updatefile_Stud_Info();
			System.out.print("\n"+"Current marks of "+rollnumber1+" are changed to "+ar.get(index1).getmarks());
			System.out.print("\n"+"Current marks of "+rollnumber2+" are changed to "+ar.get(index2).getmarks()+"\n\n");
			} 
			catch(Exception e) 
			{ 
			System.out.println("---Interrupted---"); 
			}
		}
		else
		{
			if(index1==-1){
				System.out.println("Roll Number "+rollnumber1+" was not found in the file");}
			else if(index2==-1){
				System.out.println("Roll Number "+rollnumber2+" was not found in the file");}
			System.out.println("So please Exit and Try again by inputting correct RollNumber");
		}
		
	}
	void takeinput()
	{
		System.out.println("Now Enter the query---->");
		System.out.print("Enter first Teacher Name(Enter CC/TA1/TA2):");
		Scanner in = new Scanner(System.in);
		teacher1 = in.nextLine();
		System.out.print("Enter first Student RollNumber:");
		rollnumber1=in.nextInt();
		System.out.print("Update Marks: 1.Increase 2.Decrease(Enter 1/2):");
		flag1=in.nextInt();
		if(flag1==1)
			System.out.print("Marks to add:");
		else
			System.out.print("Marks to deduct:");
		mark1=in.nextInt();
		in.nextLine();
		System.out.print("Enter second Teacher Name(CC/TA1/TA2):");
		teacher2 = in.nextLine();
		System.out.print("Enter second Student RollNumber:");
		rollnumber2=in.nextInt();
		System.out.print("Update Marks: 1.Increase 2.Decrease(Enter 1/2):");
		flag2=in.nextInt();
		if(flag2==1)
			System.out.print("Marks to add:");
		else
			System.out.print("Marks to deduct:");
		mark2=in.nextInt();
	}
	void loaddata()//to load the data into the ArrayList
	{
		File file=new File(".\\Stud_Info.txt");
		try{
			br = new BufferedReader(new FileReader(file)); 
			String st;int i=1; 
  			while ((st = br.readLine()) != null) 
  			{
  				String[] splited = st.split("\\s*,\\s*");//I assumed that each line has student details separated with commas
	 			int rollnumber=Integer.parseInt(splited[0]),marks=Integer.parseInt(splited[3]);
	 			//assumed student details are stored as RollNumber,Name,MailID,Marks and Teacher in order
				ar.add(new student(rollnumber,splited[1],splited[2],marks,splited[4]));
				//System.out.println("Loaded Student "+i+" details");
				i++;
			}
    		System.out.println("----All the Students details were loaded from Stud_Info.txt----");
    		br.close();
    		flag=1;
		}
		catch(FileNotFoundException ex)
    	{
    		System.out.println("Student_Info.txt file was not found");//code to run when exception occurs
    		System.out.println("Failed to load students details");
    		System.out.println("Create the file and try again please!!");
    	}
    	catch(NullPointerException ne)
    	{
    	 	System.out.println(ne.getMessage()+" Some NullPointerException");
    	}
    	catch(IOException e)
    	{
   	    	System.out.println(e.getMessage()+" Some IOException");
   	    	//System.out.println(e.printStackTrace());
    	}
	}
}
class createnewfiles
{
	public static void create_files() throws IOException
	{
		//details were copied into new ArrayList
		ArrayList<student>arraylist = new ArrayList<student>(Main.ar);
		// Writing into File Sorted_Roll.txt
		FileWriter filewriter1 = new FileWriter(".\\Sorted_Roll.txt");
		PrintWriter printwriter1 = new PrintWriter(filewriter1);
		int listlength=arraylist.size();
		Collections.sort(arraylist,new rollnumberCompare());//sorting the arraylist based on RollNumbers
		for(int i=0;i<listlength;i++)
		{
			//assumed student details are stored as RollNumber,Name,MailID,Marks and Teacher in order
			String buf=arraylist.get(i).getrollnumber()+","+arraylist.get(i).getname()+","+arraylist.get(i).getmail_id()+
			","+arraylist.get(i).getmarks()+","+arraylist.get(i).getteacher();
			printwriter1.print(buf);
			if(i!=listlength-1)
				printwriter1.print("\n");
		}
		printwriter1.close();
		//Completed Writing into File Sorted_Roll.txt

		// Writing into File Sorted_Name.txt
		FileWriter filewriter2 = new FileWriter(".\\Sorted_Name.txt");
		PrintWriter printwriter2 = new PrintWriter(filewriter2);
		Collections.sort(arraylist,new nameCompare());//sorting the arraylist based on Names
		for(int i=0;i<listlength;i++)
		{
			//assumed student details are stored as RollNumber,Name,MailID,Marks and Teacher in order
			String buf=arraylist.get(i).getrollnumber()+","+arraylist.get(i).getname()+","+arraylist.get(i).getmail_id()
			+","+arraylist.get(i).getmarks()+","+arraylist.get(i).getteacher();
			printwriter2.print(buf);
			if(i!=listlength-1)//adding new line
				printwriter2.print("\n");
		}
		printwriter2.close();
		//Completed Writing into File Sorted_Name.txt
		System.out.println("Both the Files Sorted_Roll.txt and Sorted_Name.txt were created/modified");
	}
}
class updateoldfile//to update the Stud_Info file everytime
{
	public static void updatefile_Stud_Info() throws IOException
	{
		ArrayList<student>arraylist = new ArrayList<student>(Main.ar);	
		int listlength=Main.ar.size();
		FileWriter filewriter = new FileWriter(".\\Stud_Info.txt");
		PrintWriter printwriter = new PrintWriter(filewriter);
		for(int i=0;i<listlength;i++)
		{
			//assumed student details are stored as RollNumber,Name,MailID,Marks and Teacher in order
			String buf=arraylist.get(i).getrollnumber()+","+arraylist.get(i).getname()+","+arraylist.get(i).getmail_id()
			+","+arraylist.get(i).getmarks()+","+arraylist.get(i).getteacher();
			printwriter.print(buf);
			if(i!=listlength-1)//adding new line
				printwriter.print("\n");
		}
		printwriter.close();
		System.out.println("Stud_Info.txt was updated with new marks of the students.You can check Stud_Info.txt file!!");
	}
}
//Thread.currentThread().getId()
// 	for(String name : splited){
					// 	System.out.println(name);
					// }
//System.out.println(Integer.parseInt(splited[0])+" "+splited[1]+splited[2]+Integer.parseInt(splited[3])+splited[4]);
    		//System.out.println(teacher1);
    		//System.out.println(rollnumer1);
// public void updatemarks(int flag,int marks)
	// {
	// 	if(flag==1)//when flag is 1 it indicates increment
	// 		this.marks+=marks;
	// 	else
	// 		this.marks-=marks;
	// }
// }
		// catch(Exception e){
		// 	System.out.println("Exception is caught");
		// }