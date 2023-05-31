package za.co.nupay.constructor;


import java.io.File;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.transform.Result;
import za.co.nupay.xml.request.MemberId;
import za.co.nupay.xml.request.NetworkRequest;
import za.co.nupay.xml.request.ObjectFactory;
import za.co.nupay.xml.response.*;



public class MsgConstructor {

public String constructEchoRequest0800(){
	
	za.co.nupay.xml.request.ObjectFactory factory = new ObjectFactory();
	
	MemberId frmMbrId = factory.createMemberId();
	frmMbrId.setMbrId(210055);
	
	MemberId toMbrId = factory.createMemberId();
	toMbrId.setMbrId(210100);
	
	NetworkRequest NtwrkRqst = factory.createNetworkRequest(); 
	
	NtwrkRqst.setFrom(frmMbrId);
	NtwrkRqst.setTo(toMbrId);
	
	
try {
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		String date = sdf.format(new Date());
		XMLGregorianCalendar xmlCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(date);
		NtwrkRqst.setTrDtTm(xmlCalendar);
	
		
		
	} catch (DatatypeConfigurationException e1) {
		
		e1.printStackTrace();
	}
	
	Random rnd = new Random();
	int msgTrcNr = 100000 + rnd.nextInt(900000);
	
	NtwrkRqst.setMsgTrcNbr(msgTrcNr);
	

	NtwrkRqst.setNtwrkMnCd(301);
	

	za.co.nupay.xml.request.Document document = factory.createDocument();
	document.setNtwrkRqst(NtwrkRqst);
	
	JAXBContext ctx = null;
	StringWriter sw = new StringWriter();
	
	
	try {
		ctx = JAXBContext.newInstance("za.co.nupay.xml.request");
		
		JAXBElement<za.co.nupay.xml.request.Document> element = factory.createDocument(document);
		Marshaller marshaller = ctx.createMarshaller();
		marshaller.marshal(element,System.out);
		marshaller.marshal(element,new File("c:\\temp\\request.xml"));
		marshaller.marshal(element, sw);
	    
	} catch (JAXBException e) {
		
		e.printStackTrace();
	}
	return sw.toString();
	
	
	

	
	//return null;
	
	
	
}

public String constructEchoResponse0810(int msgTrcNr){
	
	System.out.println("In constructor 0810");
	
	
	za.co.nupay.xml.response.ObjectFactory factory = new za.co.nupay.xml.response.ObjectFactory();
	
	za.co.nupay.xml.response.MemberId frmMbrId = factory.createMemberId();
	frmMbrId.setMbrId(210055);
	
	za.co.nupay.xml.response.MemberId toMbrId = factory.createMemberId();
	toMbrId.setMbrId(210100);
	
	za.co.nupay.xml.response.NetworkResponse NtwrkRpns = factory.createNetworkResponse();
	
	NtwrkRpns.setFrom(frmMbrId);
	NtwrkRpns.setTo(toMbrId);
	
	//XMLGregorianCalendar xmlCalendar;
	
	NtwrkRpns.setRpnsCd("00");
	try {
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		String date = sdf.format(new Date());
		XMLGregorianCalendar xmlCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(date);
		
		
		//xmlCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar());
		//xmlCalendar.setTimezone(Integer.MIN_VALUE);
		NtwrkRpns.setTrDtTm(xmlCalendar);
		
		
		
		
		
		
	} catch (DatatypeConfigurationException e1) {
		
		e1.printStackTrace();
	}
	
	
	
	NtwrkRpns.setMsgTrcNbr(msgTrcNr);
	NtwrkRpns.setNtwrkMnCd(301);
	

	za.co.nupay.xml.response.Document document = factory.createDocument();
	document.setNtwrkRpns(NtwrkRpns);
	
	JAXBContext ctx = null;
	StringWriter sw = new StringWriter();
	
	try {
		ctx = JAXBContext.newInstance("za.co.nupay.xml.response");
		
		JAXBElement<za.co.nupay.xml.response.Document> element = factory.createDocument(document);
		Marshaller marshaller = ctx.createMarshaller();
		marshaller.marshal(element,System.out);
		//marshaller.marshal(element,new File("c:\\temp\\response.xml"));
		marshaller.marshal(element, sw);
		
	} catch (JAXBException e) {
		
		e.printStackTrace();
	}
	
	return sw.toString();


}

	
	
	
	
}








		
	
		
			



