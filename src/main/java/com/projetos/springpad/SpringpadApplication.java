/**
 * com.projetos.springpad.SpringpadApplication
 * Classe raiz do aplicativo, contendo o método `main()`.
 */
package com.projetos.springpad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringpadApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringpadApplication.class, args);
	}

}
