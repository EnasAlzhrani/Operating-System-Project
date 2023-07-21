public class PCB {
	
   String PID ;
   int ProcessPriority ;
   int AT ;
   int CPUBurst ;
   int remaning ;
   int TAT = 0 ;
   int startTime = 0 ;;
   int terTime = 0 ;
   int WTime = 0 ;
   long RT = 0 ;
	
   public PCB(String pID, int processPriority, int aT, int cPUBurst) {
      super();
      PID = pID;
      ProcessPriority = processPriority;
      AT = aT;
      CPUBurst = cPUBurst;
      remaning = cPUBurst;
   }
	
   public void setstartTime(int st) {
      this.startTime = st;
   }
    
   public void setterTime(int tt){
      terTime = tt ;
   }

   public String getPID() {
      return PID;
   }
   public void setPID(String pID) {
      PID = pID;
   }
   public int getProcessPriority() {
      return ProcessPriority;
   }
   public void setProcessPriority(int processPriority) {
      ProcessPriority = processPriority;
   }
   public long getAT() {
      return AT;
   }
   public void setAT(int aT) {
      AT = aT;
   }
   public int getCPUBurst() {
      return CPUBurst;
   }
   public void setCPUBurst(int cPUBurst) {
      CPUBurst = cPUBurst;
   }
   
   public void setResponceTime()
   {
      
      RT=startTime-AT;
   }
	
   public void setWaitingTime()
   {
      WTime=TAT-CPUBurst;
   }

   public void setTAT()
   {
      TAT= terTime - AT;
   }
   
   public int getTAT()
   {
      return TAT;
   }
}
