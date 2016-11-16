package com.aedan.jterminal.environment.startup;

import com.aedan.argparser.ArgumentParser;
import com.aedan.argparser.ParseResult;
import com.aedan.jterminal.environment.Environment;

/**
 * Created by Aedan Smith.
 * <p>
 * Class for handling Startup Arguments with the Environment.
 */

public interface StartupArgument {
    /**
     * Adds the StartupArgument to an ArgumentParser.
     *
     * @param argumentParser The ArgumentParser to add to.
     */
    void addTo(ArgumentParser argumentParser);

    /**
     * Handles the argument.
     *
     * @param environment The Environment invoking the function.
     * @param parseResult The ParseResult of the ArgumentParser.
     * @throws Exception If there was an error whilst handling the argument.
     */
    void handle(Environment environment, ParseResult parseResult) throws Exception;

    default String getId() {
        return this.getClass().getSimpleName();
    }
}
