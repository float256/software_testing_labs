package lab2;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class FileUtils {
    public static void write(Map<String, String> map, FileWriter file) throws IOException {
        StringBuilder resultingString = new StringBuilder();
        map.forEach((key, value) ->
                resultingString.append(
                        String.format("%s: %s\n", key, value)
                )
        );
        file.write(resultingString.toString());
    }
}
