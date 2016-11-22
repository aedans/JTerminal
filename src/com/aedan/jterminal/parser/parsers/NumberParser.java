package com.aedan.jterminal.parser.parsers;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.commandarguments.Argument;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.parser.CommandParser;
import com.aedan.jterminal.parser.Parser;

/**
 * Created by Aedan Smith.
 */

public class NumberParser implements Parser {
    @Override
    public int process(Environment environment, CommandParser commandParser, int i, ArgumentList argumentList, String s)
            throws JTerminalException {
        if (!((s.charAt(i) >= '0' && s.charAt(i) <= '9') || s.charAt(i) == '-'))
            return -1;

        boolean decimal = false;
        String number = "";
        for (; i < s.length(); i++) {
            if ((s.charAt(i) >= '0' && s.charAt(i) <= '9') || s.charAt(i) == '-') {
                number += s.charAt(i);
            } else if (s.charAt(i) == '.') {
                number += '.';
                decimal = true;
            } else {
                break;
            }
        }
        try {
            if (decimal) {
                if (number.length() < 39) {
                    argumentList.add(new Argument(Float.parseFloat(number), Float.class));
                } else if (number.length() < 310) {
                    argumentList.add(new Argument(Double.parseDouble(number), Double.class));
                } else {
                    argumentList.add(new Argument(number));
                }
            } else {
                if (number.length() < 11) {
                    argumentList.add(new Argument(Integer.parseInt(number), Integer.class));
                } else if (number.length() < 20) {
                    argumentList.add(new Argument(Long.parseLong(number), Long.class));
                } else {
                    argumentList.add(new Argument(number));
                }
            }
            return i;
        } catch (Exception e) {
            return -1;
        }
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
