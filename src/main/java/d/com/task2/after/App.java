package d.com.task2.after;

import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import paitent.patient;
import threading.Consumer;
import threading.Producer;

/**
 * Hello world!
 *
 */
public class App 
{	
	
	
	private static List<patient>s= new LinkedList<patient>();
private static final Logger log= LogManager.getLogger(App.class.getName());
    

public static void main( String[] args )
    {
    	Producer producer= new Producer(s);
		producer.start();
		Consumer consumer = new Consumer(s);
		consumer.start();
		
		
    }
}
