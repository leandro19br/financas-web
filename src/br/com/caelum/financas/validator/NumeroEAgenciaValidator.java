package br.com.caelum.financas.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintViolation;

import br.com.caelum.financas.modelo.Conta;

public class NumeroEAgenciaValidator implements ConstraintValidator<NumeroEAgencia, Conta> {

	@Override
	public void initialize(NumeroEAgencia arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isValid(Conta conta, ConstraintValidatorContext ctx) {
		if (conta == null) {
			return true;
		}
		
		boolean agenciaEhVazia = (conta.getAgencia() == null || conta.getAgencia().trim().isEmpty());
		boolean numeroEhVazia = (conta.getNumero() == null || conta.getNumero().trim().isEmpty());
		
		return !(agenciaEhVazia ^ numeroEhVazia);
	}

}
