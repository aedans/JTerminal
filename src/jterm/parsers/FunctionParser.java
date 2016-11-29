package jterm.parsers;

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

public class FunctionParser extends Parser {
    private HashMap<String, Function> functions;

    public FunctionParser(HashMap<String, Function> functions) {
        this.functions = functions;
    }

    @Override
    protected boolean parse(Environment environment, Parser parser, ArgumentList argumentList, StringIterator in)
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
        String[] finalArgs = args.split(",");
        String[] finalStatements = body.split("\n");

        functions.put(finalName, new Function() {
            @Override
            public String getIdentifier() {
                return finalName;
            }

            @Override
            public Object apply(Object[] args) throws JTerminalException {
                if (args.length != finalArgs.length)
                    throw new JTerminalException("Incorrect arguments given", this);

                HashMap<String, Object> vars = new HashMap<>();
                for (int i = 0; i < args.length; i++) {
                    vars.put(finalArgs[i].trim(), args[i]);
                }

                Object sVars = environment.getEnvironmentVariable("VARS");
                environment.setEnvironmentVariable("VARS", vars);

                for (String s : finalStatements) {
                    Object o = environment.getCommandHandler().handleInput(
                            s,
                            environment.getInput(),
                            environment.getOutput()
                    );
                    if (o != null)
                        environment.getOutput().println(o);
                }

                environment.setEnvironmentVariable("VARS", sVars);
                return null;
            }
        });

        return true;
    }
}
