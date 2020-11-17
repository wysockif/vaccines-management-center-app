package pl.wysockif.optimizer.io;

import static java.lang.System.exit;

public class Errors {
    public static final int INPUT_FILE_NOT_FOUND = -1;
    public static final int INCORRECT_HEADLINE = -2;
    public static final int INCORRECT_FORMAT = -3;
    public static final int INSUFFICIENT_DATA = -4;

    public static final int OUTPUT_FILE_NOT_FOUNT = -11;

    public static void handleTheError(int codeError, String message) {
        System.err.println("Błąd " + codeError + " " + message);
        exit(codeError);
    }

}
