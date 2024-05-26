package com.backend.mediConnect.utils;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import com.google.gson.*;
import java.io.*;

public class FileTypeAdapter implements JsonSerializer<File>, JsonDeserializer<File> {

    @Override
    public JsonElement serialize(File file, java.lang.reflect.Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(file.getAbsolutePath());
    }

    @Override
    public File deserialize(JsonElement jsonElement, java.lang.reflect.Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return new File(jsonElement.getAsString());
    }
}

