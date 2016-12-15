package com.aedan.jterminal.packages.defaultpackage.utility.parsers.reflection;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.commandarguments.Argument;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.Environment;
import com.aedan.parser.ParseException;
import com.aedan.parser.Parser;
import com.aedan.jterminal.parser.StringIterator;
import com.aedan.jterminal.utils.ClassUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * Created by Aedan Smith.
 */

public class MethodAccessParser implements Parser<StringIterator, ArgumentList> {
    private Environment environment;

    public MethodAccessParser(Environment environment) {
        this.environment = environment;
    }

    // TODO: Variatic args
    // TODO: Forward exceptions
    @Override
    public boolean parse(ArgumentList argumentList, StringIterator in)
            throws JTerminalException {
        if (!(in.peek() == ':' && in.peek(1) == ':'))
            return false;

        if (argumentList.getLast() == null) {
            throw new ParseException("Object is null", this);
        }

        String name = "";
        in.skip(2);
        while (true) {
            if (in.peek() == '(') {
                in.next();
                break;
            } else {
                name += in.next();
            }
        }

        ArgumentList arguments = new ArgumentList();
        // TODO: Nested
        environment.getCommandHandler().getParser().parseUntil(
                in, arguments, stringIterator -> stringIterator.peek() != ')'
        );
        in.skip();

        String finalName = name;
        Argument argument = argumentList.removeLast();
        argumentList.add(new Argument() {
            @Override
            public Object get() {
                try {
                    Object[] objects = new Object[arguments.size()];
                    Class<?>[] classes = new Class<?>[arguments.size()];
                    for (int j = 0; j < arguments.size(); j++) {
                        objects[j] = arguments.get(j).get();
                        classes[j] = arguments.get(j).getArgumentType();
                    }

                    Method m = null;
                    loop:
                    for (Method method : argument.get().getClass().getMethods()) {
                        Class<?>[] params = method.getParameterTypes();
                        if (params.length != classes.length || !Objects.equals(method.getName(), finalName)) {
                            continue;
                        }

                        for (int j = 0; j < params.length; j++) {
                            if (ClassUtils.isValidParam(params[j], classes[j])) {
                                continue loop;
                            }
                        }

                        m = method;
                        break;
                    }

                    if (m == null)
                        throw new ParseException("Could not find method with name \"" +
                                finalName + "\" and args \"" + arguments + "\"", MethodAccessParser.this);

                    if (arguments.isEmpty()) {
                        return m.invoke(argument.get());
                    } else {
                        return m.invoke(argument.get(), objects);
                    }
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new ParseException(e.getMessage(), MethodAccessParser.this);
                }
            }

            @Override
            public Class<?> getArgumentType() {
                return get().getClass();
            }

            @Override
            public String toString() {
                return get().toString();
            }
        });
        return true;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
