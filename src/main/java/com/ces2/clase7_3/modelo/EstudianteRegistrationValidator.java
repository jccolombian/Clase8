package com.ces2.clase7_3.modelo;

import java.time.LocalDate;
import java.time.Period;
import java.util.function.Function;

public interface EstudianteRegistrationValidator  extends Function<Estudiante, ValidationResult>{
	
	static EstudianteRegistrationValidator isEmailValid() {
		return estudiante -> estudiante.getEmail().contains("@") ?
				ValidationResult.SUCCESS : ValidationResult.EMAIL_NOT_VALID;
	}
	
	static EstudianteRegistrationValidator isPhoneValid() {
		return estudiante -> estudiante.getPhone().startsWith("+0") ?
				ValidationResult.SUCCESS : ValidationResult.PHONE_NOT_VALID;
	}
	
	static EstudianteRegistrationValidator isAnAdult() {
		return estudiante -> 
		Period.between(estudiante.getDob(), LocalDate.now()).getYears() > 18 ?
				ValidationResult.SUCCESS : ValidationResult.IS_NOT_AN_ADULT;
	}
	
	default EstudianteRegistrationValidator and (EstudianteRegistrationValidator other) {
		return estudiante -> {
		ValidationResult result = this.apply(estudiante);
		return result.equals(ValidationResult.SUCCESS) ? other.apply(estudiante) : result;
		};
	}
}
	

