package br.com.vsc.raffle.exception;

public class AdminExistsException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public AdminExistsException(String message){
        super(message);
    }

}
