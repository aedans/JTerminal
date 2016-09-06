package com.aedan.jterminal.utils;

import java.util.regex.Pattern;

/**
 * Created by Aedan Smith on 9/2/2016.
 *
 * Class containing Regexes to determine what a String is.
 */

public final class Patterns {

    public static Pattern absoluteDirectoryPattern = Pattern.compile("\\w:.+|/.+|\\\\.+");
    public static Pattern stringLiteralPattern = Pattern.compile("\"([^\"]*)\"");
    public static Pattern embeddedCommandPattern = Pattern.compile("\\{([^{]*\\{.+\\}[^}]*|[^}]+)\\}");

    public static Pattern bytePattern = Pattern.compile("[-+0123456789]{1,3}");
    public static Pattern shortPattern = Pattern.compile("[-+0123456789]{1,5}");
    public static Pattern integerPattern = Pattern.compile("[-+0123456789]{1,10}");
    public static Pattern longPattern = Pattern.compile("[-+0123456789]{1,19}");
    public static Pattern floatPattern = Pattern.compile("[-+0123456789.]{1,40}");
    public static Pattern doublePattern = Pattern.compile("[-+0123456789.]{1,310}");

}
