package com.aedan.jterminal.packages.defaultpackage.utility.parsers.reflection;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.commandarguments.Argument;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.Environment;
import com.aedan.parser.ParseException;
import com.aedan.parser.Parser;
import com.aedan.jterminal.parser.StringIterator;
import com.aedan.jterminal.utils.ClassUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

/**
 * Created by Aedan Smith.
 */

public class ConstructorAccessParser implements Parser<StringIterator, ArgumentList> {
    private Environment environment;

    public ConstructorAccessParser(Environment environment) {
        this.environment = environment;
        environment.setEnvironmentVariable("CP", "java.lang;java.util");
    }

    // TODO: Templates?
    // TODO: Forward exceptions
    @Override
    public boolean parse(ArgumentList argumentList, StringIterator in)
            throws JTerminalException {
        if (!in.hasNext(4) || !Objects.equals(in.peekString(4), "new "))
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

        ArgumentList arguments = new ArgumentList();
        // TODO: Nested
        environment.getCommandHandler().getParser().parseUntil(
                in, arguments, stringIterator -> stringIterator.peek() != ')'
        );
        in.skip();

        String finalName = name;
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

                    Constructor<?> c = null;
                    loop:
                    for (Constructor<?> constructor : ClassUtils.fromName(finalName,
                            environment.getEnvironmentVariable("CP").toString()).getConstructors()) {
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
                        throw new ParseException("Could not find constructor with name \"" +
                                finalName + "\" and args \"" + arguments + "\"", ConstructorAccessParser.this);

                    return c.newInstance(objects);
                } catch (ClassNotFoundException e) {
                    throw new ParseException("Could not find class " + e.getMessage(), ConstructorAccessParser.this);
                } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
                    throw new JTerminalException(e.getMessage(), ConstructorAccessParser.this);
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
