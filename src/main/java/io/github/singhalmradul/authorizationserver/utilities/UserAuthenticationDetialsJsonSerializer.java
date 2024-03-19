package io.github.singhalmradul.authorizationserver.utilities;

import static com.fasterxml.jackson.core.JsonToken.START_OBJECT;

import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

import io.github.singhalmradul.authorizationserver.model.user.UserAuthenticationDetails;

public class UserAuthenticationDetialsJsonSerializer extends JsonSerializer<UserAuthenticationDetails> {

    @Override
    public void serializeWithType(
        UserAuthenticationDetails authenticationDetails,
        JsonGenerator jsonGenerator,
        SerializerProvider serializerProvider,
        TypeSerializer typeSerializer
    ) throws IOException {

        WritableTypeId typeIdDef = typeSerializer.typeId(authenticationDetails, START_OBJECT);
        typeSerializer.writeTypePrefix(jsonGenerator, typeIdDef);

        serializeUserAuthenticationDetails(authenticationDetails, jsonGenerator);

        typeSerializer.writeTypeSuffix(jsonGenerator, typeIdDef);
    }

    @Override
    public void serialize(
        UserAuthenticationDetails authenticationDetails,
        JsonGenerator jsonGenerator,
        SerializerProvider serializerProvider
    ) throws IOException {

        jsonGenerator.writeStartObject();

        serializeUserAuthenticationDetails(authenticationDetails, jsonGenerator);

        jsonGenerator.writeEndObject();
    }

    private void serializeUserAuthenticationDetails(
        UserAuthenticationDetails authenticationDetails,
        JsonGenerator jsonGenerator
    ) throws IOException {

        jsonGenerator.writeStringField("username", authenticationDetails.getUsername());
        jsonGenerator.writeObjectField(
            "authorities",
            authenticationDetails.getAuthorities() instanceof ArrayList authorities
                ? authorities
                : new ArrayList<>(authenticationDetails.getAuthorities())
        );
        jsonGenerator.writeBooleanField(
            "accountNonExpired",
            authenticationDetails.isAccountNonExpired()
        );
        jsonGenerator.writeBooleanField("accountNonLocked", authenticationDetails.isAccountNonLocked());
        jsonGenerator.writeBooleanField(
            "credentialsNonExpired",
            authenticationDetails.isCredentialsNonExpired()
        );
        jsonGenerator.writeBooleanField("enabled", authenticationDetails.isEnabled());
    }
}
