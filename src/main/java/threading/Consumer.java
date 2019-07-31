package threading;


import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import DBclass.DB;

import paitent.patient;

public class Consumer extends Thread {
	DB db;
	java.util.List<patient> list;
	private static final Logger log=  LogManager.getLogger(Consumer.class.getName());

	public Consumer(java.util.List<patient> list) {
		super();
		this.db = new DB();

		this.list = list;
	}

	public void run() {

		while (true) {

			try {
				
				if(list.size()!=0)
					db.insertPationet(list);
				
				
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				log.error(e.getMessage());
			}

		}

	}

}
