package com.example.demo.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class NewUserDtoValidator implements ConstraintValidator<NewUserDtoConstraint, String> {

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {

        List<Character> specialCharacters = Arrays.asList('_', '#', '$', '%','.');

        return password.length() > 8
                && containsOneSpecialCharacter(password, specialCharacters)
                && containsOneNumber(password)
                && containsOneCapitalizedCharacter(password);
    }

    private boolean containsOneSpecialCharacter(String password, List<Character> specialCharacters) {
        return (password.chars().filter(ch -> specialCharacters.contains((char)ch))
                .count() == 1);
    }

    private boolean containsOneNumber(String password){
        return(password.chars().filter(Character::isDigit).count() == 1);
    }

    private boolean containsOneCapitalizedCharacter(String password){
        return (password.chars().filter(Character::isUpperCase).count() == 1);
    }

}
