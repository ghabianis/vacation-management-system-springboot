package com.example.restservicecors.serializers;

import java.io.IOException;

import com.example.restservicecors.models.Conge.Conge;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

// CongeSerializer.java
public class CongeSerializer extends JsonSerializer<Conge> {
    @Override
    public void serialize(Conge conge, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("id", conge.getId());
        jsonGenerator.writeStringField("description", conge.getDescription());
        jsonGenerator.writeObjectField("dateDebut", conge.getDatDebut());
        jsonGenerator.writeObjectField("dateFin", conge.getDateFin());
        jsonGenerator.writeStringField("user_id", conge.getUser().getId()); // Only include the user ID
        jsonGenerator.writeObjectField("dateRupture", conge.getDateRupture());
        jsonGenerator.writeStringField("etat", conge.getEtat());
        jsonGenerator.writeEndObject();
    }
}