package exporter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Iterator;

import org.xml.sax.SAXException;
import org.kabeja.batik.tools.SAXJPEGSerializer;
import org.kabeja.batik.tools.SAXPDFSerializer;
import org.kabeja.batik.tools.SAXPNGSerializer;
import org.kabeja.batik.tools.SAXTIFFSerializer;
import org.kabeja.dxf.DXFDocument;
import org.kabeja.dxf.DXFEntity;
import org.kabeja.dxf.DXFLayer;
import org.kabeja.parser.DXFParser;
import org.kabeja.parser.ParseException;
import org.kabeja.parser.ParserBuilder;
import org.kabeja.svg.SVGGenerator;
import org.kabeja.xml.SAXGenerator;
import org.kabeja.xml.SAXSerializer;

public class Exporter {
	
	public static final int DOCUMENT_TYPE_PNG  = 0;
	public static final int DOCUMENT_TYPE_JPEG = 1;
	public static final int DOCUMENT_TYPE_PDF  = 2;
	public static final int DOCUMENT_TYPE_TIF  = 3;
	
	
	private FileInputStream  in;
	FileOutputStream out;
	DXFParser parser;
	DXFDocument doc;
	
	public Exporter(){
		in  = null;
		out = null;
		doc = null;
	}
	
	public void setInputFile(String sourceFile) throws FileNotFoundException, ParseException{
		in  = new FileInputStream(sourceFile);
		
		parser = (DXFParser) ParserBuilder.createDefaultParser();
		
		parser.parse(in,DXFParser.DEFAULT_ENCODING);
		doc = parser.getDocument();
	}
	
	public void setOutputFile(String outputFile) throws FileNotFoundException{
		out = new FileOutputStream(outputFile);
	}
	
	public void generatePNGoutput(String outputFile,ParserOptions options) throws SAXException, FileNotFoundException{
		this.generateOutput(outputFile,Exporter.DOCUMENT_TYPE_PNG,options);
	}
	
	public void generatePNGoutput(ParserOptions options) throws SAXException{
		this.generateOutput(Exporter.DOCUMENT_TYPE_PNG,options);
	}
	
	public void generateJPEGoutput(String outputFile,ParserOptions options) throws SAXException, FileNotFoundException{
		this.generateOutput(outputFile,Exporter.DOCUMENT_TYPE_JPEG,options);
	}
	
	public void generateJPEGoutput(ParserOptions options) throws SAXException{
		this.generateOutput(Exporter.DOCUMENT_TYPE_JPEG,options);
	}
	
	public void generateTIFoutput(String outputFile,ParserOptions options) throws SAXException, FileNotFoundException{
		this.generateOutput(outputFile,Exporter.DOCUMENT_TYPE_TIF,options);
	}
	
	public void generateTIFoutput(ParserOptions options) throws SAXException{
		this.generateOutput(Exporter.DOCUMENT_TYPE_TIF,options);
	}
	
	public void generatePDFoutput(String outputFile,ParserOptions options) throws SAXException, FileNotFoundException{
		this.generateOutput(outputFile,Exporter.DOCUMENT_TYPE_PDF,options);
	}
	
	public void generatePDFoutput(ParserOptions options) throws SAXException{
		this.generateOutput(Exporter.DOCUMENT_TYPE_PDF,options);
	}
	
	public void generateOutput(String outputFile, int format, ParserOptions options) throws SAXException, FileNotFoundException{
		this.setOutputFile(outputFile);
		this.generateOutput(format,options);
	}
	
	public void generateOutput(int format, ParserOptions options) throws SAXException{
		SAXGenerator  generator = new SVGGenerator();		
		SAXSerializer serializer = null;
		//out = new FileOutputStream(outputFile);
		
		if(in == null){
			throw new RuntimeException("The input file was not informed. Please, use the setInputFile before calling generateOutput");
		}
		if(out == null){
			throw new RuntimeException("The output file was not informed. Please, use the setOutputFile before calling generateOutput");
		}
		
		if(options == null){
			options = new ParserOptions(); 
		}
		
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
		HashMap<String, String> generatorProperties = new HashMap<String, String>();
		
		serializerProperties.put("dpi"    , Integer.toString(options.getDpi()));
		serializerProperties.put("quality", Integer.toString(options.getQuality()));
		if(options.getPaper() == null){
			serializerProperties.put("width"  , Integer.toString(options.getWidth())+options.getUnit());
			serializerProperties.put("height" , Integer.toString(options.getHeight())+options.getUnit());
			generatorProperties.put("width"  ,  Integer.toString(options.getWidth())+options.getUnit());
			generatorProperties.put("height" ,  Integer.toString(options.getHeight())+options.getUnit());
		}else{
			serializerProperties.put("paper", options.getPaper());
		}
		serializerProperties.put("orientation", options.getOrientation());				
		if(options.getBoundsRule() != null){
			generatorProperties.put("bounds-rule", options.getBoundsRule());
		}
		if(options.getOutputStyle() != null){
			generatorProperties.put("output-style", options.getOutputStyle());
		}
		generatorProperties.put("svg-overflow", Boolean.toString(options.isSvgOverflow()));
		
		
		serializer.setProperties(serializerProperties);
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
		
		generator.setProperties(generatorProperties);
		serializer.setOutput(out);				
		generator.generate(doc,serializer,new HashMap<Object, Object>());
		
	}
	
	private void validateDoc(){
		if(doc == null){
			throw new RuntimeException("The input file was not informed. Please, use the setInputFile before calling generateOutput");
		}
	}
	
	public DXFDocument getDXFDocument(){
		this.validateDoc();
		return doc;
	}
	
	public DXFLayer findLayer(String layerName){
		this.validateDoc();
		DXFLayer layer = doc.getDXFLayer(layerName);        
        if ((layer == null)||(!layer.getName().equals(layerName))) {
            return null;
        }else{
        	return layer;
        }
	}
		
}
