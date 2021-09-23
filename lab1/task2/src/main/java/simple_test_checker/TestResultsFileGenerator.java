package simple_test_checker;

import java.util.List;

public class TestResultsFileGenerator {
    public static String createTestResults(List<TestResult> testResults) {
        StringBuilder testResultsInStringFormat = new StringBuilder();
        for (int currIdx = 0; currIdx < testResults.size(); currIdx++) {
            testResultsInStringFormat
                    .append(currIdx).append(" ")
                    .append(testResults.get(currIdx).name()).append("\n");
        }
        return testResultsInStringFormat.toString();
    }
}
