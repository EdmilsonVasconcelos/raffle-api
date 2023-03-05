package br.com.vsc.raffle.exception;

public class RaffleDoesNotExistException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RaffleDoesNotExistException(String message){
        super(message);
    }

}