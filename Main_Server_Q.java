import java.io.*;
import java.io.PrintStream;
import java.io.IOException;
import java.net.*;
import java.lang.Thread;
import java.util.*;



public class Main_Server_Q
{
  static ServerSocket serversocket=null;
  static Socket client=null;
  static int max_client=10;
  static final clientThread[] threads=new clientThread[max_client];
  static FileOutputStream fos = null;
  static File file;

  public static void main(String[] args) throws Exception
  {
    int port_number=6990;

try {
      serversocket = new ServerSocket(port_number);
    } 
    catch (IOException e) {
      System.out.println(e);
    }
        while (true) {
    try
    {
      client = serversocket.accept();

      InetAddress in_address=client.getInetAddress();
      String ip_address=in_address.getHostAddress();
      System.out.println("The host address is"+" "+ip_address);

BufferedReader inFromClient1 = new BufferedReader(new InputStreamReader(client.getInputStream()));
     PrintStream outToclient1=new  PrintStream(client.getOutputStream());


String state;
 
  int log_in=0;
  int create_account=0;

  while(1==1)
  {

    state=inFromClient1.readLine();
if(state.equals("Sign_Up"))
{


  System.out.println("********Sign Up**********");
try
{
file = new File("F:\\sem6\\Network_Lab_1401CS53\\Socket_Programming\\f1.txt");
 fos = new FileOutputStream(file,true);

if (!file.exists()) {
   System.out.println("sss");
       file.createNewFile();
    }


    String hh=inFromClient1.readLine();
    hh=hh+" "+ip_address;

      System.out.println(hh);

      byte[] bytesArray = hh.getBytes();

      fos.write(bytesArray);
      String gg="\n";
      byte[] bytesArray1 = gg.getBytes();

      fos.write(bytesArray1);

      fos.flush();
      outToclient1.println("Account Created");
      create_account++;
    }

      catch(Exception e)
      { 

        System.out.println("ddd");

      }

}
else if(state.equals("Log_in"))
{

  System.out.println("********Log In**********");
  try{
    file = new File("F:\\sem6\\Network_Lab_1401CS53\\Socket_Programming\\f1.txt");
      FileReader fileReader = new FileReader(file);
      BufferedReader bufferedReader = new BufferedReader(fileReader);
      String line;
        String hh1=inFromClient1.readLine();
        String[] ff1=hh1.split("\\s");
     String result="No";

      while ((line = bufferedReader.readLine()) != null) {
        System.out.println(line);
        String[] gg1=line.split("\\s");

        System.out.println(gg1[0]+" "+gg1[1]);

       if((ff1[0].equals(gg1[0]))&&(ff1[1].equals(gg1[1])))
       {
             result="Yes";
            log_in++;
            System.out.println("yooo");
            break;
       }


        }
        outToclient1.println(result);

      fileReader.close();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
}

System.out.println("xxx"+create_account+"xxxx"+log_in);
     if((create_account==1)||(log_in==1))
     {
      break;
     }
}
      
      int i=0;
      for(i=0;i<max_client;i++)
      {
        if(threads[i]==null)
        {
          threads[i]=new clientThread(client,threads);
          threads[i].start();
          break;
        }
      }
      if(i==max_client)
      {
        PrintStream os = new PrintStream(client.getOutputStream());
                 os.println("Server too busy. Try later.");
                 os.close();
        client.close();
      }
    }
  
    catch(Exception e)
    {

    }
}
  }

}

class clientThread extends Thread
{
  static FileOutputStream fos1 = null;
  static File file1;
     private String clientname=null;
    private BufferedReader in_from_server=null;
     private PrintStream outToserver = null;
     private Socket clientsocket=null;
     private final clientThread[] threads;
     private final String[] friend=new String[10];
 
     private int max_client;

     public clientThread(Socket clientsocket, clientThread[] threads)
     {
         this.clientsocket=clientsocket;
         this.threads=threads;
         max_client=threads.length;
     }

     public void run()
     {
      clientThread[] threads=this.threads;

      try
      {
        in_from_server = new BufferedReader(new InputStreamReader(clientsocket.getInputStream()));
        outToserver=new PrintStream(clientsocket.getOutputStream());
        String client_name;

        while(true)
        {
          System.out.println("Enter your name");
          client_name=in_from_server.readLine().trim();
           if (client_name.indexOf('@') == -1) {
          break;
        }
        }



        outToserver.println("Welcome "+client_name+" to chat room.\nTo leave enter /quit in a new line.");

        synchronized (this) {
          for(int i=0;i<max_client;i++)
          {
            if (threads[i] != null && threads[i] == this) {
                           clientname = "@" + client_name;
                           break;
          }
        }

        for (int i = 0; i < max_client; i++) {

          if (threads[i] != null && threads[i] != this) {

            threads[i].outToserver.println("*** A new user " + client_name
                + " entered the chat room !!! ***");
          }
        }
      }





      while(true)
      {
        String line=in_from_server.readLine();
        System.out.println(line);
           
        
        if(line.startsWith(":"))
        {
          String cl_m;
          String k1;
          k1=line.substring(1);
          System.out.println(k1);

          try{

          file1 = new File("F:\\sem6\\Network_Lab_1401CS53\\Socket_Programming\\f1.txt");
      
     
    }
    catch(Exception e)
    {

    }
    FileReader fileReader1 = new FileReader(file1);
     BufferedReader bufferedReader = new BufferedReader(fileReader1);
      String line1;
     int q1=0;
       String final1="";
      while ((line1 = bufferedReader.readLine()) != null) {

        System.out.println(line1);
        String[] gg1=line1.split("\\s");

        if(gg1[1].equals("friend"))
        {

          System.out.println("555555555555555555555555555555555555555555555");
          System.out.println(client_name+" "+gg1[0]);
          System.out.println(k1+" "+gg1[2]);

              if(gg1[2].equals(k1)&&gg1[0].equals(client_name))
              {
                          q1=0;
                          break;
              }

        }
       
      
        if((gg1[0].equals(k1))&&(!((gg1[0].equals(client_name)))))
        {
                q1++;
                final1=client_name+" friend "+gg1[0];
                
        }
                        
            

          }
    



          if(q1==1)
          {

          try
          {
            file1 = new File("F:\\sem6\\Network_Lab_1401CS53\\Socket_Programming\\f1.txt");
           fos1 = new FileOutputStream(file1,true);

      byte[] bytesArray = final1.getBytes();

      fos1.write(bytesArray);
      String gg="\n";
      byte[] bytesArray1 = gg.getBytes();

      fos1.write(bytesArray1);

      fos1.flush();

          }
          catch(Exception e)
          {

          }
          outToserver.println("Friend added");
        }
        else
        {
          outToserver.println("Friend not added sorry");
        }


        }
        else if (line.startsWith("/quit")) {

          break;
        }

        else if(line.startsWith("@"))
        {
           System.out.println("*****************private*************");
          String[] words = line.split("\\s", 2);

          String fr_name=words[0].substring(1);

          try{

          file1 = new File("F:\\sem6\\Network_Lab_1401CS53\\Socket_Programming\\f1.txt");
      
     
    }
    catch(Exception e)
    {

    }

          FileReader fileReader1 = new FileReader(file1);
          BufferedReader bufferedReader = new BufferedReader(fileReader1);
          String line1;

           int chat_value=0;





 while ((line1 = bufferedReader.readLine()) != null) {

  String[] gg1=line1.split("\\s");

        if(gg1[1].equals("friend"))
        {
              System.out.println("555555555555555555555555555555555555555555555");
          System.out.println(client_name+" "+gg1[0]);
          System.out.println(fr_name+" "+gg1[2]);

              if(gg1[2].equals(fr_name)&&gg1[0].equals(client_name))
              {
                          chat_value++;
                          break;
              } 
        }

 }








          if(chat_value==1)
          {


          for(int j=0;j<words.length;j++)
          {
            System.out.println(words[j]);
          }


          if(words.length>1 && words[1]!=null)
          {
            words[1]=words[1].trim();
          }
          if (!words[1].isEmpty()) {


               synchronized (this) {

                for(int i=0;i<max_client;i++)
                {
                  if(threads[i]!=null&&threads[i]!=this&&threads[i].clientname!=null&&threads[i].clientname.equals(words[0]))
                  {
                              threads[i].outToserver.println("<" + client_name + "> " + words[1]);
                              this.outToserver.println(">" + client_name + "> " + words[1]);
                              break;
                  }
                }
               }
              }
            }
            else
            {
                  this.outToserver.println("You can not send message friend not added ");
            }


        }

        else {
          System.out.println("*****************public*************");
          /* The message is public, broadcast it to all other clients. */
          synchronized (this) {
            for (int i = 0; i < max_client; i++) {
              if (threads[i] != null && threads[i].clientname != null) {
                threads[i].outToserver.println("<" + client_name + "> " + line);
              }
            }
          }
        }



      }

      synchronized (this) {
        for (int i = 0; i < max_client; i++) {
          if (threads[i] != null && threads[i] != this
              && threads[i].clientname != null) {
            threads[i].outToserver.println("*** The user " + client_name
                + " is leaving the chat room !!! ***");
          }
        }
      }

      outToserver.println("Bye");

      synchronized (this) {
        for (int i = 0; i < max_client; i++) {
          if (threads[i] == this) {
            threads[i] = null;
          }
        }
      }

      in_from_server.close();
      outToserver.close();
      clientsocket.close();




      }
      catch(Exception e)
      {

      }
     }

 }