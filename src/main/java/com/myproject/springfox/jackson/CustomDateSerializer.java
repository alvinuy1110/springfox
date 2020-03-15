package com.myproject.springfox.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomDateSerializer extends StdSerializer<Date> {
    private static final long serialVersionUID = 1L;

    public CustomDateSerializer() {
        this(Date.class);
    }
    public CustomDateSerializer(Class<Date> t) {
        super(t);
    }
    @Override
    public void serialize(Date value,
                          JsonGenerator generator, SerializerProvider arg2) throws IOException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        generator.writeString(formatter.format(value));
    }
}