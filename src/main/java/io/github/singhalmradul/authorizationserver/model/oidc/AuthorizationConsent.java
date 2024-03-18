package io.github.singhalmradul.authorizationserver.model.oidc;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@IdClass(AuthorizationConsent.AuthorizationConsentId.class)
@Table(name = "oidc_authorization_consent")
@Data
public class AuthorizationConsent {

    @Id
    @Column(name = "registered_client_id", updatable = false, nullable = false)
    private String registeredClientId;

    @Id
    @Column(name = "principal_name", updatable = false, nullable = false)
    private String principalName;

    @Column(name = "authorities", length = 1000, nullable = false)
    private String authorities;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class AuthorizationConsentId implements Serializable {

        private String registeredClientId;
        private String principalName;

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            AuthorizationConsentId that = (AuthorizationConsentId) o;
            return registeredClientId.equals(that.registeredClientId) && principalName.equals(that.principalName);
        }

        @Override
        public int hashCode() {
            return Objects.hash(registeredClientId, principalName);
        }
    }
}
