package com.aedan.jterminal;

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
     * Default JServerTerminal constructor.
     *
     * @throws Exception If there was an error hosting the JServerTerminal.
     */
    public JServerTerminal() throws Exception {
        this(0);
    }

    /**
     * Creates a JServerTerminal form a ServerSocket.
     *
     * @param port The port to host the JServerTerminal on.
     * @throws Exception If there was an error hosting the JServerTerminal
     */
    public JServerTerminal(int port) throws Exception {
        this(new ServerSocket(port));
    }

    /**
     * Creates a JServerTerminal from a ServerSocket.
     *
     * @param socket The ServerSocket to create the JServerTerminal from.
     * @throws Exception If there was an error connecting to the socket.
     */
    public JServerTerminal(ServerSocket socket) throws Exception {
        this(socket.accept());
    }

    /**
     * Creates a JServerTerminal from a Socket.
     *
     * @param socket The Socket to create the JServerTerminal from.
     * @throws Exception If there was an error connecting to the socket.
     */
    public JServerTerminal(Socket socket) throws Exception {
        super(
                "",
                new ScannerInput(new Scanner(socket.getInputStream())),
                new CommandOutput(new PrintStream(socket.getOutputStream())),
                null
        );
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }
}