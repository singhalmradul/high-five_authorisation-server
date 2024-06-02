package io.github.singhalmradul.authorizationserver.repositories.user;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.singhalmradul.authorizationserver.model.user.UserAuthenticationDetails;

public interface UserAuthenticationDetailsRepository extends JpaRepository<UserAuthenticationDetails, UUID> {}
