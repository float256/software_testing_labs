package org.simple_test_checker;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class App 
{
    public static void main( String[] args ) throws IOException {
        if (args.length == 3) {
            String pathToProgram = args[0];
            String pathToTestedFile = args[1];
            String testResultsFile = args[2];

            try(FileReader fileReader = new FileReader(pathToTestedFile)) {
                List<TestCasePair> testCasePairs = TestCasesParser.parse(fileReader);
                Runtime runtime = Runtime.getRuntime();
                List<TestResult> testResults =TestRunner.runTests(pathToProgram, testCasePairs, runtime);
                String testResultsInStringFormat = TestResultsFileGenerator.createTestResults(testResults);

                try (PrintWriter writer = new PrintWriter(testResultsFile, StandardCharsets.UTF_8)) {
                    writer.print(testResultsInStringFormat);
                } catch (FileNotFoundException exception) {
                    System.out.println("Output file can't be created");
                }
            } catch (FileNotFoundException exception) {
                System.out.println("File not found");
            } catch (SecurityException exception) {
                System.out.println("This program can't be run due to insecurity");
            }
        } else {
            System.out.println("Incorrect number of parameters");
        }
    }
}
