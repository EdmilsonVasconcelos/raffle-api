package br.com.vsc.raffle.exception;

public class ProductExistsException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public ProductExistsException(String message){
        super(message);
    }

}