package pl.wysockif.optimizer.io;

import static java.lang.System.exit;

public class ErrorsHandler {
    public static final int INPUT_FILE_NOT_FOUND = -100;
    public static final int INPUT_FILE_INCORRECT_HEADLINE = -101;
    public static final int INPUT_FILE_INCORRECT_FORMAT = -102;
    public static final int INPUT_FILE_SUFFICIENT_DATA = -103;

    public static final int OUTPUT_FILE_NOT_FOUND = -200;
    public static final int OUTPUT_FILE_EXCEED_LIMIT = -201;

    public static final int INCORRECT_CALL = -300;

    public static final int INTERNAL_ERROR = -400;

    public static void handleTheError(int codeError, String message) {
        System.err.println("| Błąd " + codeError + " | " + message + "!");
        exit(codeError);
    }

    public static void handleTheError(int codeError, String message, String tip) {
        System.err.println("| Błąd " + codeError + " | " + message + "!");
        System.out.println("Wskazówka: " + tip);
        exit(codeError);
    }
}
