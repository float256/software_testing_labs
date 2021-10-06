package lab2;

import lab2.entities.WebPageValidationResult;
import lombok.Cleanup;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class App {
    public static String HELP_MESSAGE = """
            This program checks links in web page

            Arguments:
                                
             - URL
             - File for valid links
             - File for invalid links
            """;

    public static void main(String[] args) throws IOException {
        if (isHelpCommand(args)) {
            System.out.println(HELP_MESSAGE);
        } else if (isCorrectArgsNumber(args)) {
                String url = args[0];
                @Cleanup FileWriter validLinksFileWriter = new FileWriter(args[1]);
                @Cleanup FileWriter invalidLinksFileWriter = new FileWriter(args[2]);
                WebPageValidationResult validationResult = UrlValidator.validateUrlsInPage(url);
                FileUtils.write(validationResult.getValidLinks(), validLinksFileWriter);
                FileUtils.write(validationResult.getInvalidLinks(), invalidLinksFileWriter);

        } else {
            System.out.println("Incorrect number of arguments");
        }
    }

    private static boolean isHelpCommand(String[] args) {
        return (args.length == 0) ||
                (args.length == 1) && (args[0].equals("help"));
    }

    private static boolean isCorrectArgsNumber(String[] args) {
        return args.length == 3;
    }
}
