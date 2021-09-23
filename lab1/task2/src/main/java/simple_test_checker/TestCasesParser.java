package simple_test_checker;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TestCasesParser {
    public static List<TestCasePair> parse(FileReader fileReader) throws IOException {
        BufferedReader reader = new BufferedReader(fileReader);
        String currentLine = reader.readLine();
        List<TestCasePair> testCasePairs = new ArrayList<>();
        while (currentLine != null) {
            Scanner scanner = new Scanner(currentLine);
            scanner.useDelimiter(",");

            String commandLineArguments = scanner.next();
            String expectedOutput = scanner.next();

            if (scanner.hasNext()) {
                throw new TestCasesParserException("Incorrect file format");
            }

            TestCasePair testCasePair = new TestCasePair(commandLineArguments, expectedOutput);
            testCasePairs.add(testCasePair);
            currentLine = reader.readLine();
        }
        return testCasePairs;
    }
}
