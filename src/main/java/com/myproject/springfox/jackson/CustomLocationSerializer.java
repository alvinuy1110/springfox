package com.myproject.springfox.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.myproject.springfox.domain.Location;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class CustomLocationSerializer extends StdSerializer<Location> {
    private static final long serialVersionUID = 1L;

    public CustomLocationSerializer() {
        this(Location.class);
    }


    public CustomLocationSerializer(Class<Location> t) {
        super(t);
    }

    @Override
    public void serialize(Location value,
                          JsonGenerator generator, SerializerProvider arg2) throws IOException {

        generator.writeStartObject();
        generator.writeStringField("floor-field", value.getFloor());
        generator.writeStringField("room-number", value.getRoom());
        generator.writeEndObject();

    }
}