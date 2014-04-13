package test;

import model.Exporter;

public class TestaParsePDF {
	public static void main(String[] args) {
		Exporter exporter = new Exporter();
		exporter.parseFile("files/BE_28-02-14.dxf","files/BE_28-02-14.pdf",Exporter.DOCUMENT_TYPE_PDF);
		
	}
}
