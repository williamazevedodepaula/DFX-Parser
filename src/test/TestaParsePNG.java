package test;

import model.Exporter;

public class TestaParsePNG {
	public static void main(String[] args) {
		Exporter exporter = new Exporter();
		exporter.parseFile("files/BE_envio DXF_14-04-14.dxf","files/BE_envio DXF_14-04-14.png",Exporter.DOCUMENT_TYPE_PNG);
		
	}
}
