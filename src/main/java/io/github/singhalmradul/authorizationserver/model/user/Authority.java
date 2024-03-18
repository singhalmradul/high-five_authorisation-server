package io.github.singhalmradul.authorizationserver.model.user;

import static jakarta.persistence.GenerationType.UUID;

import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "authority")
@JsonDeserialize
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Authority implements GrantedAuthority, Comparable<Authority>{

    @Id
    @GeneratedValue(strategy = UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @NonNull
    @Column(name = "role", unique = true, nullable = false)
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

    @Override
    public int compareTo(Authority other) {
        if(other == null) {
            return 1;
        }
        return this.role.compareTo(other.role);
    }
}