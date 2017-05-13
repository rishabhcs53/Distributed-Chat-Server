import java.awt.*;
import java.awt.event.*;
import java.lang.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.JOptionPane;

public class User1_Q extends Frame implements ActionListener,Runnable
{
 //Declarations
  int bye1=0;
 Button b;
 TextField tf;
 TextArea ta;
 static Socket clientsocket=null;
  static PrintStream outToserver = null; 
  static BufferedReader in_from_server=null;
  static BufferedReader inputline=null;
  static boolean closed = false;
  static Thread client_thread;
  static Thread client_thread1;
Button b1;
Button b2;
Button n1;
TextField tg1;
TextField tf2;
TextField tf3;
static String username="";
static String password="";
static String result="";



static User1_Q client;
 static User1_Q client1;





 public void hh()
 {
  Frame f=new Frame("Chatting");//Frame for Client
  f.setLayout(new FlowLayout());//set layout
  f.setBackground(Color.orange);//set background color of the Frame
  b=new Button("Send");//Send Button
  n1=new Button("Add_Friend");
  n1.addActionListener(this);
  b.addActionListener(this);//Add action listener to send button.
  f.addWindowListener(new W1());//add Window Listener to the Frame
  tf=new TextField(15);
  ta=new TextArea(12,20);
  tg1=new TextField(15);
  ta.setBackground(Color.cyan);
  f.add(tf);//Add TextField to the frame
  f.add(b);//Add send Button to the frame
  f.add(ta);//Add TextArea to the frame
  f.add(n1);
  f.add(tg1);



  setFont(new Font("Arial",Font.BOLD,20));
  f.setSize(200,200);//set the size
  f.setVisible(true);
  f.setLocation(100,300);//set the location
  f.validate();
 }
 
public void create()
{
   Frame f1=new Frame("Account");//Frame for account
    f1.setLayout(new FlowLayout());//set layout
     f1.setBackground(Color.blue);//set background color of the Frame
     b1=new Button("Create Account");//Send Button
     b2=new Button("Log In");
    b1.addActionListener(this);//Add action listener to send button.
    b2.addActionListener(this);//Add action listener to send button.
    f1.addWindowListener(new W1());//add Window Listener to the Frame
    tf2=new TextField(15);
    tf3=new TextField(15);
    f1.add(tf2);//Add TextField to the frame
     f1.add(tf3);//Add TextField to the frame
  f1.add(b1);//Add send Button to the frame
  f1.add(b2);
  tf2.setText("Username");
  tf3.setText("Password");
   setFont(new Font("Arial",Font.BOLD,20));
  f1.setSize(200,200);//set the size
  f1.setVisible(true);
  f1.setLocation(100,300);//set the location
  f1.validate();
}







 private class W1 extends WindowAdapter
 {
  public void windowClosing(WindowEvent we)
  {
   System.exit(0);
  }
 }
 





 public void actionPerformed(ActionEvent ae)
 {
  if(ae.getSource()==b)
  {
         if(clientsocket!=null&& outToserver !=null)
  {
    System.out.println("fffdsaaa11");

    try{
      client_thread=new Thread(client);
    client_thread.start();
   
   if(!closed)
   {
     String s=tf.getText();
     outToserver.println(s); 
   }
   else
   {
   outToserver.close();
    in_from_server.close();
    clientsocket.close();
    System.exit(0);
    }

  }
  catch(Exception e)
{
    
}
}

}
else if(ae.getSource()==n1)
{
   if(clientsocket!=null&& outToserver !=null)
  {
    try
    { 
      client_thread1=new Thread(client);
    client_thread1.start();


      if(!closed)
   {
       String s1=tg1.getText();
     outToserver.println(s1); 
   }
   else
   {
   outToserver.close();
    in_from_server.close();
    clientsocket.close();
    System.exit(0);
    }


    }
    catch(Exception e)
    {

    }

  
  }
        
}

else if(ae.getSource()==b1)
{
       outToserver.println("Sign_Up");
       username=tf2.getText();
       password=tf3.getText();
        outToserver.println(username+" "+password);
         try
          {
          String res=in_from_server.readLine();
          JOptionPane.showMessageDialog(null, res);
        }
        catch(Exception e)
        {
           JOptionPane.showMessageDialog(null, "Account not created");
        }

}
else if(ae.getSource()==b2)
{
  outToserver.println("Log_in");
  username=tf2.getText();
  password=tf3.getText();
  outToserver.println(username+" "+password);

           try{

                result=in_from_server.readLine();
                System.out.println(result);
              }
             catch(Exception e)
            {
                  System.out.println("ggg");
            }

                   
if(result.equals("Yes"))
  {
      JOptionPane.showMessageDialog(null, "Logged_In");
      hh();
  }
 else
  {
    JOptionPane.showMessageDialog(null, "Wrong username or password try again");
    
}
}
}
 

  public void run()
 {
   Thread t = Thread.currentThread();
      String name = t.getName();
      System.out.println("name=" + name);
  String client_sentence;

  
 
  try{

    
    while((client_sentence = in_from_server.readLine()) !=null)
    {
      ta.append(client_sentence+"\n");
      if(client_sentence.equals("Bye"))
      {
        System.out.println("derrr");
        bye1++;
      break;
    }

    }

    if(bye1==1)
    {
    System.out.println("derrrwwwwww");
    closed=true;
    System.out.println(closed);
      outToserver.close();
    in_from_server.close();
    clientsocket.close();
    System.exit(0);
 }
}
 catch (IOException e) {
      System.err.println("IOException:  " + e);
    }



}







 public static void main(String args[])
 {
  
  int port_number=6990;
    String host="172.16.129.205";

 try{



    clientsocket=new Socket(host,port_number);
    outToserver = new PrintStream(clientsocket.getOutputStream());
    in_from_server = new BufferedReader(new InputStreamReader(clientsocket.getInputStream()));

   //s=new Socket("localhost",6000);
   //below line reads input from InputStreamReader
 
   //below line writes output to OutPutStream
   
  }

  catch(Exception e)  
  {
        

  }

           
  client = new User1_Q();

  Scanner s1=new Scanner(System.in);
           client.create();

       
      
 



       }
     }