package br.com.vsc.raffle.exception;

public class RaffleException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RaffleException(String message){
        super(message);
    }

}