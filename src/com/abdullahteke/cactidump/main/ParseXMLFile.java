package com.abdullahteke.cactidump.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;


public class ParseXMLFile {

	public static void main(String[] args) throws XMLStreamException, FileNotFoundException {
		List<Event> eventList= null;
		
		Event curEvent=null;
		String tagContent=null;
		XMLInputFactory factory = XMLInputFactory.newInstance();
		XMLStreamReader xmlReader= factory.createXMLStreamReader(new FileInputStream(new File("xmlfiles/umumi_traffic_out_250.xml")));
		
		int index=0;
		int in2=0;
		
		while (xmlReader.hasNext()){
			index= xmlReader.next();
			
			switch (index){
			case XMLStreamConstants.START_ELEMENT:
				if ("row".equals(xmlReader.getLocalName())){
					curEvent = new Event();
				}
				
				if ("data".equals(xmlReader.getLocalName())){
					eventList= new ArrayList<Event>();
				}
				
				break;
					
			case XMLStreamConstants.CHARACTERS:
				tagContent = xmlReader.getText().trim();
				break;
				
			case XMLStreamConstants.END_ELEMENT:
				
				if (xmlReader.getLocalName().equalsIgnoreCase("row")){
					eventList.add(curEvent);
					
				}else if (xmlReader.getLocalName().equalsIgnoreCase("t")){
					curEvent.time= Integer.parseInt(tagContent);
					
				}else  if (xmlReader.getLocalName().equalsIgnoreCase("v")){
					if (in2==0){
						curEvent.inUtil=Double.parseDouble(tagContent);
						in2++;
					}else{
						curEvent.outUtil=Double.parseDouble(tagContent);
						in2=0;
					}
					
					
				}
				
				break;
			case XMLStreamConstants.START_DOCUMENT:
				eventList= new ArrayList<Event>();
				break;				
			}
			
		}
		
		for (Event ev:eventList){
			System.out.println(ev);
		}

	}

}

class  Event{
	
	long time;
	double inUtil;
	double outUtil;
	
	@Override
	public String toString() {	
		return time+"\t"+inUtil+"\t"+outUtil;
	}
}
