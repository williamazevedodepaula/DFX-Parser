package feiraShop.test;

import java.io.FileNotFoundException;

import org.junit.Test;
import org.xml.sax.SAXException;

import feiraShop.StandExtrator;

public class StandExtratorTest {

	@Test
	public void test() throws SAXException, FileNotFoundException {
		String numerationLayer = "5 - NUMERAÇÃO";
		String inputFile       = "files/BE_envio DXF_25-04-14_apenas planta loja_Numeração unidades no centro.dxf";
		String outputFile      = "files/BE_envio DXF_25-04-14_apenas planta loja_Numeração unidades no centro.png";
		String ouputStandData  = "files/stands.json";
		
		StandExtrator extrator = new StandExtrator();
		
		extrator.setInputFile(inputFile);
		extrator.setOutputFile(outputFile);
		extrator.setNumerationLayer(numerationLayer);
		extrator.setStandDataOutputFile(ouputStandData);
		extrator.extract();
	}

}
