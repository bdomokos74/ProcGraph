package org.bds.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: bds
 * Date: Feb 6, 2010
 * Time: 9:14:50 PM
 */
public class TestHelper {
    public BufferedReader getResourceReader(String resource) {
        return new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(resource)));
    }

    public String resourceToString(String resource) throws IOException {
        BufferedReader reader = getResourceReader(resource);
        String line;
        StringBuilder builder = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            builder.append(line).append("\n");
        }
        return builder.toString().trim();
    }
}
