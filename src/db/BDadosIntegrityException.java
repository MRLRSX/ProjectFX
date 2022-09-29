package db;

public class BDadosIntegrityException extends RuntimeException{


	private static final long serialVersionUID = 1L;

	public BDadosIntegrityException(String mensagem) {
           super(mensagem);		
	}
}
