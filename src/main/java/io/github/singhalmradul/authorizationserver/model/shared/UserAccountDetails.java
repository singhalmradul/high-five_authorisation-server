package io.github.singhalmradul.authorizationserver.model.shared;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_account_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "builder")
public class UserAccountDetails {

    @Id
    @Column(name = "user_id", updatable = false, nullable = false)
    private UUID userId;

    @Pattern(regexp = "^[a-z][a-z0-9]*$", message = "username must contain only lowercase letters and numbers, begin with a letter")
    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Email
    @Column(name = "email", unique = true, nullable = false)
    private String email;

}
