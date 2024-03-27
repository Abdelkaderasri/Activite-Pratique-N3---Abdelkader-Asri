package org.enset.spring_mvc_thymeleaf;

import org.enset.spring_mvc_thymeleaf.entities.Patient;
import org.enset.spring_mvc_thymeleaf.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@SpringBootApplication
public class ActivitePratique3Application implements CommandLineRunner {
	@Autowired
	private PatientRepository patientRepository;

	public static void main(String[] args) {
		SpringApplication.run(ActivitePratique3Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//		patientRepository.save(new Patient(null,"Abdelkader",new Date(),false,26));
		//		patientRepository.save(new Patient(null,"Hicham",new Date(),true,33));
		//		patientRepository.save(new Patient(null,"Ahmed",new Date(),false,43));
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
