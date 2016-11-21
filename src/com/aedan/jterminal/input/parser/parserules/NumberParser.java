package com.aedan.jterminal.input.parser.parserules;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.commandarguments.Argument;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.parser.ParseRule;
import com.aedan.jterminal.input.parser.Parser;

/**
 * Created by Aedan Smith.
 */

public class NumberParser implements ParseRule {
    @Override
    public boolean matches(String s, int i) {
        return (s.charAt(i) >= '0' && s.charAt(i) <= '9')
                || (s.charAt(i) == '-' && (s.charAt(i + 1) >= '0' && s.charAt(i + 1) <= '9'));
    }

    @Override
    public int process(Environment environment, Parser parser, int i, ArgumentList argumentList, String s)
            throws JTerminalException {
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
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
