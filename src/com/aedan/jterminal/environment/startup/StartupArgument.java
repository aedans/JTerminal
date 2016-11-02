package com.aedan.jterminal.environment.startup;

import acklib.utils.misc.ArgumentParser;
import com.aedan.jterminal.environment.Environment;

/**
 * Created by Aedan Smith.
 * <p>
 * Class for handling Startup Arguments with the Environment.
 */

public interface StartupArgument {

    /**
     * Handles the argument.
     *
     * @param environment The Environment invoking the function.
     * @param parser      The ArgumentParser for accessing the Arguments.
     * @throws Exception If there was an error whilst handling the argument.
     */
    void handle(Environment environment, ArgumentParser parser) throws Exception;
}
