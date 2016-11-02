package com.aedan.jterminal;

import com.aedan.jterminal.environment.Environment;
import com.aedan.jterminal.input.ScannerInput;
import com.aedan.jterminal.output.CommandOutput;

import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Aedan Smith.
 * <p>
 * Class for creating a JTerminal server.
 */

public class JServerTerminal extends JTerminal {

    /**
     * The Socket for the JServerTerminal.
     */
    private Socket socket;

    /**
     * Creates a JServerTerminal from a ServerSocket.
     *
     * @param socket The ServerSocket to create the JServerTerminal from.
     * @throws Exception If there was an error connecting to the socket.
     */
    public JServerTerminal(ServerSocket socket) throws Exception {
        this(socket.accept(), null);
    }

    /**
     * Creates a JServerTerminal from a ServerSocket.
     *
     * @param socket The ServerSocket to create the JServerTerminal from.
     * @param environment The Environment for the JServerTerminal.
     * @throws Exception If there was an error connecting to the socket.
     */
    public JServerTerminal(ServerSocket socket, Environment environment) throws Exception {
        this(socket.accept(), environment);
    }

    /**
     * Creates a JServerTerminal from a Socket.
     *
     * @param socket The Socket to create the JServerTerminal from.
     * @throws Exception If there was an error connecting to the socket.
     */
    public JServerTerminal(Socket socket) throws Exception {
        this(socket, null);
    }

    /**
     * Creates a JServerTerminal from a Socket.
     *
     * @param socket The Socket to create the JServerTerminal from.
     * @param environment The Environment for the JServerTerminal.
     * @throws Exception If there was an error connecting to the socket.
     */
    public JServerTerminal(Socket socket, Environment environment) throws Exception {
        super(
                "",
                new ScannerInput(new Scanner(socket.getInputStream())),
                new CommandOutput(new PrintStream(socket.getOutputStream())),
                environment
        );
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }
}