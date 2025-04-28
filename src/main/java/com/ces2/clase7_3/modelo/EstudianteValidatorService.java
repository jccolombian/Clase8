package com.ces2.clase7_3.modelo;

import java.time.LocalDate;
import java.time.Period;


public class EstudianteValidatorService {
	
	private static boolean isEmailValid(String email ) {
		return email.contains("@");
	}
	
	private static boolean isPhoneNumberValid(String phoneNumber) {
		return phoneNumber.startsWith("+0");
	}
	
	private static boolean isAdult(LocalDate dob ) {
		return Period.between(dob,LocalDate.now()).getYears() > 18;
	}
	
	public static boolean isValid(Estudiante estudiante) {
		return isEmailValid(estudiante.getEmail()) &&
				isPhoneNumberValid(estudiante.getPhone()) &&
				isAdult(estudiante.getDob());
	}
}
