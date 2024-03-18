package io.github.singhalmradul.authorizationserver.utilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.collection.spi.PersistentBag;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;

public class PersistentBagConverter implements Converter<List<?>,List<?>>{

    @Override
    public JavaType getInputType(TypeFactory typeFactory) {
        return typeFactory.constructCollectionType(List.class, Object.class);
    }

    @Override
    public JavaType getOutputType(TypeFactory typeFactory) {
        return typeFactory.constructCollectionType(List.class, Object.class);
    }
    @Override
    public List<?> convert(List<?> value) {
        if (value == null) {
            return Collections.emptyList();
        }
        if (value instanceof PersistentBag) {
            return new ArrayList<>(value);
        }
        return value;
    }

}
