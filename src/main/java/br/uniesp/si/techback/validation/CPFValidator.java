package br.uniesp.si.techback.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CPFValidator
        implements ConstraintValidator<CPFValido, String> {

    @Override
    public boolean isValid(String cpf,
                           ConstraintValidatorContext context) {

        if (cpf == null || cpf.isBlank()) {
            return true;
        }

        cpf = cpf.replaceAll("\\D", "");

        return cpf.length() == 11;
    }
}