package com.spotify.heroic.metric;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spotify.heroic.HeroicPrimaryModule;
import com.spotify.heroic.common.Statistics;

public class BasicSerializationTest {
    private ObjectMapper mapper;

    @Before
    public void setup() {
        mapper = new ObjectMapper();
        mapper.registerModule(HeroicPrimaryModule.serializerModule());
    }

    @Test
    public void testPoint() throws Exception {
        final Point expected = new Point(0, 3.14);
        assertSerialization("Point.json", expected, Point.class);
    }

    @Test
    public void testMetricTypedGroup() throws Exception {
        final MetricTypedGroup expected = new MetricTypedGroup(MetricType.POINT, new ArrayList<>());
        assertSerialization("MetricTypedGroup.json", expected, MetricTypedGroup.class);
    }

    @Test
    public void testResultGroup() throws Exception {
        final List<TagValues> tags = new ArrayList<>();
        final ResultGroup expected = new ResultGroup(tags, new MetricTypedGroup(MetricType.POINT, new ArrayList<>()));
        assertSerialization("ResultGroup.json", expected, ResultGroup.class);
    }

    @Test
    public void testResultGroups() throws Exception {
        final List<ResultGroup> groups = new ArrayList<>();
        final List<RequestError> errors = new ArrayList<>();
        final ResultGroups expected = new ResultGroups(groups, errors, Statistics.EMPTY);

        assertSerialization("ResultGroups.json", expected, ResultGroups.class);
    }

    private <T> void assertSerialization(final String json, final T expected, final Class<T> type) throws IOException, JsonParseException, JsonMappingException {
        // verify that it is equal to the local file.
        try (InputStream in = openResource(json)) {
            assertEquals(expected, mapper.readValue(in, type));
        }

        // roundtrip
        final String string = mapper.writeValueAsString(expected);
        assertEquals(expected, mapper.readValue(string, type));
    }

    private InputStream openResource(String path) {
        final Class<?> cls = BasicSerializationTest.class;
        final String fullPath = cls.getPackage().getName().replace('.', '/') + "/" + path;
        return cls.getClassLoader().getResourceAsStream(fullPath);
    }
}