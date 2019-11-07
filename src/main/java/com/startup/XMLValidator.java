package com.startup;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

public class XMLValidator {

	public static void main(String[] args) {
		
		String xsdPath = "/home/pranit/Downloads/DUO_RelatiefVerzuim_V8.xsd";
		String xmlPath = "/home/pranit/Downloads/invalidReq.xml";
		
		if(validateXMLSchema(xsdPath, xmlPath))
			System.out.println("Valid");
		else
			System.out.println("Invalid");

	}

	public static boolean validateXMLSchema(String xsdPath, String xmlPath) {
		try
	    {
	        SchemaFactory factory = 
	            SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
	        Schema schema = factory.newSchema(new StreamSource(xsdPath));
	        Validator validator = schema.newValidator();
	        validator.validate(new StreamSource(xmlPath));
	        return true;
	    }
	    catch(Exception ex)
	    {
	    	ex.printStackTrace();
	        return false;
	    }

	}

}
