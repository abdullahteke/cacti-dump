package com.abdullahteke.cactidump.main;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.abdullahteke.cactidump.controller.XMLFileManager;

public class ParseXMLFile2 {

	public static void main(String[] args)  {
		
//		String xmlFile="xmlfiles/umumi_traffic_out_250.xml";
//		String csvFile="csvfiles/umumi_traffic_out_250.csv";
		
		String xmlFile="xmlfiles/5mincpu.xml";
		String csvFile="csvfiles/5mincpu.csv";
		
//		XMLFileManager.getInstance().createXML2CSV(xmlFile, csvFile);
		XMLFileManager.getInstance().createXML2CSV(xmlFile, csvFile);
//        Date date = new Date(1388790000000L);
//        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//        String formatted = format.format(date);
//        System.out.println(formatted);


	}

}
