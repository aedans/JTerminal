package com.aedan.jterminal.command.commandarguments;

import java.util.function.Supplier;

/**
 * Created by Aedan Smith.
 */

public interface Argument extends Supplier<Object> {
    Class<?> getArgumentType();
}
