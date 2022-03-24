package com.abdullahteke.cactidump.controller;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLFileManager {
	
	private static XMLFileManager instance;
	
	private static Logger logger = LogManager.getLogger(XMLFileManager.class);
	
	public static XMLFileManager getInstance() {
		
		if (instance==null){
			instance=new XMLFileManager();
		}
		return instance;
	}
	
	public static void createXML2CSV(String xmlFilePath,String csvFilePath){
		
		File xmlFile= new File(xmlFilePath);
		SimpleFileWriter csvFile= SimpleFileWriter.openFileForWriting(csvFilePath);
		
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder=null;
		Document doc=null;
		
		try {
			
			dBuilder = dbFactory.newDocumentBuilder();
			doc=dBuilder.parse(xmlFile);
			
		} catch (ParserConfigurationException e) {
			logger.error("Hata:"+e.getMessage());
		
		} catch (SAXException e) {
			logger.error("Hata:"+e.getMessage());

		} catch (IOException e) {
			logger.error("Hata:"+e.getMessage());

		}
		
		doc.getDocumentElement().normalize();
			
		NodeList nList= doc.getElementsByTagName("row");
		
		Element element=null;
		
		String time=null;
		NodeList values=null;
		String csvLine="";
		
		for (int i=0;i<nList.getLength();i++){
		
			element= (Element) nList.item(i);
			
			time=getInstance().getDateAndTime(element.getElementsByTagName("t").item(0).getTextContent());
			csvLine= csvLine+time;
			
			values= element.getElementsByTagName("v");
			
			for (int k=0;k<values.getLength();k++){
				csvLine=csvLine+","+getInstance().roundDouble(values.item(k).getTextContent());
			}
		   csvFile.println(csvLine);
		   csvLine="";
		}
		
		csvFile.close();

	}
	
	public String getDateAndTime(String stTime) {
		String retVal="0";
		
		long time=Integer.parseInt(stTime);
		
		if (time>0){
			Date myDate=new Date(time*1000);
			DateFormat myDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
			retVal=myDateFormat.format(myDate);
		}
		
		return retVal;
	}
	
	public String roundDouble(String st){
		String retVal="0.0";
		
		
		NumberFormat nf=NumberFormat.getInstance();
		nf=java.text.NumberFormat.getInstance(Locale.US);
		nf.setMaximumFractionDigits(2);
		nf.setMinimumFractionDigits(2);
		nf.setGroupingUsed(true);
		retVal=nf.format(Double.parseDouble(st));		
		return retVal;
	}
	
}
