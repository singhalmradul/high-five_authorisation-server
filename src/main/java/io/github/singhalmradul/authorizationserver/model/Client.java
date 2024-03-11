// package io.github.singhalmradul.authorizationserver.model;

// import java.time.Instant;

// import jakarta.persistence.Column;
// import jakarta.persistence.Entity;
// import jakarta.persistence.Id;
// import lombok.Getter;
// import lombok.NoArgsConstructor;
// import lombok.Setter;

// @Entity
// @Getter
// @Setter
// @NoArgsConstructor
// public class Client {

//     @Id
//     private String id;

//     private String clientId;

//     private Instant clientIdIssuedAt;

//     private String clientSecret;

//     private Instant clientSecretExpiresAt;

//     private String clientName;

//     @Column(length = 1000)
//     private String clientAuthenticationMethods;

//     @Column(length = 1000)
//     private String authorizationGrantTypes;

//     @Column(length = 1000)
//     private String redirectUris;

//     @Column(length = 1000)
//     private String postLogoutRedirectUris;

//     @Column(length = 1000)
//     private String scopes;

//     @Column(length = 2000)
//     private String clientSettings;

//     @Column(length = 2000)
//     private String tokenSettings;
// }