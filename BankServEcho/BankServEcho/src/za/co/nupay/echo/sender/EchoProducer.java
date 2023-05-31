package za.co.nupay.echo.sender;

import java.util.Calendar;
import java.util.Hashtable;
import java.util.TimerTask;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.InitialDirContext;
import com.ibm.mq.jms.MQQueue;
import com.ibm.msg.client.jms.JmsConnectionFactory;
import com.ibm.msg.client.jms.JmsDestination;
import za.co.nupay.constructor.MsgConstructor;
import za.co.nupay.utils.ReadConfigs;



public class EchoProducer extends TimerTask {

		private String corrID = null;
		private static String initialContextUrl = null;
		private static String connectionFactoryFromJndi = null;
		private static String destinationFromJndi = null;
		

	public static String getTagValue(String xml, String tagName){
	    return xml.split("<"+tagName+">")[1].split("</"+tagName+">")[0];
	}
	

	private final void process_0800_Message(){
		
		String[] inPutParms = {"-i",ReadConfigs.INITIALCONTEXTURL,"-c",ReadConfigs.CONNECTIONFACTORY, "-d", ReadConfigs.DESTINATION};

		parseArgs(inPutParms);

		Connection connection = null;
		Session session = null;
		Destination destination = null;
		MessageProducer producer = null;

		 try {
	   
		   String contextFactory = "com.sun.jndi.fscontext.RefFSContextFactory";
		   Hashtable<String, String> environment = new Hashtable<String, String>();
		   environment.put(Context.INITIAL_CONTEXT_FACTORY, contextFactory);
		   environment.put(Context.SECURITY_PRINCIPAL,ReadConfigs.USER);
		   environment.put(Context.SECURITY_CREDENTIALS, ReadConfigs.PASSWORD);
		   environment.put(Context.PROVIDER_URL, initialContextUrl);
		   
		   Context context = new InitialDirContext(environment);
		   System.out.println("Initial context found!");
		   JmsConnectionFactory cf = (JmsConnectionFactory) context.lookup(connectionFactoryFromJndi);
	  
		   destination = (JmsDestination) context.lookup(destinationFromJndi);
		   connection = cf.createConnection();
		   session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		   producer = session.createProducer(destination);
		   TextMessage message = session.createTextMessage();
		   
		   MsgConstructor echo800 = new MsgConstructor();

		   String echo810 = echo800.constructEchoRequest0800();
   
		   message.setText(echo810.trim());
   
		   Calendar cal = Calendar.getInstance(); cal.set(2011,9,12,5,42,0); 
		   
		   long ms = cal.getTimeInMillis();
		   
		   message.setJMSType("MQMT_REQUEST");
		   message.setStringProperty("JMS_IBM_MQMD_UserIdentifier", "Finbond");
		   
		   message.setJMSMessageID(this.corrID);
		   
		   MQQueue replyToQ = new MQQueue(ReadConfigs.WMQ_QUEUE_MANAGER, ReadConfigs.REPLYTO_Q_NAME);
		   
		   Destination replyTo = (Destination) replyToQ;
		   message.setJMSReplyTo(replyTo);

		   connection.start();
			
		   String replyString = ((TextMessage) message).getText();

		   producer.send(message);
		
		 }
		 catch (JMSException jmsex) {
			
		   recordFailure(jmsex);
		 }
		 catch (NamingException ne) {
		   System.out.println("The initial context could not be instantiated, or the lookup failed.");
		  
		   recordFailure(ne);
		 }
		 finally {
		   if (producer != null) {
		     try {
		       producer.close();
		     }
		     catch (JMSException jmsex) {
		       System.out.println("Producer could not be closed.");
		       recordFailure(jmsex);
		     }
		   }
			if (session != null) {
				try {
					session.close();
				} catch (JMSException jmsex) {

					recordFailure(jmsex);
				}
			}

			if (connection != null) {
				try {
					connection.close();
				} catch (JMSException jmsex) {

					recordFailure(jmsex);
				}
			}

	
 }
}
	
	private static void recordSuccess() {
	 System.out.println("SUCCESS");

	 return;
	}


	private static void recordFailure(Exception ex) {
	 if (ex != null) {
	   if (ex instanceof JMSException) {
	     processJMSException((JMSException) ex);
	   } else {
	     System.out.println(ex);
	   
	   }
	 }
	 System.out.println("FAILURE");

	 return;
	}
	
	
	
	private static void parseArgs(String[] args) {
	 try {
	   int length = args.length;
	   if (length == 0) {
	     throw new IllegalArgumentException("No arguments! Mandatory arguments must be specified.");
	   }
	   if ((length % 2) != 0) {
	     throw new IllegalArgumentException("Incorrect number of arguments!");
	   }

	   int i = 0;

	   while (i < length) {
	     if ((args[i]).charAt(0) != '-') {
	       throw new IllegalArgumentException("Expected a '-' character next: " + args[i]);
	     }

	     char opt = (args[i]).toLowerCase().charAt(1);

	     switch (opt) {
	       case 'i' :
	         initialContextUrl = args[++i];
	         break;
	       case 'c' :
	         connectionFactoryFromJndi = args[++i];
	         break;
	       case 'd' :
	         destinationFromJndi = args[++i];
	         break;
	       default : {
	         throw new IllegalArgumentException("Unknown argument: " + opt);
	       }
	     }

	     ++i;
	   }

	   if (initialContextUrl == null) {
	     throw new IllegalArgumentException("An initial context must be specified.");
	   }

	   if (connectionFactoryFromJndi == null) {
	     throw new IllegalArgumentException(
	         "A connection factory to lookup in the initial context must be specified.");
	   }

	   if (destinationFromJndi == null) {
	     throw new IllegalArgumentException(
	         "A destination to lookup in the initial context must be specified.");
	   }
	 }
	 catch (Exception e) {
	   System.out.println(e.getMessage());
	   printUsage();
	   System.exit(-1);
	 }
	 return;
	}


	private static void printUsage() {
	 System.out.println("\nUsage:");
	 System.out.println("JmsJndiProducer -i initialContext -c connectionFactory -d destination");
	 return;
	}

	private static void processJMSException(JMSException jmsex) {
	 System.out.println(jmsex);
	 Throwable innerException = jmsex.getLinkedException();
	 if (innerException != null) {
	   System.out.println("Inner exception(s):");
	 }
	 while (innerException != null) {
	   System.out.println(innerException);
	   innerException = innerException.getCause();
	 }
	 return;
	}




	@Override
	public void run() {
		process_0800_Message();
		
	}


	}


