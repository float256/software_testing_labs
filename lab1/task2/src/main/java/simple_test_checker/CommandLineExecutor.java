package simple_test_checker;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class CommandLineExecutor {
    public static String runCommand(String command, Runtime runtime) throws IOException {
        InputStream commandResultInputStream = runtime.exec(command).getInputStream();
        Scanner scanner = new Scanner(commandResultInputStream).useDelimiter("\0");
        return scanner.hasNext() ? scanner.next() : "";
    }
}
