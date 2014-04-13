package test;

import model.Exporter;

public class TestaParseJPEG {
	public static void main(String[] args) {
		Exporter exporter = new Exporter();
		exporter.parseFile("files/BE_28-02-14.dxf","files/BE_28-02-14.jpeg",Exporter.DOCUMENT_TYPE_JPEG);
		
	}
}
