package io.github.singhalmradul.authorizationserver.utilities;

import java.io.IOException;
import java.util.Collection;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;

import io.github.singhalmradul.authorizationserver.model.User;
import io.github.singhalmradul.authorizationserver.model.shared.UserAccountDetails;
import io.github.singhalmradul.authorizationserver.model.user.Authority;
import io.github.singhalmradul.authorizationserver.model.user.UserAuthenticationDetails;

public class UserJsonDeserializer extends JsonDeserializer<User> {

    @Override
    public User deserializeWithType(JsonParser jsonParser, DeserializationContext deserializationContext, TypeDeserializer typeDeserializer) throws IOException {
        ObjectMapper mapper = (ObjectMapper) jsonParser.getCodec();
        JsonNode node = mapper.readTree(jsonParser);

        UUID userId = UUID.fromString(node.get("id").asText());
        String username = node.get("username").asText();
        String email = node.get("email").asText();
        UserAccountDetails accountDetails = new UserAccountDetails(userId, username, email);

        String password = node.get("password").asText();
        Collection<Authority> authorities = mapper.convertValue(node.get("authorities"), new TypeReference<Collection<Authority>>(){});
        boolean accountNonExpired = node.get("accountNonExpired").asBoolean();
        boolean accountNonLocked = node.get("accountNonLocked").asBoolean();
        boolean credentialsNonExpired = node.get("credentialsNonExpired").asBoolean();
        boolean enabled = node.get("enabled").asBoolean();
        UserAuthenticationDetails authenticationDetails = new UserAuthenticationDetails(userId, password, authorities, accountNonExpired, accountNonLocked, credentialsNonExpired, enabled);

        return new User(accountDetails, authenticationDetails);
    }

    @Override
    public User deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        ObjectMapper mapper = (ObjectMapper) jsonParser.getCodec();
        JsonNode node = mapper.readTree(jsonParser);

        UUID userId = UUID.fromString(node.get("id").asText());
        String username = node.get("username").asText();
        String email = node.get("email").asText();
        UserAccountDetails accountDetails = new UserAccountDetails(userId, username, email);

        Collection<Authority> authorities = mapper.convertValue(node.get("authorities"),
                new TypeReference<Collection<Authority>>() {
                });
        boolean accountNonExpired = node.get("accountNonExpired").asBoolean();
        boolean accountNonLocked = node.get("accountNonLocked").asBoolean();
        boolean credentialsNonExpired = node.get("credentialsNonExpired").asBoolean();
        boolean enabled = node.get("enabled").asBoolean();
        UserAuthenticationDetails authenticationDetails = new UserAuthenticationDetails(userId, null, authorities,
                accountNonExpired, accountNonLocked, credentialsNonExpired, enabled);

        return new User(accountDetails, authenticationDetails);
    }
}
