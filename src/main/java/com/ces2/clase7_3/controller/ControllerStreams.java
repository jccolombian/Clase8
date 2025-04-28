package com.ces2.clase7_3.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ces2.clase7_3.modelo.Cliente;
import com.ces2.clase7_3.modelo.Estudiante;
import com.ces2.clase7_3.modelo.EstudianteValidatorService;
import com.ces2.clase7_3.modelo.ValidationResult;
import com.ces2.clase7_3.modelo.Ver;
import com.ces2.clase7_3.modelo.EstudianteRegistrationValidator;


@Controller
public class ControllerStreams {
	
	/*
	 * Function : apply()
	 * Bifunction: apply()
	 * Consumer: accept()
	 * BiConsumer: accept()
	 * Predicate: test()
	 * Supplier: get()
	 * */
	
	List<String> resultado = new ArrayList<>();
	
	@GetMapping("/combinator")
	public String combinator(Model model) {
		
		Estudiante estudiante = new Estudiante(
									"Alice",
									"alice@gmail.com",
									"+0898787879878",
									LocalDate.of(2000,1,1)
								);
		
		Boolean valido = EstudianteValidatorService.isValid(estudiante);
		
		resultado.add(estudiante.toString());
		
		resultado.add("isValid(estudiante): "+valido);
		
		Estudiante estudiante2 = new Estudiante(
				"Alice",
				"alicegmail.com",
				"0898787879878",
				LocalDate.of(2000,1,1)
			);
		
		Boolean valido2 = EstudianteValidatorService.isValid(estudiante2);
		
		resultado.add(estudiante2.toString());
		
		resultado.add("No sabemos que lo hace falso:");
		
		resultado.add("isValid(estudiante2): "+valido2);
		
		ValidationResult valido3 = EstudianteRegistrationValidator.isEmailValid()
				.and(EstudianteRegistrationValidator.isPhoneValid())
				.and(EstudianteRegistrationValidator.isAnAdult()).apply(estudiante2);
		
		resultado.add("Con EstudianteRegistrationValidator y con estudiante2 invalido:");
		resultado.add("EstudianteRegistrationValidator apply(estudiante2): "+valido3);
		
		
		
//		if(valido3 != ValidationResult.SUCCESS) {
//			throw new IllegalStateException(valido3.name());
//		}
		
		
			
		resultado.add("Probarlo en este caso:");
		
		Estudiante estudiante3 = new Estudiante(
				"Alice",
				"alicegmail.com",
				"0898787879878",
				LocalDate.of(2000,1,1)
			);
		
		ValidationResult valido4 = EstudianteRegistrationValidator.isEmailValid()
				.and(EstudianteRegistrationValidator.isPhoneValid())
				.and(EstudianteRegistrationValidator.isAnAdult()).apply(estudiante3);
		
		resultado.add("Con EstudianteRegistrationValidator y con estudiante3 invalido:");
		resultado.add("EstudianteRegistrationValidator apply(estudiante3: "+valido4);
		
		resultado.add("Y probarlo en este otro caso: ");
			Estudiante estudiante4 = new Estudiante(
									"Alice",
									"alice@gmail.com",
									"+0898787879878",
									LocalDate.of(2014,1,1)
								);
			
		ValidationResult valido5 = EstudianteRegistrationValidator.isEmailValid()
				.and(EstudianteRegistrationValidator.isPhoneValid())
				.and(EstudianteRegistrationValidator.isAnAdult()).apply(estudiante4);
		
		resultado.add("Con EstudianteRegistrationValidator y con estudiante4 valido:");
		resultado.add("EstudianteRegistrationValidator apply(estudiante4: "+valido5);	
	
		
		model.addAttribute("resultado", resultado );
		
		return "resultado";
	}
	
	@GetMapping("/callbacks")
	public String callbacks(Model model) {
		
		resultado.add("Ejemplo 1:");
		
		resultado.add("hello(\"John\",\"Montana\",null)"+hello("John","Montana",null));

		resultado.add("Ejemplo 2:");
		
		hello("John", null, value -> {
			resultado.add("no apellido proporcionado para " + value);
		});
		
		resultado.add("Ejemplo usando Runnable:");
		
		hello2("John", null, () -> {
			resultado.add("no apellido proporcionado");
		});

		
		model.addAttribute("resultado", resultado );
		return "resultado";
	}
	
	static String hello(String nombre, String apellido, Consumer<String> callback){
		
		String salida = "";
		
		salida += nombre;
		
		if(apellido != null){
			
			salida += " "+apellido;
		} else {
			
			 callback.accept(nombre);
		}
		
		return salida;
		
	}
	
	static String hello2(String nombre, String apellido, Runnable callback){
		
		String salida = "";
		
		salida += " "+nombre;
		
		if(apellido != null){
			salida += " "+apellido;
		} else {
			 callback.run();
		}
		return salida;
	}

	@GetMapping("/pasarfunciones")
	public String pasarfunciones(Model model) {
		
		
		resultado.add("Con un telefono correcto:");
		
		Cliente sara = new Cliente("Sara","3003475643");
		
		resultado.add(sara.llamarCliente(esTelefonoValidoPredicate));
		
		
		
		
		resultado.add("Con un telefono incorrecto:");
		
		Cliente samuel = new Cliente("Samuel","3063475643");
		
		resultado.add(samuel.llamarCliente(esTelefonoValidoPredicate));
		
		
		model.addAttribute("resultado", resultado );
		return "resultado";
	}
	
	static Predicate<String> esTelefonoValidoPredicate = n -> 
	{	String[] tresDigitos = {"300","301","302","303","304","305",
			"310","311","312","313","314","320","321","322","323"};
	
		return Arrays.asList(tresDigitos).contains(n.substring(0, 3)) &&
			n.length() == 10;
	};
	
	Ver<Cliente,Boolean,Boolean> verCliente = (c,verNom,verTel) -> {
		
		return "Cliente "+(verNom? c.getName() : " no se muestra nombre. ") +
				" con telefono: "+(verTel? c.getPhone() : " no se muestra telefono");
				
	};
	
	@GetMapping("/definidaPorUsuario")
	public String definidaPorUsuario(Model model) {
		
		
		resultado.add("");
		
		Cliente sara = new Cliente("Sara","3003475643");
		
		
		resultado.add("verCliente.ver(sara, true, true): "+verCliente.ver(sara, true, true));
		resultado.add("verCliente.ver(sara, true, false): "+verCliente.ver(sara, true, false));
		resultado.add("verCliente.ver(sara, false, true): "+verCliente.ver(sara, false, true));
		resultado.add("verCliente.ver(sara, false, false): "+verCliente.ver(sara, false, false));
		
		model.addAttribute("resultado", resultado );
		return "resultado";
	}
	
	
//	@GetMapping("/")
//	public String (Model model) {
//		resultado.add("");
//		
//		
//		resultado.add(": "+);
//		
//		model.addAttribute("resultado", resultado );
//		return "resultado";
//	}
}
