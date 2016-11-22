package com.aedan.jterminal.packages.defaultpackage.utility.parsers.reflection;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.commandarguments.Argument;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.parser.CommandParser;
import com.aedan.jterminal.parser.Parser;
import com.aedan.jterminal.utils.ClassUtils;
import javafx.util.Pair;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

/**
 * Created by Aedan Smith.
 */

public class ConstructorAccessParser implements Parser {
    @Override
    public int process(Environment environment, CommandParser commandParser, int i, ArgumentList argumentList, String s)
            throws JTerminalException {
        try {
            if (!(s.charAt(i) == 'n' && s.charAt(i + 1) == 'e' && s.charAt(i + 2) == 'w' && s.charAt(i + 3) == ' '))
                return -1;

            String name = "";
            for (i += 4; i < s.length(); i++) {
                if (s.charAt(i) == '(') {
                    i++;
                    break;
                } else {
                    name += s.charAt(i);
                }
            }

            Pair<ArgumentList, Integer> parse = commandParser.nestedParse(environment, s.substring(i), '(', ')');
            ArgumentList arguments = parse.getKey();
            i = i + parse.getValue();

            Object[] objects = new Object[arguments.size()];
            Class<?>[] classes = new Class<?>[arguments.size()];
            for (int j = 0; j < arguments.size(); j++) {
                objects[j] = arguments.get(j).value;
                classes[j] = arguments.get(j).getArgumentType();
            }

            Constructor<?> c = null;
            loop:
            for (Constructor<?> constructor : Class.forName(name).getConstructors()) {
                Class<?>[] params = constructor.getParameterTypes();
                if (params.length != classes.length || !Objects.equals(constructor.getName(), name)) {
                    continue;
                }

                for (int j = 0; j < params.length; j++) {
                    if (ClassUtils.isValidParam(params[j], classes[j])) {
                        continue loop;
                    }
                }

                c = constructor;
                break;
            }

            if (c == null)
                throw new JTerminalException("Could not find constructor with name \"" + name + "\" and args \"" + arguments + "\"", this);

            Object o = c.newInstance(objects);
            argumentList.add(new Argument(o));

            return i;
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new JTerminalException(e.getMessage(), this);
        } catch (ClassNotFoundException e) {
            throw new JTerminalException("Could not find class " + e.getMessage(), this);
        }
    }
}
