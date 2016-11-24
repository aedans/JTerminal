package com.aedan.jterminal.packages.defaultpackage.utility.parsers.reflection;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.commandarguments.Argument;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.parser.Parser;
import com.aedan.jterminal.parser.StringIterator;
import com.aedan.jterminal.utils.ClassUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by Aedan Smith.
 */

public class ConstructorAccessParser implements Parser {
    public ConstructorAccessParser(Environment environment) {
        environment.getEnvironmentVariables().put("CP", "java.lang;java.util");
    }

    // TODO: Variatic args
    // TODO: Templates?
    @Override
    public boolean apply(Environment environment, Parser parser, ArgumentList argumentList, StringIterator in)
            throws JTerminalException {
        try {
            if (!(in.peek() == 'n' && in.peek(1) == 'e' && in.peek(2) == 'w' && in.peek(3) == ' '))
                return false;
            in.skip(4);

            String name = "";
            while (true) {
                if (in.peek() == '(') {
                    in.next();
                    break;
                } else {
                    name += in.next();
                }
            }

            ArgumentList arguments = parser.nestedParse(environment, in, '(', ')');

            Object[] objects = new Object[arguments.size()];
            Class<?>[] classes = new Class<?>[arguments.size()];
            for (int j = 0; j < arguments.size(); j++) {
                objects[j] = arguments.get(j).value;
                classes[j] = arguments.get(j).getArgumentType();
            }

            Constructor<?> c = null;
            loop:
            for (Constructor<?> constructor : ClassUtils.fromName(name, environment).getConstructors()) {
                Class<?>[] params = constructor.getParameterTypes();
                if (params.length != classes.length) {
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

            argumentList.add(new Argument(c.newInstance(objects)));
            return true;
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new JTerminalException(e.getMessage(), this);
        } catch (ClassNotFoundException e) {
            throw new JTerminalException("Could not find class " + e.getMessage(), this);
        }
    }
}
