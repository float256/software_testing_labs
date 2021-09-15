package org.simple_test_checker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestRunner {
    public static List<TestResult> runTests(
            String pathToProgram, List<TestCasePair> testCases, Runtime runtime) throws IOException {
        List<TestResult> testingResult = new ArrayList<>();
        for (TestCasePair currentTestCase : testCases) {
            String arguments = currentTestCase.getCommandLineArguments();
            String expectedOutput = currentTestCase.getExpectedOutput().strip();
            String actualOutput = CommandLineExecutor.runCommand(
                    pathToProgram + " " + arguments, runtime).strip();
            testingResult.add(actualOutput.equals(expectedOutput) ? TestResult.Success : TestResult.Failure);
        }
        return testingResult;
    }
}
