package com.aedan.jterminal.input;

import java.util.LinkedList;

/**
 * Created by Aedan Smith on 10/5/2016.
 * <p>
 * Class for tokenizing Strings.
 */

public final class StringTokenizer {

    /**
     * Tokenizes a String's String literals.
     *
     * @param s The String to Tokenize.
     * @return The List of String literal values. Index 0 is the newly tokenized String.
     */
    public static LinkedList<String> tokenizeStringLiterals(String s) {
        LinkedList<String> strings = new LinkedList<>();
        strings.add(s);
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '\\') {
                i++;
                continue;
            }
            if (s.charAt(i) == '\"') {
                int j;
                for (j = i + 1; true; j++) {
                    if (s.charAt(j) == '\\') {
                        j++;
                        continue;
                    }
                    if (s.charAt(j) == '\"') {
                        break;
                    }
                }
                strings.add(s.substring(i + 1, j));
                i = j;
            }
        }
        for (int i = 1; i < strings.size(); i++) {
            strings.set(0, strings.get(0).replace("\"" + strings.get(i) + "\"", "&" + i));
            strings.set(i, strings.get(i).replaceAll("\\\\(.)", "$1"));
        }
        return strings;
    }

}
