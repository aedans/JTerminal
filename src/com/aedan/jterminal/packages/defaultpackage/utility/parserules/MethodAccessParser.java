package com.aedan.jterminal.packages.defaultpackage.utility.parserules;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.commandarguments.Argument;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.parser.ParseRule;
import com.aedan.jterminal.input.parser.Parser;
import com.aedan.jterminal.utils.ClassUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;

/**
 * Created by Aedan Smith.
 */

public class MethodAccessParser implements ParseRule {
    @Override
    public boolean matches(String s, int i) {
        return s.charAt(i) == ':' && s.charAt(i + 1) == ':';
    }

    @Override
    public int process(Environment environment, Parser parser, int i, ArgumentList argumentList, String s)
            throws JTerminalException {
        try {
            if (argumentList.getLast() == null) {
                throw new JTerminalException("Object is null", this);
            }
            String name = "", args = "";
            for (i += 2; i < s.length(); i++) {
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

            Method m = null;
            loop:
            for (Method method : argumentList.getLast().value.getClass().getMethods()) {
                Class<?>[] params = method.getParameterTypes();
                if (params.length != classes.length || !Objects.equals(method.getName(), name)) {
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
                throw new JTerminalException("Could not find method with name \"" + name + "\" and args \"" + arguments + "\"", this);

            if (mArgs.isEmpty()) {
                Object o = m.invoke(argumentList.getLast().value);
                if (o != null)
                    argumentList.setLast(new Argument(o));
                else
                    argumentList.removeLast();
            } else {
                Object o = m.invoke(argumentList.getLast().value, objects);
                if (o != null)
                    argumentList.setLast(new Argument(o));
                else
                    argumentList.removeLast();
            }
            return i;
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new JTerminalException(e.getMessage(), this);
        }
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
