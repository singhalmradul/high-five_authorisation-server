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

import io.github.singhalmradul.authorizationserver.model.user.Authority;
import io.github.singhalmradul.authorizationserver.model.user.UserAuthenticationDetails;

public class UserAuthenticationDetailsJsonDeserializer
    extends JsonDeserializer<UserAuthenticationDetails>
{

    @Override
    public UserAuthenticationDetails deserializeWithType(
        JsonParser jsonParser,
        DeserializationContext deserializationContext,
        TypeDeserializer typeDeserializer
    ) throws IOException {
        return deserializeUserAuthenticationDetails(jsonParser);
    }

    @Override
    public UserAuthenticationDetails deserialize(
        JsonParser jsonParser,
        DeserializationContext deserializationContext
    ) throws IOException {
        return deserializeUserAuthenticationDetails(jsonParser);
    }

    private UserAuthenticationDetails deserializeUserAuthenticationDetails(
        JsonParser jsonParser
    ) throws IOException {

        ObjectMapper mapper = (ObjectMapper) jsonParser.getCodec();
        JsonNode node = mapper.readTree(jsonParser);

        UUID username = UUID.fromString(node.get("username").asText());
        Collection<Authority> authorities = mapper.convertValue(
            node.get("authorities"),
            new TypeReference<Collection<Authority>>() {}
        );
        boolean accountNonExpired = node.get("accountNonExpired").asBoolean();
        boolean accountNonLocked = node.get("accountNonLocked").asBoolean();
        boolean credentialsNonExpired = node.get("credentialsNonExpired").asBoolean();
        boolean enabled = node.get("enabled").asBoolean();

        return new UserAuthenticationDetails(
            username,
            null,
            authorities,
            accountNonExpired,
            accountNonLocked,
            credentialsNonExpired,
            enabled
        );
    }
}
