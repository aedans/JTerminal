package com.aedan.jterminal.input;

import java.util.Scanner;

/**
 * Created by Aedan Smith on 8/14/2016.
 *
 * The default CommandInput for the JTerminal.
 */

public class SystemInput implements CommandInput {

    /**
     * The Scanner for the System Input.
     */
    private Scanner scanner = new Scanner(System.in);

    /**
     * @return String: The next line of the System input.
     */
    @Override
    public String nextLine() {
        return scanner.nextLine();
    }

}
