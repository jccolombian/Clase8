package com.ces2.clase7_3.modelo;

import java.util.function.Predicate;

public class Cliente {
	

	private final String name;
	private final String phone;
	private String mensaje = "No hay mensaje";
	
	public Cliente(String nam,String pho){
		this.name = nam;
		this.phone = pho;
	}
	public void saludarCliente() {
		this.mensaje =  "Hello "+this.name+
				", gracias por registrarse telefono: "+this.phone;
	}
	public String getName() {
		return name;
	}
	public String getPhone() {
		return phone;
	}
	
	public String verMensaje() {
		return mensaje;
	}
	
	public String setMensaje(String msg) {
		return this.mensaje = msg;
	}
	
	public String llamarCliente(Predicate<String> esTelefonoValido) {
		return esTelefonoValido.test(this.phone)? 
				"Llamando a "+this.name+" al número: "+this.phone :
				"El número de " + this.name +" es errado, no se puede hacer la llamada";
	
	}
}