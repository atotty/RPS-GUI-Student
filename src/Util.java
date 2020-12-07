/*
 * This class contains utility methods for the application.
 *
 * Note: You do not need to modify anything in this file, but you may
 * find it useful to read this source code to better understand how the
 * program works. Additionally, you may add your own utility methods
 * here for use elsewhere in the program.
 */
public class Util {

    /**
     * Prints log message to console if the message level is at least
     * the provided log level.
     *
     * You may modify this method if you choose, but it is not necessary.
     * The program expects this method to exist with its current header.
     * @param logLevel
     * @param messageLevel
     * @param message
     */
    public static void log(int logLevel, int messageLevel, String message) {
        if (messageLevel <= logLevel)
            System.out.println(message);
    }
}
