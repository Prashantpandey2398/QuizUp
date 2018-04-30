
package SocketProg;

import java.net.*;
import java.io.*;
import java.util.*;
import javax.accessibility.AccessibleAttributeSequence;
import sun.misc.JavaAWTAccess;

public class Server implements Runnable {
  public HashMap<InetAddress,String> users=new HashMap<InetAddress,String>();
  public static String uq[] = new String[4];
  public static String uc1[] = new String[4];
  public static String uc2[] = new String[4];
  public static String uc3[] = new String[4];
  public static String ca[] = new String[4];
  public int cq=0;
  public static HashMap<InetAddress,Boolean> users_status=new HashMap<InetAddress,Boolean>();
  private Socket connection;
  private static String TimeStamp;
  private int ID;
  private InetAddress IP ;
  public static void main(String[] args) {
      uq[0]="What is name of current Indian cricket Captain?";
      uq[1]="What is name of current Indian Prime Minister?";
      uq[2]="Who is the current governor of RBI?";
      uq[3]="Which of the following is a quorum based algorithm?";
      uc1[0]="MS Dhoni";
      uc2[0]="Virat Kohli";
      uc3[0]="Rohit Sharma";
      uc1[1]="Jawaharlal Nehru";
      uc2[1]="Narendra Modi";
      uc3[1]="Manmohan Singh";
      uc1[2]="Ramnath Kovind";
      uc2[2]="Raghuram Rajan";
      uc3[2]="Urjit Patel";
      uc1[3]="Ricart Agarwala Algorithm";
      uc2[3]="Lamport Algorithm";
      uc3[3]="Maekawa Algorithm";
      ca[0]="2";
      ca[1]="2";
      ca[2]="3";
      ca[3]="3";
      TimeStamp = new java.util.Date().toString();
      System.out.println(TimeStamp);
  int port = 1500;
  int count = 0;
    try{
      ServerSocket socket1 = new ServerSocket(port);
      System.out.println("MultipleSocketServer Initialized");
      while (true) {
        Socket connection = socket1.accept();
        if(users_status.get(connection.getInetAddress())==null){
        System.out.println("No IP");
        users_status.put(connection.getInetAddress(), true);
    }
        Runnable runnable = new Server(connection, ++count, connection.getInetAddress());
        Thread thread = new Thread(runnable); 
        thread.start();
      }
    }
    catch (Exception e) {}
  }
Server(Socket s, int i, InetAddress ip) {
  this.connection = s;
  this.ID = i;
}
public void run() {
      TimeStamp = new java.util.Date().toString();
      String st = "11:35:00";
      System.out.println(TimeStamp);
      int sh = Integer.parseInt(st.split(":")[0]);
      int sm = Integer.parseInt(st.split(":")[1]);
      String t = TimeStamp.split(" ")[3];
      int hour = Integer.parseInt(t.split(":")[0]);
      int minute = Integer.parseInt(t.split(":")[1]);
      int second = Integer.parseInt(t.split(":")[2]);
      int total_second = ((60*hour+minute)*60)+second;
      int sts = (60*sh+sm)*60;
      cq=(total_second-sts)/15;
      float cqf = (float)(total_second-sts)/15;
      if (cq<0) {
        cq=0;
    }
      else{
          if(cqf-cq>0){
              cq+=1;
          }
    }
      System.out.println(cq);
    try {
      //need to wait 10 seconds to pretend that we're processing something
     if (cq==0) {
     System.out.println(cq);
     BufferedInputStream is = new BufferedInputStream(connection.getInputStream());
     InputStreamReader isr = new InputStreamReader(is);
     int character;
     StringBuffer process = new StringBuffer();
     while((character = isr.read()) != 13) {
        process.append((char)character);
     }
      System.out.println(process);
      try {
          int sleep_time = total_second-sts;
          if(sleep_time<0){
              sleep_time *= -1000;
          }
          else{
              sleep_time=15-((total_second-sts)%15);
              sleep_time*=1000;
          }
        Thread.sleep(sleep_time);
      }
      catch (Exception e){}
      String returnCode = "\n";
      returnCode+=uq[cq];
      returnCode+="\n";
      returnCode+=uc1[cq];
      returnCode+="\n";
      returnCode+=uc2[cq];
      returnCode+="\n";
      returnCode+=uc3[cq];
      returnCode+="\n";
      returnCode+=Integer.toString(cq+1);
      DataOutputStream out = new DataOutputStream(connection.getOutputStream());
      out.writeUTF(returnCode);
    }
     else if(cq>=4){
     BufferedInputStream is = new BufferedInputStream(connection.getInputStream());
     InputStreamReader isr = new InputStreamReader(is);
     int character;
     StringBuffer process = new StringBuffer();
     while((character = isr.read()) != 13) {
        process.append((char)character);
     }
      System.out.println(process);
      String answer = Character.toString(process.toString().charAt(0));
         String reply="";
         if(users_status.get(connection.getInetAddress())){
             if(answer.equals(ca[cq-1])){
                 reply+="You won";
             }
             else{
                 users_status.put(connection.getInetAddress(), false);
                 reply+="You lost";
             }
             System.out.println(users_status.get(connection.getInetAddress())+" "+reply);
         }
         else{
             reply+="You lost";
         }
      try {
        int sleep_time = total_second-sts;
        sleep_time=15-((total_second-sts)%15);
        sleep_time*=1000;
        Thread.sleep(sleep_time);
      }
      catch (Exception e){}
      String returnCode = reply;
      DataOutputStream out = new DataOutputStream(connection.getOutputStream());
      out.writeUTF(returnCode);
     }
     else{
     BufferedInputStream is = new BufferedInputStream(connection.getInputStream());
     InputStreamReader isr = new InputStreamReader(is);
     int character;
     StringBuffer process = new StringBuffer();
     while((character = isr.read()) != 13) {
        process.append((char)character);
     }
      System.out.println(process);
      String answer = Character.toString(process.toString().charAt(0));
         String reply="";
         if(users_status.get(connection.getInetAddress())){
             if(answer.equals(ca[cq-1])){
                 reply+="Your answer is correct";
             }
             else{
                 users_status.put(connection.getInetAddress(), false);
                 reply+="Your answer is incorrect";
             }
             System.out.println(users_status.get(connection.getInetAddress())+" "+reply);
         }
         else{
             reply+="You answer is incorrect";
         }
      try {
        int sleep_time = total_second-sts;
        sleep_time=15-((total_second-sts)%15);
        sleep_time*=1000;
        Thread.sleep(sleep_time);

      }
      catch (Exception e){}
      String returnCode = reply+"\n";
      returnCode+=uq[cq];
      returnCode+="\n";
      returnCode+=uc1[cq];
      returnCode+="\n";
      returnCode+=uc2[cq];
      returnCode+="\n";
      returnCode+=uc3[cq];
      returnCode+="\n";
      returnCode+=Integer.toString(cq+1);
      DataOutputStream out = new DataOutputStream(connection.getOutputStream());
      out.writeUTF(returnCode);
     }
}
      
    catch (Exception e) {
      System.out.println(e);
    }
    finally {
      try {
        connection.close();
     }
      catch (IOException e){}
    }
}
}