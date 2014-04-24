package model;
import org.kabeja.batik.tools.SAXJPEGSerializer;
import org.kabeja.batik.tools.SAXPNGSerializer;
import org.kabeja.xml.SAXSerializer;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;


public class ContentHandlerImpl extends SAXJPEGSerializer{

	@Override
	public void characters(char[] arg0, int arg1, int arg2) throws SAXException {
		// TODO Auto-generated method stub
		System.out.println("characters----------------" );
		System.out.println("arg0: "+arg0.toString() );
		System.out.println("arg1: "+arg1 );
		System.out.println("arg2: "+arg2 );
	}

	@Override
	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub
		System.out.println("endDocument----------------" );
	}

	@Override
	public void endElement(String arg0, String arg1, String arg2)
			throws SAXException {
		// TODO Auto-generated method stub
		System.out.println("endElement----------------" );
		System.out.println("arg0: "+arg0.toString() );
		System.out.println("arg1: "+arg1 );
		System.out.println("arg2: "+arg2 );
	}

	@Override
	public void endPrefixMapping(String arg0) throws SAXException {
		// TODO Auto-generated method stub
		System.out.println("Arg0: "+arg0);
		
	}

	@Override
	public void ignorableWhitespace(char[] arg0, int arg1, int arg2)
			throws SAXException {
		// TODO Auto-generated method stub
		System.out.println("ignorableWhitespace----------------" );
		System.out.println("arg0: "+arg0.toString() );
		System.out.println("arg1: "+arg1 );
		System.out.println("arg2: "+arg2 );
		
	}

	@Override
	public void processingInstruction(String arg0, String arg1)
			throws SAXException {
		// TODO Auto-generated method stub
		System.out.println("Arg0: "+arg0);
		System.out.println("Arg1: "+arg1);
		
	}

	@Override
	public void setDocumentLocator(Locator arg0) {
		// TODO Auto-generated method stub
		System.out.println("arg0 : "+arg0);
	}

	@Override
	public void skippedEntity(String arg0) throws SAXException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startElement(String arg0, String arg1, String arg2,
			Attributes arg3) throws SAXException {
		// TODO Auto-generated method stub
		System.out.println("startElement----------------" );
		System.out.println("arg0: "+arg0.toString() );
		System.out.println("arg1: "+arg1 );
		System.out.println("arg2: "+arg2 );
	}

	@Override
	public void startPrefixMapping(String arg0, String arg1)
			throws SAXException {
		// TODO Auto-generated method stub
		
	}

}
