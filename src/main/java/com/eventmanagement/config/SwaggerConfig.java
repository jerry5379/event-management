package com.eventmanagement.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(title = "Event Management System",
                description = "CRUD APIS for Event Management System",
                contact = @Contact(
                        name = "Pritesh Kadam",
                        email = "kadampritesh46@gmail.com"
                ))

)
public class SwaggerConfig {
}
