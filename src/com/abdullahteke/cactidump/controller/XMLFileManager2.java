package com.abdullahteke.cactidump.controller;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLFileManager2 {
	
	private static XMLFileManager2 instance;
	private static Vector<InterfaceRecord> records;
	private static Logger logger = LogManager.getLogger(XMLFileManager.class);
	
	public static XMLFileManager2 getInstance() {
		
		if (instance==null){
			instance=new XMLFileManager2();
		}
		return instance;
	}
	
	public static void createXML2CSV(String xmlFilePath){
		
		File xmlFile= new File(xmlFilePath);
		
		records= new Vector<InterfaceRecord>();
		
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
		
		NodeList values=null;
		
		
		for (int i=0;i<nList.getLength();i++){
		
			element= (Element) nList.item(i);
			
			InterfaceRecord tmpRecord= new InterfaceRecord();
			
			values= element.getElementsByTagName("value");

			
			for (int k=0;k<values.getLength();k++){
				switch (k) {
					case 0:
						tmpRecord.setTimeStamp(values.item(k).getTextContent());
						break;
					case 1:
						tmpRecord.setOnNode(values.item(k).getTextContent());
						break;
					case 2:
						tmpRecord.setInterfaceName(values.item(k).getTextContent());
						break;
					case 3:
						tmpRecord.setThroughputIn(getInstance().roundDouble(values.item(k).getTextContent()));
						break;
					case 4:
						tmpRecord.setThroughputOut(getInstance().roundDouble(values.item(k).getTextContent()));
						break;
					default:
						break;
				}
			}
			
			records.add(tmpRecord);
		}
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
	
	public void createCSVFile(){
		
		
		InterfaceRecord tmp;

		SimpleFileWriter writer= SimpleFileWriter.openFileForWriting("csvfiles/ANKARA_"+records.get(0).getOnNode()+".csv");

		for (int i=0;i< records.size();i++){
			tmp= records.get(i);
			System.out.println(tmp.getTimeStamp()+";"+tmp.getOnNode()+";"+tmp.getInterfaceName()+";"+tmp.getThroughputIn()+";"+tmp.getThroughputOut());
			writer.println(tmp.getTimeStamp()+";"+tmp.getInterfaceName()+";"+tmp.getThroughputIn()+";"+tmp.getThroughputOut());
		}
		writer.close();
	}
	
}
