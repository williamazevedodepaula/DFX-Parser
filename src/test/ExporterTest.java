package test;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.kabeja.dxf.Bounds;
import org.kabeja.dxf.DXFConstants;
import org.kabeja.dxf.DXFEntity;
import org.kabeja.dxf.DXFLayer;
import org.kabeja.dxf.DXFText;
import org.kabeja.parser.ParseException;
import org.xml.sax.SAXException;

import exporter.Exporter;
import exporter.ParserOptions;

public class ExporterTest {

	@Test
	public void deveGerarUmPNG(){
		Exporter exporter = new Exporter();
		String   sourceFile         = "files/BE_envio DXF_25-04-14_apenas planta loja_Numeração unidades no centro.dxf";
		String   outputFile         = "files/BE_envio DXF_25-04-14_apenas planta loja_Numeração unidades no centro.png";
		int      format             = Exporter.DOCUMENT_TYPE_PNG;

		boolean done = false;
		
		System.out.println("Parsing...");
		try {
			exporter.setInputFile(sourceFile);
			
			ParserOptions options = new ParserOptions();
			options.setDpi(500);
			options.setQuality(1000);
			options.setPaper(ParserOptions.PAPER_A3);
			options.setBoundsRule(ParserOptions.BOUNDS_RULE_MODELSPACE);
			options.setOutputStyle(ParserOptions.OUTPUT_STYLE_NAME);
			options.setOrientation(ParserOptions.ORIENTATION_LANDSCAPE);
			options.setSvgOverflow(true);
			
			exporter.generateOutput(outputFile,format,options);
			done = true;
		} catch (FileNotFoundException e) {
			System.out.println("ERRO: Arquivo não encontrado");
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (SAXException e){
			e.printStackTrace();
		}
		
		
		System.out.println("finished");
		assertEquals(true, done);		
	}
	
	@Test
	public void listaTodasLayers(){
		Exporter exporter = new Exporter();
		String   sourceFile         = "files/BE_envio DXF_25-04-14_apenas planta loja_Numeração unidades no centro.dxf";
		try {            
            
			exporter.setInputFile(sourceFile);			
            System.out.println("Listagem de Layers:");
            
            @SuppressWarnings("rawtypes")
			Iterator iterator = exporter.getDXFDocument().getDXFLayerIterator();
            while(iterator.hasNext()){
            	DXFLayer layer = (DXFLayer) iterator.next();
            	System.out.println(layer.getName());
            }                        

        } catch (Exception e) {
            e.printStackTrace();
        }	
	}
	
	@Test
	public void encontraLayer() throws FileNotFoundException, ParseException{
		Exporter exporter = new Exporter();
		String   sourceFile         = "files/BE_envio DXF_25-04-14_apenas planta loja_Numeração unidades no centro.dxf";		

		String   identifierLayer    = "5 - NUMERAÇÃO";
		
		
		exporter.setInputFile(sourceFile);
		
		DXFLayer layer = exporter.findLayer(identifierLayer);
		if(layer == null){
			throw new RuntimeException("Layer " + identifierLayer + " não encontrada");
		}
		
		System.out.println("Listagem de entidades:");
		
        @SuppressWarnings("unchecked")
		List<DXFEntity> entities = layer.getDXFEntities(DXFConstants.ENTITY_TYPE_TEXT);
        for (DXFEntity entity : entities) {
        	DXFText text = (DXFText) entity; 
        	System.out.println("Stand: "+text.getText().toString());
        	System.out.println("Bounds:");
        	
        	System.out.println("Escala: "+text.getScaleX());
        	
        	Bounds bound = entity.getBounds();
        	System.out.println("	Min. X: "+bound.getMinimumX() );
        	System.out.println("	Max. X: "+bound.getMaximumX() );
        	System.out.println("	width: "+bound.getWidth() );
        	System.out.println("	Min. Y: "+bound.getMinimumY() );
        	System.out.println("	Max. Y: "+bound.getMaximumY() );
        	System.out.println("	height: "+bound.getHeight() );
        	System.out.println("	");
        	System.out.println("	");        	        	 
        }
	}

}
