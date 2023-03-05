package br.com.vsc.raffle.exception;

import java.util.ArrayList;
import java.util.List;

import br.com.vsc.raffle.dto.error.ErrorValidationDTO;
import br.com.vsc.raffle.dto.error.ExceptionResponse;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@AllArgsConstructor
public class ErrorValidationHandler {
	
	private MessageSource messageSource;
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<ErrorValidationDTO> handle(MethodArgumentNotValidException exception) {
		List<ErrorValidationDTO> errors = new ArrayList<>();
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
		
		fieldErrors.forEach(e -> {
			String message = messageSource.getMessage(e, LocaleContextHolder.getLocale());
			ErrorValidationDTO error = new ErrorValidationDTO(e.getField(), message);
			errors.add(error);
		});
		
		return errors;
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(AdminAlreadyExistsException.class)
	public ExceptionResponse handle(AdminAlreadyExistsException exception) {
		return new ExceptionResponse(exception.getMessage());
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ProductAlreadyExistsException.class)
	public ExceptionResponse handle(ProductAlreadyExistsException exception) {
		return new ExceptionResponse(exception.getMessage());
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ProductDoesNotExistException.class)
	public ExceptionResponse handle(ProductDoesNotExistException exception) {
		return new ExceptionResponse(exception.getMessage());
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(PurchaseNotExistsException.class)
	public ExceptionResponse handle(PurchaseNotExistsException exception) {
		return new ExceptionResponse(exception.getMessage());
	}

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(RaffleException.class)
	public ExceptionResponse handle(RaffleException exception) {
		return new ExceptionResponse(exception.getMessage());
	}

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(RaffleDoesNotExistException.class)
	public ExceptionResponse handle(RaffleDoesNotExistException exception) {
		return new ExceptionResponse(exception.getMessage());
	}
}
