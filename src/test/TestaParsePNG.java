package test;

import model.Exporter;

public class TestaParsePNG {
	public static void main(String[] args) {
		Exporter exporter = new Exporter();
		exporter.parseFile("files/BE_28-02-14.dxf","files/BE_28-02-14.png",Exporter.DOCUMENT_TYPE_PNG);
		
	}
}
