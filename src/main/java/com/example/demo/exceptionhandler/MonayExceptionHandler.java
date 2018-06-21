package com.example.demo.exceptionhandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class MonayExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messageSource;

	/*
	 * Trata Erros quando o usuario tenta salvar uma entidade com argumentos invalidos
	 */
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		String menssagemUsuario = messageSource.getMessage("menssagem.invalida", null, LocaleContextHolder.getLocale());
		String menssagemDesenvolvedor = ex.getCause() != null ? ex.getCause().toString() : ex.toString(); 
		List<Erro> erros = Arrays.asList(new Erro(menssagemUsuario, menssagemDesenvolvedor));
		return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<Erro> erros = criarListaDeErros(ex.getBindingResult());
		return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
	}
	
	/*
	 * Trata erros de recurso não encontrado,
	 * Ex..: Usuario apaga uma entidade que não existe na tabela; 
	 */
	@ExceptionHandler({EmptyResultDataAccessException.class})
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<Object> handleEmptyResultDataAccessException( EmptyResultDataAccessException ex, WebRequest request) {
		
		
		String menssagemUsuario = messageSource.getMessage("recurso.nao-encontrado", null, LocaleContextHolder.getLocale());
		String menssagemDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(menssagemUsuario, menssagemDesenvolvedor));
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
		
	}

	private List<Erro> criarListaDeErros(BindingResult bindingResult) {
		List<Erro> erros = new ArrayList<>();

		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			String menssagemUsuario = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
			String menssagemDesenvolvedor = fieldError.toString();
			erros.add(new Erro(menssagemUsuario, menssagemDesenvolvedor));
		}

		return erros;
	}

	public static class Erro {
		private String menssagemUsuario;
		private String menssagemDesenvolvedor;

		public Erro(String menssagemUsuario, String menssagemDesenvolvedor) {
			this.menssagemUsuario = menssagemUsuario;
			this.menssagemDesenvolvedor = menssagemDesenvolvedor;
		}

		public String getMenssagemUsuario() {
			return menssagemUsuario;
		}

		public void setMenssagemUsuario(String menssagemUsuario) {
			this.menssagemUsuario = menssagemUsuario;
		}

		public String getMenssagemDesenvolvedor() {
			return menssagemDesenvolvedor;
		}

		public void setMenssagemDesenvolvedor(String menssagemDesenvolvedor) {
			this.menssagemDesenvolvedor = menssagemDesenvolvedor;
		}
	}

}
