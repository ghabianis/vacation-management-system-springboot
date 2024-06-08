package com.example.restservicecors.serializers;

import java.io.IOException;

import com.example.restservicecors.models.user.User;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

// UserSerializer.java
public class UserSerializer extends JsonSerializer<User> {
    @Override
    public void serialize(User user, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("id", user.getId());
        jsonGenerator.writeStringField("firstName", user.getFirstName());
        jsonGenerator.writeStringField("lastName", user.getLastName());
        jsonGenerator.writeStringField("username", user.getUsername());
        jsonGenerator.writeStringField("role", user.getRole());
        // Exclude password and role for security reasons
        jsonGenerator.writeObjectField("createdAt", user.getCreatedAt());
        jsonGenerator.writeObjectField("updatedAt", user.getUpdatedAt());
        jsonGenerator.writeObjectField("deletedAt", user.getDeletedAT());
        jsonGenerator.writeObjectField("dateEmbauchement", user.getDateEmbauchement());
        jsonGenerator.writeStringField("code", user.getCode());
        jsonGenerator.writeEndObject();
    }
}
