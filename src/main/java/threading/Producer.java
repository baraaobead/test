package threading;

import java.io.IOException;

import java.util.List;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ftpClient.FtpClient;
import paitent.patient;

public class Producer extends Thread{
	Thread t;
	private  FtpClient ftpClient;
	java.util.List<patient>List;
	Logger log= LogManager.getLogger(Producer.class.getName());
	public Producer(List<patient>list) {
		super();
		
		this.List=list;
	} 

	public void run() {
		while(true) {
			try {
				  try {ftpClient= new FtpClient();
					  List< patient> patient=ftpClient.startWork();
					  if(!patient.isEmpty())
						List.addAll(patient);
					} catch (IOException e) {
						
					}
				Thread.sleep(50*1000);				
					} 
			catch (InterruptedException e) {
				
					log.error(e.getMessage());
				
				}	 
			
		}
	
	}
	
}
	
	
	

