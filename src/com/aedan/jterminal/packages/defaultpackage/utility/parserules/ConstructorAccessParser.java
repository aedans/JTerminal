package com.aedan.jterminal.packages.defaultpackage.utility.parserules;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.commandarguments.Argument;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.parser.ParseRule;
import com.aedan.jterminal.input.parser.Parser;
import com.aedan.jterminal.utils.ClassUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;

/**
 * Created by Aedan Smith.
 */

public class ConstructorAccessParser implements ParseRule {
    @Override
    public boolean matches(String s, int i) {
        return s.charAt(i) == 'n' && s.charAt(i + 1) == 'e' && s.charAt(i + 2) == 'w' && s.charAt(i + 3) == ' ';
    }

    @Override
    public int process(Environment environment, Parser parser, int i, ArgumentList argumentList, String s)
            throws JTerminalException {
        try {
            String name = "", args = "";
            for (i += 4; i < s.length(); i++) {
                if (s.charAt(i) == '(') {
                    break;
                } else {
                    name += s.charAt(i);
                }
            }
            for (i++; i < s.length(); i++) {
                if (s.charAt(i) == ')') {
                    break;
                } else {
                    args += s.charAt(i);
                }
            }

            LinkedList<ArgumentList> mArgs = parser.parse(environment, args);
            ArrayList<Argument> arguments = new ArrayList<>();
            mArgs.forEach(arguments::addAll);

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
