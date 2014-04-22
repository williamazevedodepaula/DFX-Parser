package test;

import model.Exporter;

public class TestaParsePDF {
	public static void main(String[] args) {
		Exporter exporter = new Exporter();
		exporter.parseFile("files/BE_envio DXF_14-04-14.dxf","files/BE_envio DXF_14-04-14.pdf",Exporter.DOCUMENT_TYPE_PDF);
		
	}
}
