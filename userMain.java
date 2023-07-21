import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.* ;

public class userMain {

   public static int p1num ;
   public static PCB [] Q1Q2List ;
   public static PCB [] Q1;
   public static PCB [] Q2;
   public static int N = 1 ;
   public static int q1c = 0 ;
   public static int q2c = 0 ;
   public static int q1q2c = 0 ;
   public static int sizeQ1=0;
   public static int sizeQ2=0;
   public static String [] gchart;
   public static int totBurst=0;
   
   
   public static void main (String [] args ) throws IOException{
      Scanner in =new Scanner(System.in);
      int choice;
      int priority;
      String pid;
      int at;
      int cbu;
   
   
      do
      {
         System.out.println("please, choose one of the follwing choices(1-4)");
         System.out.println("1.Enter process' information");
         System.out.println("2.Report detailed information about each process");
         System.out.println("3.Report the average turnaround time, waiting time, and response time");
         System.out.println("4.Exit the program");
         choice = in.nextInt();
      
         switch(choice)
         {
            case 1:
               System.out.println("Enter the number of processes: ");
               p1num = in.nextInt(); 
               Q1Q2List = new PCB [p1num];
               for(int i=0;i<p1num;i++)
               {
                  System.out.print("please enter the priority of process (1-2)");
                  priority = in.nextInt();
                  pid = "P"+N ;
                  System.out.print("please enter Arrival Time: ");
                  at = in.nextInt();
                  System.out.print("CPU Burst: ");
                  cbu = in.nextInt();
                  totBurst += cbu ;
               
                  Q1Q2List[q1q2c++] = new PCB(pid,priority,at,cbu);
                  N++;
                  if(priority == 1)
                     sizeQ1++;
                  else if(priority == 2)
                     sizeQ2++;
               }
               Q1 = new PCB [sizeQ1];
               Q2 = new PCB [sizeQ2];
            
               for(int i=0;i<p1num;i++)
               {
                  if(Q1Q2List[i].ProcessPriority == 1)
                  {
                     Q1[q1c++]= Q1Q2List[i];
                     SJF();
                  }else if(Q1Q2List[i].ProcessPriority == 2)
                  {
                     Q2[q2c++]= Q1Q2List[i];
                  }
               
               }
            
            
               break;
         
            case 2:
               multilevel();
               Report1();
               break;
            case 3:
               double sumTurnAroundTime=0;
               double sumWaitingTime=0;
               double sumResponseTime=0;
            
               double avgTurnAroundTime=0;
               double avgWaitingTime=0;
               double avgResponseTime=0;
            
               for(int i=0;i<Q1Q2List.length;i++)
               {
                  sumTurnAroundTime += Q1Q2List[i].TAT;
                  sumWaitingTime+= Q1Q2List[i].WTime;
                  sumResponseTime+= Q1Q2List[i].RT;
               }
            
               avgTurnAroundTime=sumTurnAroundTime/p1num;
               avgWaitingTime=sumWaitingTime/p1num;
               avgResponseTime=sumResponseTime/p1num;
            
               System.out.println("Average turn around time: "+avgTurnAroundTime);
               System.out.println("Average waiting time: "+avgWaitingTime);
               System.out.println(" Average responce time : "+avgResponseTime); 
               System.out.println();
            
               try
               {
                  BufferedWriter writer = new BufferedWriter(new FileWriter("Report2.txt"));
                  writer.write("Average turn around time ="+avgTurnAroundTime+"\n");
                  writer.write("Average waiting time ="+avgWaitingTime+"\n");
                  writer.write("Average responce time ="+avgResponseTime+"\n");
               
                  writer.flush();
                  writer.close();
               
                  writer.close();
               }catch(IOException ex)
               {
                  System.out.println("error");
               }               
               break;
            case 4:
               System.exit(0);
               break;
            default:
               System.out.println("sorry, you should choose a number between(1-4)only!");
         }
      
      
      
      }while(choice!=4);
   
   
   }//main

   public static void SJF(){
   
      int pos ; 
      int n = q1c ;
      PCB temp ; 
   
      for(int i=0;i<n;i++)
      {
         pos=i;
         for(int j=i+1;j<n;j++)
         {
            if(Q1[j].getCPUBurst() < Q1[pos].getCPUBurst())
               pos=j;
         }
      
         temp=Q1[i];
         Q1[i]=Q1[pos];
         Q1[pos]=temp;
      }
   
   }//SJF

   public static void multilevel(){
      int gCounter = 0 ;
      int i = 0 ;
      int q22 = 0 ;
      int systime = 0 ;
      boolean flag = true ;
      int q1pro = Q1.length;
      int verq1pro = 0 ;
      while(i<totBurst){
         if(Q1.length>0) {
            for(int j =0 ;j<Q1.length ; j++){
               if(Q1[j].AT <= systime && Q1[j].remaning>0) {
                  for(int k =1 ;k<=Q1[j].CPUBurst ; k++) {
                     System.out.print(Q1[j].PID +"|"); 
                     i++;
                  }
               
               
                  Q1[j].setstartTime(systime);
                  Q1[j].setterTime(systime + Q1[j].getCPUBurst());
                  Q1[j].setTAT();
                  Q1[j].setWaitingTime();
                  Q1[j].setResponceTime();
                  systime += Q1[j].CPUBurst ;
                  Q1[j].remaning -= Q1[j].CPUBurst ;
                  q1pro--;
               }
              
               if((j+1) == Q1.length )
                  if(q1pro>0 && verq1pro == q1pro){
                     flag = false ;
                  }
                  else
                     if(q1pro>0){
                        verq1pro = q1pro ;
                        j = -1 ;
                     }
                     else
                        flag = false ;
            }
         }
             
         if(Q2.length>0)
            if(flag == false){
               for(int j =0 ;j<Q1.length ; j++){
                  if(Q1[j].AT <= systime && Q1[j].remaning>0) {
                     for(int k =1 ;k<=Q1[j].CPUBurst ; k++) {
                        System.out.print(Q1[j].PID +"|"); 
                        i++;
                     }
                     systime += Q1[j].CPUBurst ;
                     Q1[j].remaning -= Q1[j].CPUBurst ;
                  
                     q1pro--;
                  }
               }
            
               for(int j =0 ;j<Q2.length ; j++) {
                  if(Q2[j].AT <= systime && Q2[j].remaning>0){
                     System.out.print(Q2[j].PID +"|"); 
                  
                     if(Q2[j].remaning == Q2[j].getCPUBurst())
                        Q2[j].setstartTime(systime);
                  
                     Q2[j].remaning--;
                     systime++;
                  
                     if(Q2[j].remaning == 0 ){
                        Q2[j].setterTime(systime);
                        Q2[j].setTAT();
                        Q2[j].setWaitingTime();
                        Q2[j].setResponceTime();
                     }
                  
                  
                     i++;
                  
                  
                     flag = true;
                     break ;
                  }
               }
            }
              
              
      
             
      }
   
      System.out.println(); 
   
   
   }//multi
   
   
   
   static void Report1() throws IOException {
         
      PrintWriter PF = new PrintWriter("Report1.txt");
         
      try {
         PF.printf("%s", "Q1 : \n");
         PF.printf("%-15s", "process ID");
         PF.printf("%-20s", "Process Priority");
         PF.printf("%-15s", "CPU burst");
         PF.printf("%-15s", "Arrival time");
         PF.printf("%-20s", "Start time");
         PF.printf("%-20s", "Termination time");
         PF.printf("%-30s", "Turn around time");
         PF.printf("%-20s", "Waiting time");
         PF.printf("%-20s\n", "Response time");
         
         for (int i=0; i<Q1.length; i++) {
            PF.printf("%-15s", Q1[i].PID);
            PF.printf("%-20d", Q1[i].ProcessPriority);
            PF.printf("%-15d", Q1[i].CPUBurst);
            PF.printf("%-15d", Q1[i].AT);
            PF.printf("%-20d", Q1[i].startTime);
            PF.printf("%-20d", Q1[i].terTime );
            PF.printf("%-30d", Q1[i].TAT);
            PF.printf("%-20d", Q1[i].WTime);
            PF.printf("%-20d\n", Q1[i].RT);
         }
               
         PF.printf("%s", "Q2 : \n");
         PF.printf("%-15s", "process ID");
         PF.printf("%-20s", "Process Priority");
         PF.printf("%-15s", "CPU burst");
         PF.printf("%-15s", "Arrival time");
         PF.printf("%-20s", "Start time");
         PF.printf("%-20s", "Termination time");
         PF.printf("%-30s", "Turn around time");
         PF.printf("%-20s", "Waiting time");
         PF.printf("%-20s\n", "Response time");
         
         for (int i=0; i<Q2.length; i++) {
            PF.printf("%-15s", Q2[i].PID);
            PF.printf("%-20d", Q2[i].ProcessPriority);
            PF.printf("%-15d", Q2[i].CPUBurst);
            PF.printf("%-15d", Q2[i].AT);
            PF.printf("%-20d", Q2[i].startTime);
            PF.printf("%-20d", Q2[i].terTime );
            PF.printf("%-30d", Q2[i].TAT);
            PF.printf("%-20d", Q2[i].WTime);
            PF.printf("%-20d\n", Q2[i].RT);
         
               
         }
            
      }
      catch(Exception c) {}
      PF.close();
   } 

}//class