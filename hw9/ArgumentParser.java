/**
 * Helper class used to perform checks on command cline
 * arguments
 *
 * @author Kyle Burke
 * @author Shreesh Tripathi
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArgumentParser {

    public static boolean isOption(String test){
        Pattern p = Pattern.compile("-\\w+");
        Matcher m = p.matcher(test);

        return (m.matches());
    }

    public static boolean isFilename(String test){
        Pattern p = Pattern.compile("\\w+.\\w+");
        Matcher m = p.matcher(test);

        return (m.matches());
    }
}
