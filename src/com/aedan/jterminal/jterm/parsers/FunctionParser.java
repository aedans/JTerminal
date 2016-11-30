package com.aedan.jterminal.jterm.parsers;

import com.aedan.jterminal.JTerminalException;
import com.aedan.jterminal.command.commandarguments.ArgumentList;
import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.jterm.Function;
import com.aedan.jterminal.parser.Parser;
import com.aedan.jterminal.parser.StringIterator;

import java.util.HashMap;

/**
 * Created by Aedan Smith.
 */

class FunctionParser implements Parser<HashMap<String, Function>> {
    @Override
    public boolean parse(Environment environment, HashMap<String, Function> functions, StringIterator in)
            throws JTerminalException {
        String name = "";
        while (in.peek() != '('){
            name += in.next();
        }
        in.skip(1);
        String args = "";
        while (in.peek() != ')'){
            args += in.next();
        }

        in.skip(1);
        String body = "";
        int depth = 0;
        while (in.hasNext()){
            if (in.peek() == '{') {
                depth++;
                in.next();
            } else if (in.peek() == '}'){
                depth--;
                in.next();
                if (depth == 0)
                    break;
            } else {
                body += in.next();
            }
        }

        String finalName = name;
        String[] finalArgs = args.isEmpty() ? new String[0] : args.split(",");
        String finalBody = body;

        functions.put(finalName, new Function() {
            @Override
            public String getIdentifier() {
                return finalName;
            }

            @Override
            public Object apply(Object[] args) throws JTerminalException {
                if (args.length != finalArgs.length)
                    throw new JTerminalException("Incorrect arguments given to function " + getIdentifier(), this);

                HashMap<String, Object> vars = new HashMap<>();
                for (int i = 0; i < args.length; i++) {
                    vars.put(finalArgs[i].trim(), args[i]);
                }

                Object sVars = environment.getEnvironmentVariable("VARS");
                environment.setEnvironmentVariable("VARS", vars);

                StringIterator bodyIterator = new StringIterator(finalBody);
                while (bodyIterator.hasNext()){
                    ArgumentList arguments = new ArgumentList();
                    environment.getCommandHandler().getParser().parseUntil(
                            environment, bodyIterator, arguments, stringIterator -> stringIterator.peek() != '\n'
                    );
                    bodyIterator.next();
                    Object o = environment.getCommandHandler().handleInput(
                            arguments, environment.getInput(), environment.getOutput()
                    );
                    if (o != null)
                        System.out.println(o);
                }

                environment.setEnvironmentVariable("VARS", sVars);
                return environment.removeEnvironmentVariable("RETURN");
            }
        });

        return true;
    }
}
