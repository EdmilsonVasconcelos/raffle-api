package br.com.vsc.raffle.exception;

public class NumberRaffleDoesNotExistException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NumberRaffleDoesNotExistException(String message){
        super(message);
    }

}