package io.github.singhalmradul.authorizationserver.model;

import static jakarta.persistence.GenerationType.UUID;

import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@JsonDeserialize
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Authority implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = UUID)
    private UUID id;

    @NonNull
    @Column(unique = true, nullable = false)
    private String role;

    // ---------------------------------------------------------------------------------------------------
    @JsonIgnore
    @Override
    public String getAuthority() {
        return role;
    }

    @Override
    public String toString() {
        return role;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Authority authority) {
            return role.equals(authority.role);
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return role.hashCode();
    }
}