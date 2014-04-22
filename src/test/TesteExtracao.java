package test;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.util.Iterator;
import java.util.List;

import org.apache.batik.apps.svgbrowser.JSVGViewerFrame.NewWindowAction;
import org.junit.Test;
import org.kabeja.dxf.Bounds;
import org.kabeja.dxf.DXFConstants;
import org.kabeja.dxf.DXFDocument;
import org.kabeja.dxf.DXFEntity;
import org.kabeja.dxf.DXFLayer;
import org.kabeja.dxf.DXFText;
import org.kabeja.parser.DXFParser;
import org.kabeja.parser.Parser;
import org.kabeja.parser.ParserBuilder;

public class TesteExtracao {
	
/*	static {
        logger = Logger.getLogger(TestesDXF.class);
    }	*/
	String pathDXFFile = "files/BE_envio DXF_14-04-14.dxf";
	
	@Test
	public void listaTodasLayers(){
		try {            
            FileInputStream fis = new FileInputStream(pathDXFFile);

            if (fis == null) {
                throw new NullPointerException("Arquivo DXF não encontrado");
            }

            Parser parser = ParserBuilder.createDefaultParser();
            parser.parse(fis, DXFParser.DEFAULT_ENCODING);
            DXFDocument doc = parser.getDocument();
            
            System.out.println("Listagem de Layers:");
            Iterator iterator = doc.getDXFLayerIterator();
            while(iterator.hasNext()){
            	DXFLayer layer = (DXFLayer) iterator.next();
            	System.out.println(layer.getName());
            }                        

        } catch (Exception e) {
            e.printStackTrace();
        }	
	}

	
	
	
	@Test
	public void encontraLayer() {
        try {
            String pathDXFFile = "files/BE_envio DXF_14-04-14.dxf";
            String layerName = "5 - NUMERAÇÃO";
            FileInputStream fis = new FileInputStream(pathDXFFile);

            if (fis == null) {
                throw new NullPointerException("Arquivo DXF não encontrado");
            }

            Parser parser = ParserBuilder.createDefaultParser();
            parser.parse(fis, DXFParser.DEFAULT_ENCODING);
            DXFDocument doc = parser.getDocument();
            DXFLayer layer = doc.getDXFLayer(layerName);
            

            if ((layer == null)||(!layer.getName().equals(layerName))) {
                throw new NullPointerException("Layer " + layerName + " não encontrada");
            }

            System.out.println("Listagem de entidades:");
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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	
 /*@Override
    protected String getClassName() {
        return TestesDXF.class.getSimpleName();
    }
*/

}
