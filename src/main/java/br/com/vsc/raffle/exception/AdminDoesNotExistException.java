package br.com.vsc.raffle.exception;

public class AdminDoesNotExistException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public AdminDoesNotExistException(String message){
        super(message);
    }

}