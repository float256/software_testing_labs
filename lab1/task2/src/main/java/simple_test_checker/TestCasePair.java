package simple_test_checker;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TestCasePair {
    private String commandLineArguments;
    private String expectedOutput;
}
