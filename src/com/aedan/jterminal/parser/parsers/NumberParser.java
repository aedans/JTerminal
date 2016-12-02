package com.aedan.jterminal.parser.parsers;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.commandarguments.ConstantArgument;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.parser.Parser;
import com.aedan.jterminal.parser.StringIterator;

/**
 * Created by Aedan Smith.
 */

public class NumberParser implements Parser<StringIterator, ArgumentList> {
    @Override
    public boolean parse(ArgumentList argumentList, StringIterator in)
            throws JTerminalException {
        if (!((in.peek() >= '0' && in.peek() <= '9') || in.peek() == '-'))
            return false;

        boolean decimal = false;
        String number = "";
        while (in.hasNext()) {
            if ((in.peek() >= '0' && in.peek() <= '9') || in.peek() == '-') {
                number += in.next();
            } else if (in.peek() == '.') {
                number += in.next();
                decimal = true;
            } else {
                break;
            }
        }
        try {
            if (decimal) {
                if (number.length() < 39) {
                    argumentList.add(new ConstantArgument(Float.parseFloat(number), Float.class));
                } else if (number.length() < 310) {
                    argumentList.add(new ConstantArgument(Double.parseDouble(number), Double.class));
                } else {
                    argumentList.add(new ConstantArgument(number));
                }
            } else {
                if (number.length() < 11) {
                    argumentList.add(new ConstantArgument(Integer.parseInt(number), Integer.class));
                } else if (number.length() < 20) {
                    argumentList.add(new ConstantArgument(Long.parseLong(number), Long.class));
                } else {
                    argumentList.add(new ConstantArgument(number));
                }
            }
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
