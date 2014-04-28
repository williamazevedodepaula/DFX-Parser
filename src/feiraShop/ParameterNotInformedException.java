package feiraShop;

public class ParameterNotInformedException extends RuntimeException{


	private static final long serialVersionUID = 2661653857340835101L;
	
	ParameterNotInformedException(String parametro){
		super("O parâmetro "+parametro+" não foi informado");
	}
	ParameterNotInformedException(String parametro, String metodo){		
		super("O parâmetro "+parametro+" não foi informado. Utilize o método "+metodo);
	}
	
}
