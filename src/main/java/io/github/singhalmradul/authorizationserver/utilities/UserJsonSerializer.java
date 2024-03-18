package io.github.singhalmradul.authorizationserver.utilities;

import static com.fasterxml.jackson.core.JsonToken.START_OBJECT;

import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

import io.github.singhalmradul.authorizationserver.model.User;

public class UserJsonSerializer extends JsonSerializer<User> {

    @Override
    public void serializeWithType(User user, JsonGenerator jsonGenerator, SerializerProvider serializerProvider, TypeSerializer typeSerializer) throws IOException {

        WritableTypeId typeIdDef = typeSerializer.typeId(user, START_OBJECT);
        typeSerializer.writeTypePrefix(jsonGenerator, typeIdDef);

        jsonGenerator.writeStringField("id", user.getId().toString());
        jsonGenerator.writeStringField("username", user.getUsername());
        jsonGenerator.writeStringField("email", user.getEmail());

        jsonGenerator.writeStringField("password", user.getPassword());
        jsonGenerator.writeObjectField("authorities", new ArrayList<>(user.getAuthorities()));
        jsonGenerator.writeBooleanField("accountNonExpired", user.isAccountNonExpired());
        jsonGenerator.writeBooleanField("accountNonLocked", user.isAccountNonLocked());
        jsonGenerator.writeBooleanField("credentialsNonExpired", user.isCredentialsNonExpired());
        jsonGenerator.writeBooleanField("enabled", user.isEnabled());

        typeSerializer.writeTypeSuffix(jsonGenerator, typeIdDef);
        }

    @Override
    public void serialize(User user, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException {
        jsonGenerator.writeStartObject();

        jsonGenerator.writeStringField("userId", user.getId().toString());
        jsonGenerator.writeStringField("username", user.getUsername());
        jsonGenerator.writeStringField("email", user.getEmail());

        jsonGenerator.writeObjectField("authorities", user.getAuthorities());
        jsonGenerator.writeBooleanField("accountNonExpired", user.isAccountNonExpired());
        jsonGenerator.writeBooleanField("accountNonLocked", user.isAccountNonLocked());
        jsonGenerator.writeBooleanField("credentialsNonExpired", user.isCredentialsNonExpired());
        jsonGenerator.writeBooleanField("enabled", user.isEnabled());

        jsonGenerator.writeEndObject();
    }
}
