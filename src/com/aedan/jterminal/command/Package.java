package com.aedan.jterminal.command;

import com.aedan.jterminal.Environment;

/**
 * Created by Aedan Smith on 8/15/2016.
 * <p>
 * Interface for adding created packages for the Environment.
 */

public interface Package {
    /**
     * Adds the package to the Environment
     *
     * @param environment The Environment to addTo to.
     */
    void addTo(Environment environment);

    default String getId() {
        return this.getClass().getSimpleName();
    }
}
