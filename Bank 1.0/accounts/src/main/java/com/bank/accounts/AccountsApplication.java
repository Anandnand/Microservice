package com.bank.accounts;

import com.bank.accounts.dto.AccountsContactInfoDto;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
/*@ComponentScans({ @ComponentScan("com.bank.accounts.controller") })
@EnableJpaRepositories("com.bank.accounts.repository")
@EntityScan("com.bank.accounts.model")*/
@EnableConfigurationProperties(value = {AccountsContactInfoDto.class})
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
		info = @Info(
				title = "Accounts microservice REST API Documentation",
				description = "bank Accounts microservice REST API Documentation",
				version = "v1",
				contact = @Contact(
						name = "Anand",
						email = "anand5173@gmail.com",
						url = "https://www.bank.com"
				),
				license = @License(
						name = "Apache 2.0",
						url = "https://www.bank.com"
				)
		),
		externalDocs = @ExternalDocumentation(
				description =  "BAnk Accounts microservice REST API Documentation",
				url = "https://www.bank.com/swagger-ui.html"
		)
)
public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

}
