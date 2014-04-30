package feiraShop;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.kabeja.dxf.Bounds;
import org.kabeja.dxf.DXFConstants;
import org.kabeja.dxf.DXFEntity;
import org.kabeja.dxf.DXFLayer;
import org.kabeja.dxf.DXFText;
import org.kabeja.parser.ParseException;
import org.xml.sax.SAXException;

import com.google.gson.Gson;
import exporter.Exporter;
import exporter.ParserOptions;

public class StandExtrator{
	
	private String   inputFile;
	private String   outputFile;
	private String   standDataOutputFile;
	private String   numerationLayer;
	
	public StandExtrator(){
		this.inputFile       = null;
		this.outputFile      = null;
		this.numerationLayer = null;
	}
	
	public String getStandDataOutputFile() {
		return standDataOutputFile;
	}

	public void setStandDataOutputFile(String standDataOutputFile) {
		this.standDataOutputFile = standDataOutputFile;
	}

	public StandExtrator(String inputFile, String outputFile, String numerationLayer){
		this.inputFile       = inputFile;
		this.outputFile      = outputFile;
		this.numerationLayer = numerationLayer;
	}

	public void extract() throws SAXException, ParameterNotInformedException{
		
		
		
		if(this.inputFile ==  null){
			throw new ParameterNotInformedException("arquivo de origem", "setInputFile(String)");
		}
		if(this.outputFile == null){
			throw new ParameterNotInformedException("arquivo de destino", "setOutputFile(String)");
		}
		if(this.numerationLayer == null){
			throw new ParameterNotInformedException("layer de numeração", "setNumerationLayer(String)");
		}
		if(this.standDataOutputFile == null){
			throw new ParameterNotInformedException("arquivo para enumeração dos stands", "setNumerationLayer(String)");
		}
		
		Exporter exporter = new Exporter();
		try {
			exporter.setInputFile(inputFile);
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Arquivo "+inputFile+" não encontrado.");			
		} catch (ParseException e) {
			throw new RuntimeException(e.getMessage());
		}
		
		ParserOptions options = new ParserOptions();
		options.setDpi(1000);
		options.setQuality(1000);
		//options.setPaper(ParserOptions.PAPER_A3);
		options.setHeight(2000);
		options.setWidth(2000);
		options.setUnit(ParserOptions.UNIT_PIXEL);
		options.setBoundsRule(ParserOptions.BOUNDS_RULE_MODELSPACE);
		options.setOutputStyle(ParserOptions.OUTPUT_STYLE_NAME);
		options.setOrientation(ParserOptions.ORIENTATION_LANDSCAPE);
		options.setSvgOverflow(true);
		
		try {
			exporter.setOutputFile(outputFile);
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Arquivo "+inputFile+" não encontrado.");
		}
		
		exporter.generatePNGoutput(options);
		System.out.println("finished");
		
		DXFLayer layer = exporter.findLayer(numerationLayer);
		
		@SuppressWarnings("unchecked")
		List<DXFEntity> entities = layer.getDXFEntities(DXFConstants.ENTITY_TYPE_TEXT);				
		
		Gson gson = new Gson();
		StandList stands = new StandList();
		
				
		for(DXFEntity entity : entities){
			DXFText text = (DXFText) entity;
			Stand stand = new Stand();
			
			Bounds bounds = text.getBounds();
			stand.setName(text.getText().toString());
			stand.setHeight(bounds.getHeight());
			stand.setWidth(bounds.getWidth());
			stand.setX(bounds.getMinimumX());
			stand.setY(bounds.getMinimumY());
			
			stands.add(stand);
			//System.out.println(json);
		}
		String json = gson.toJson(stands);
		
		try {
			FileOutputStream jsonFile = new FileOutputStream(standDataOutputFile);
			jsonFile.write(json.getBytes());
			jsonFile.close();
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Arquivo "+standDataOutputFile+" não encontrado.");
		} catch (IOException e) {
			throw new RuntimeException("Falha ao escrever no arquivo "+standDataOutputFile);
		}
	}

	public String getInputFile() {
		return inputFile;
	}

	public void setInputFile(String inputFile) {
		this.inputFile = inputFile;
	}

	public String getOutputFile() {
		return outputFile;
	}

	public void setOutputFile(String outputFile) {
		this.outputFile = outputFile;
	}

	public String getNumerationLayer() {
		return numerationLayer;
	}

	public void setNumerationLayer(String numerationLayer) {
		this.numerationLayer = numerationLayer;
	}
	
	
}
