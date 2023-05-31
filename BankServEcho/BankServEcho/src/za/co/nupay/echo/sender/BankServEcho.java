package za.co.nupay.echo.sender;

import java.util.Timer;


public class BankServEcho {

static int num = 0;
	
	public static void main(String[] args) {
		
		 System.out.println("In the timer main method to send 0800 to BankServ every 5 mins");
		 
		 Timer timer = new Timer();		
	
		 timer.schedule(new EchoProducer(), 0, 300000);
		 	
	}
	
	
}	



