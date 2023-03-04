package br.com.vsc.raffle.exception;

public class AdminNotExistException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public AdminNotExistException(String message){
        super(message);
    }

}