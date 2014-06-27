package feiraShop;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.kabeja.dxf.Bounds;
import org.kabeja.dxf.DXFCircle;
import org.kabeja.dxf.DXFConstants;
import org.kabeja.dxf.DXFDocument;
import org.kabeja.dxf.DXFEntity;
import org.kabeja.dxf.DXFLayer;
import org.kabeja.dxf.DXFText;
import org.kabeja.dxf.helpers.Point;
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
		options.setHeight(2000);
		options.setWidth (2000);
		options.setUnit(ParserOptions.UNIT_PIXEL);
		options.setBoundsRule (ParserOptions.BOUNDS_RULE_MODELSPACE);
		options.setOutputStyle(ParserOptions.OUTPUT_STYLE_NAME);
		options.setOrientation(ParserOptions.ORIENTATION_AUTO);
		options.setProportional(true);
		options.setSvgOverflow(true);
		
		try {
			exporter.setOutputFile(outputFile);
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Arquivo "+inputFile+" não encontrado.");
		}
		
		DXFLayer layer = exporter.findLayer(numerationLayer);
		exporter.generatePNGoutput(options);
				
		
		//Busca todos os elementos de texto na layer procurada (cada um representa um stand)
		@SuppressWarnings("unchecked")
		List<DXFEntity> entities = layer.getDXFEntities(DXFConstants.ENTITY_TYPE_TEXT);				
		
		Gson gson = new Gson();
		StandList stands = new StandList();			

		/* Pega as coordenadas iniciais do documento, invertendo o eixo Y (multiplica por -1), 
		 * pois o eixo do canvas do Android é invertido em relação ao autocad)*/
		
		DXFDocument doc = exporter.getDXFDocument();
		Bounds docBounds = doc.getBounds(true);
		Point coordenadasTranslacao = new Point(docBounds.getMinimumX(),-docBounds.getMaximumY(),0);		
		double width  = docBounds.getWidth();
		double height = docBounds.getHeight();
		
		//Percorre os elementos encontrados
		for(DXFEntity entity : entities){
			DXFText text = (DXFText) entity;
			Stand stand = new Stand();						
			
			
			/*
			 * Realiza a translação da figura para o ponto 0,0
			 * facilitando a conversão para o android, independente da unidade
			 * de medida utilizada.
			 * O eixo Y é invertido, de forma que as coordenadas negativas se tornam positivas
			 * 
			 *        |                                     |
			 *(0,0) --|-----------------> + X             --| _____------------------> + X
			 *        |                                     |(FIGURA)
			 *        |          _____                      | ------
			 *        |         (FIGURA)       ====>        |      
			 *        |          ------                     |
			 *        |                                     |
			 *        |                                     |
			 *        \/                                    \/
			 *      - Y                                 + Y	
			 */			
			
			Bounds bounds = text.getBounds();
			stand.setName(text.getText().toString());
			stand.setHeight(bounds.getHeight());
			stand.setWidth(bounds.getWidth());
			stand.setX(bounds.getMinimumX()-coordenadasTranslacao.getX());
			stand.setY(-bounds.getMaximumY()-coordenadasTranslacao.getY());
			
			//Desloca as coordenadas para o meio do elemento
			//stand.setX(stand.getY()+bounds.getHeight()/2);
			//stand.setY(stand.getX()+bounds.getWidth()/2);
			
			
			/*
			 * Reescala as coordenadas, convertendo do plano cartesiano translatado do CAD
			 * para o plano cartesiano da imagem PNG gerada (em pixels)
			 */
			
			
			/*
			 * Se a altura do modela na planta for maior que a largura,
			 * A planta terá o tamanho ajustado de maneira a ocupar toda a altura.
			 * Neste caso, aplica-se uma regra de três para obter a nova coordenada Y do elemento
			 * Para obter a nova coordenada X, deve-se supor que a nova largura é proporcional
			 * à anterior, e em seguida adicionar a diferença da largura proporcional para a real
			 * e dividir por dois (pois o kabeja introduz metade dessa diferença de cada lado,
			 * mantendo a imagem centralizada)
			 * 
			 * Equação para Y:
			 * ---------------
			 * 
			 * 	 y_novo = (altura_nova * y_atual) / altura_antiga
			 * 
			 * Equação para X:
			 * ---------------
			 * 
			 *    largura_esperada = ((largura_atual * altura_nova) / altura_atual)
			 *    
			 *    x_esperado = (largura_esperada * x_antigo)/largura_antiga
			 *    
			 *    diferenca  = largura_nova - largura_esperada
			 *    
			 *    x_novo = x_esperado + diferenca/2
			 */
			if(height>width){
				
				double yFinalAntigo = stand.getEndY();
				double xFinalAntigo = stand.getEndX();
				double yAntigo      = stand.getY();
				double xAntigo      = stand.getX();
				
				double yFinalNovo   = (options.getHeight() * yFinalAntigo) / height;
				double yNovo        = (options.getHeight() * yAntigo     ) / height; 
				stand.setY(yNovo);
				stand.setHeight(yFinalNovo-yNovo);								
				
				
				double largEsperada = ((width*options.getHeight()) /height);				
				double xEsperado    = (largEsperada * xAntigo    ) / width;				 
				double diference    = exporter.getOutputWidth()-largEsperada;
				double xNovo        = xEsperado+(diference/2);
				stand.setX(xNovo);				
				
				//Utiliza o mesmo processo para encontrar as coordenadas do fim do elemento
				double xFinalEsperado = (largEsperada * xFinalAntigo) / width;
				double xFinalNovo     = xFinalEsperado + (diference/2);
				
				//Obtém o tamanho do elemento através das coordenadas inicial e final
				stand.setWidth(xFinalNovo-xNovo);
				
				
		   /*
			* Caso contrário (se a altura do modela na planta for maior ou igual à largura),
			* a planta terá o tamanho ajustado de maneira a ocupar toda a largura.
			* Neste caso, aplica-se uma regra de três para obter a nova coordenada X do elemento
			* Para obter a nova coordenada Y, deve-se supor que a nova altura é proporcional
			* à anterior, e em seguida adicionar a diferença da altura proporcional para a real
			* e dividir por dois (pois o kabeja introduz metade dessa diferença de cada lado,
			* mantendo a imagem centralizada)
			* 
			* Equação para X:
			* ---------------
			* 
			* 	 x_novo = (largura_nova * x_atual) / largura_antiga
			* 
			* Equação para X:
			* ---------------
			* 
			*    altura_esperada = ((altura_atual * largura_nova) / largura_atual)
			*    
			*    y_esperado = (altura_esperada * y_atual) / altura_antiga
			*    
			*    diferenca  = altura_nova - altura_esperada
			*    
			*    y_novo = y_esperado + diferenca/2
			*/
			}else{
				stand.setX((exporter.getOutputWidth() * stand.getX()) / width);
				
				double yFinalAntigo = stand.getEndY();
				
				double alturaEsperada = ((height*exporter.getOutputWidth())/width);				
				double yEsperado = (alturaEsperada * stand.getY()) / height;
				double diference = exporter.getOutputHeight()-alturaEsperada;
				stand.setX(yEsperado+(diference/2));
				
				//Utiliza o mesmo processo para encontrar as coordenadas do fim do elemento
				double yFinalEsperado = (alturaEsperada * yFinalAntigo) / height;
				double yFinalNovo     = yFinalEsperado + (diference/2);
				
				//Obtém o tamanho do elemento através das coordenadas inicial e final
				stand.setHeight(yFinalNovo-stand.getY());
			}
			
			
			stands.add(stand);			
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
