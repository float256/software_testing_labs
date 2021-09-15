package org.simple_test_checker;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException {
        if (args.length == 2) {
            String pathToProgram = args[0];
            String pathToTestedFile = args[1];

            try(FileReader fileReader = new FileReader(pathToTestedFile)) {
                List<TestCasePair> testCasePairs = TestCasesParser.parse(fileReader);
                Runtime runtime = Runtime.getRuntime();
                List<TestResult> testResults =TestRunner.runTests(pathToProgram, testCasePairs, runtime);
                String testResultsInStringFormat = TestResultsFileGenerator.createTestResults(testResults);

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
