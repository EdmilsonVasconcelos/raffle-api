package br.com.vsc.raffle.exception;

public class AdminAlreadyExistsException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public AdminAlreadyExistsException(String message){
        super(message);
    }

}
