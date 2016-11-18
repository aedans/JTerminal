package com.aedan.jterminal.input.parser;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.commandarguments.Argument;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.environment.Environment;

/**
 * Created by Aedan Smith.
 */

public class NumberParser implements ParseRule {
    @Override
    public boolean matches(String s, int i) {
        return Character.isDigit(s.charAt(i));
    }

    @Override
    public int process(Environment environment, Parser parser, int i, ArgumentList argumentList, String s)
            throws JTerminalException {
        boolean decimal = false;
        String number = "";
        for (; i < s.length(); i++) {
            if (Character.isDigit(s.charAt(i))) {
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
