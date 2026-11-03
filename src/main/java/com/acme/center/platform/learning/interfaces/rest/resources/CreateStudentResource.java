package com.acme.center.platform.learning.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Create student resource.
 */
@Schema(
    name = "CreateStudentRequest",
    description = "Request payload for creating a new student",
    example = "{\"firstName\": \"John\", \"lastName\": \"Doe\", \"email\": \"john.doe@example.com\", \"street\": \"123 Main St\", \"number\": \"Apt 4\", \"city\": \"Springfield\", \"postalCode\": \"12345\", \"country\": \"USA\"}"
)
public record CreateStudentResource(
    @Schema(description = "Student first name", example = "John", minLength = 1, maxLength = 50)
    String firstName,

    @Schema(description = "Student last name", example = "Doe", minLength = 1, maxLength = 50)
    String lastName,

    @Schema(description = "Student email address", example = "john.doe@example.com")
    String email,

    @Schema(description = "Street address", example = "123 Main St", minLength = 1, maxLength = 100)
    String street,

    @Schema(description = "Street number or apartment", example = "Apt 4", minLength = 1, maxLength = 20)
    String number,

    @Schema(description = "City name", example = "Springfield", minLength = 1, maxLength = 50)
    String city,

    @Schema(description = "Postal code", example = "12345", minLength = 1, maxLength = 20)
    String postalCode,

    @Schema(description = "Country name", example = "USA", minLength = 1, maxLength = 50)
    String country
) {
    /**
     * Validates the resource.
     * @throws IllegalArgumentException if any of the fields is null or blank.
     */
    public CreateStudentResource {
        if (firstName == null || firstName.isBlank()) throw new IllegalArgumentException("First name is required");
        if (lastName == null || lastName.isBlank()) throw new IllegalArgumentException("Last name is required");
        if (email == null || email.isBlank()) throw new IllegalArgumentException("Email is required");
        if (street == null || street.isBlank()) throw new IllegalArgumentException("Street is required");
        if (city == null || city.isBlank()) throw new IllegalArgumentException("City is required");
        if (postalCode == null || postalCode.isBlank()) throw new IllegalArgumentException("Postal code is required");
        if (country == null || country.isBlank()) throw new IllegalArgumentException("Country is required");
    }
}
