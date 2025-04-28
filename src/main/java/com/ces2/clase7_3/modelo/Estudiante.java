package com.ces2.clase7_3.modelo;

import java.time.LocalDate;

public class Estudiante {
	private final String name;
	private final String email;
	private final String phone;
	private final LocalDate dob;
	
	public Estudiante(String name, String email, String phone, LocalDate dob) {
		super();
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.dob = dob;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPhone() {
		return phone;
	}

	public LocalDate getDob() {
		return dob;
	}

	@Override
	public String toString() {
		return "Estudiante [name=" + name + ", email=" + email + ", phone=" + phone + ", dob=" + dob + "]";
	}
	
	
	
}
