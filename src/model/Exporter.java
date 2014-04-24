package model;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.kabeja.batik.tools.SAXJPEGSerializer;
import org.kabeja.batik.tools.SAXPDFSerializer;
import org.kabeja.batik.tools.SAXPNGSerializer;
import org.kabeja.batik.tools.SAXTIFFSerializer;
import org.kabeja.dxf.DXFBlock;
import org.kabeja.dxf.DXFDimensionStyle;
import org.kabeja.dxf.DXFDocument;
import org.kabeja.dxf.DXFEntity;
import org.kabeja.dxf.DXFHatchPattern;
import org.kabeja.dxf.DXFLayer;
import org.kabeja.dxf.DXFLineType;
import org.kabeja.dxf.DXFStyle;
import org.kabeja.dxf.DXFView;
import org.kabeja.dxf.DXFViewport;
import org.kabeja.dxf.objects.DXFDictionary;
import org.kabeja.dxf.objects.DXFObject;
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
	public static final int DOCUMENT_TYPE_TIF  = 3;
	
	
	public void parseFile(String sourceFile, String outputFile,int format) {
		
		DXFParser parser = (DXFParser) ParserBuilder.createDefaultParser();

		try {
		
		FileInputStream  in  = new FileInputStream(sourceFile);
		FileOutputStream out = new FileOutputStream(outputFile);
			
		parser.parse(in,DXFParser.DEFAULT_ENCODING);
		DXFDocument doc = parser.getDocument();
		System.out.println("Parser - Name");
		
		
		
		System.out.println(parser.getName());
		//the SVG will be emitted as SAX-Events
		//see org.xml.sax.ContentHandler for more information
		//ContentHandler myhandler = new ContentHandlerImpl();
		//the output - create first a SAXGenerator (SVG here)
		
		
		DXFDictionary dic = doc.getRootDXFDictionary();
		
	/*	System.out.println("getDXFObjectIterator:");
		Iterator it0 = doc.getRootDXFDictionary().getDXFObjectIterator();
		while(it0.hasNext()){
			DXFObject view = (DXFObject) it0.next();
			System.out.println(view.getID());
		}
	*/
		
		System.out.println("getDXFStyleIterator:");
		Iterator it = doc.getDXFStyleIterator();
		while(it.hasNext()){
			DXFStyle view = (DXFStyle) it.next();
			
			System.out.println(view.getName());			
			
		}
		
		System.out.println("getDXFBlockIterator:");
		Iterator it2 = doc.getDXFBlockIterator();
		while(it2.hasNext()){
			DXFBlock view = (DXFBlock) it2.next();
			System.out.println(view.getName());
			System.out.println("---------------entities:");
			Iterator i = view.getDXFEntitiesIterator();
			while(i.hasNext()){		
				DXFEntity e = (DXFEntity) i.next();
				System.out.println(e.getID());
				System.out.println(e.getLayerName());
			}
			//view.getDXFDocument().get
		}
		
		System.out.println("getDXFDimensionStyleIterator:");
		Iterator it3 = doc.getDXFDimensionStyleIterator();
		while(it3.hasNext()){
			DXFDimensionStyle view = (DXFDimensionStyle) it3.next();
			System.out.println(view.getName());
		
		}
		
		System.out.println("getDXFHatchPatternIterator:");
		Iterator it4 = doc.getDXFHatchPatternIterator();
		while(it4.hasNext()){
			DXFHatchPattern view = (DXFHatchPattern) it4.next();
			System.out.println(view.getID());
			
		}
		
		System.out.println("getDXFLayerIterator:");
		Iterator it5 = doc.getDXFLayerIterator();
		while(it5.hasNext()){
			DXFLayer view = (DXFLayer) it5.next();
			System.out.println(view.getName());
			
		}
		
		System.out.println("getDXFLineTypeIterator:");
		Iterator it6 = doc.getDXFLineTypeIterator();
		while(it6.hasNext()){
			DXFLineType view = (DXFLineType) it6.next();
			System.out.println(view.getName());
			
			
		}
		
		System.out.println("getDXFStyleIterator:");
		Iterator it7 = doc.getDXFStyleIterator();
		while(it7.hasNext()){
			DXFStyle view = (DXFStyle) it7.next();
			System.out.println(view.getName());
		}
		
		System.out.println("getDXFViewIterator:");
		Iterator it8 = doc.getDXFViewIterator();
		while(it8.hasNext()){
			DXFView view = (DXFView) it8.next();
			System.out.println(view.getName());
			
		}
		
		System.out.println("getDXFViewportIterator:");
		Iterator it9 = doc.getDXFViewportIterator();
		while(it9.hasNext()){
			DXFViewport view = (DXFViewport) it9.next();
			
			System.out.println(view.getLayerName());
			
			
		}
		
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
		case DOCUMENT_TYPE_TIF :
			serializer = new SAXTIFFSerializer();
			break;
		}
		
		
		
		HashMap<String, String> serializerProperties = new HashMap<String, String>();
		serializerProperties.put("dpi", "500");
		serializerProperties.put("quality", "1000");
	//	serializerProperties.put("width", "5000px");
	//	serializerProperties.put("height", "5000px");
		serializerProperties.put("orientation", "landscape");
		serializerProperties.put("paper", "a3");
		serializer.setProperties(serializerProperties);
		
		
		
		//setup properties		
		HashMap<String, String> properties = new HashMap<String, String>();
	//	properties.put("width", "5000px");
	//	properties.put("height", "5000px");
		
		properties.put("output-style", "output-style-name");
		properties.put("svg-overflow", "true");
		
		
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
