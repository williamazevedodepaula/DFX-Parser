package model;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.kabeja.batik.tools.SAXJPEGSerializer;
import org.kabeja.batik.tools.SAXPDFSerializer;
import org.kabeja.batik.tools.SAXPNGSerializer;
import org.kabeja.dxf.DXFDocument;
import org.kabeja.parser.DXFParser;
import org.kabeja.parser.ParseException;
import org.kabeja.parser.Parser;
import org.kabeja.parser.ParserBuilder;
import org.kabeja.svg.SVGGenerator;
import org.kabeja.xml.SAXGenerator;
import org.kabeja.xml.SAXSerializer;

public class Exporter {
	
	public static final int DOCUMENT_TYPE_PNG  = 0;
	public static final int DOCUMENT_TYPE_JPEG = 1;
	public static final int DOCUMENT_TYPE_PDF  = 2;
	
	
	public void parseFile(String sourceFile, String outputFile,int format) {
		Parser parser = ParserBuilder.createDefaultParser();
		try {
		
		FileInputStream  in  = new FileInputStream(sourceFile);
		FileOutputStream out = new FileOutputStream(outputFile);
			
		parser.parse(in,DXFParser.DEFAULT_ENCODING);
		DXFDocument doc = parser.getDocument();
		//the SVG will be emitted as SAX-Events
		//see org.xml.sax.ContentHandler for more information
		//ContentHandler myhandler = new ContentHandlerImpl();
		//the output - create first a SAXGenerator (SVG here)
		SAXGenerator generator = new SVGGenerator();
		
		SAXSerializer serializer = null;
		
		switch(format){
		case DOCUMENT_TYPE_PNG :
			serializer = new SAXPNGSerializer();
			break;
		case DOCUMENT_TYPE_JPEG :
			serializer = new SAXJPEGSerializer();
			break;
		case DOCUMENT_TYPE_PDF :
			serializer = new SAXPDFSerializer();
			break;
		}
		HashMap<String, String> serializerProperties = new HashMap<String, String>();
		serializerProperties.put("dpi", "800");
		serializerProperties.put("quality", "300");
	//	serializerProperties.put("width", "5000px");
	//	serializerProperties.put("height", "5000px");
		serializerProperties.put("orientation", "portrait");
		serializerProperties.put("paper", "a4");
		serializer.setProperties(serializerProperties);
		
		
		
		//setup properties		
		HashMap<String, String> properties = new HashMap<String, String>();
	//	properties.put("width", "5000px");
	//	properties.put("height", "5000px");
	//	properties.put("bounds-rule", "paperspace");
		
		/*
		 * Propriedades aceitas
		 * 
		 * -> bounds-rule  (defines the way of calculation/setup the bounds of the Document)
		 * 			Valores: * kabeja
		 * 					 * paperspace
		 * 					 * Paperspace-Limits
		 * 					 * Modelspace
		 * 					 * Modelspace-Limits	
		 * 
		 * -> output-style
		 * 			Valores: * layout
		 * 					 * plotsetting
		 * 					 * output-style-name
		 * -> width   
		 * 		unidades aceitas: * in (inches)
		 * 						  * mm (milimetros)
		 * 						  * px (pixels)
		 * -> height
		 * 		unidades aceitas: * in (inches)
		 * 						  * mm (milimetros)
		 * 						  * px (pixels)
		 * 
		 * -> svg-overflow: ? 
		 * */
		
		generator.setProperties(properties);
		//start the output
		try {
			System.out.println("parsing...");
			serializer.setOutput(out);
			
						
			generator.generate(doc,serializer,new HashMap<Object, Object>());
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}		
		
		System.out.println("finished");
	}

}
