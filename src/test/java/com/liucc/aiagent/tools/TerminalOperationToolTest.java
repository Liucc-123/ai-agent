package com.liucc.aiagent.tools;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TerminalOperationToolTest {

    @Test
    void executeTerminalCommand() {
        TerminalOperationTool terminalOperationTool = new TerminalOperationTool();
        String command = "echo Hello, World!";
        String result = terminalOperationTool.executeTerminalCommand(command);
        assertNotNull(result);
        assertTrue(result.contains("Hello, World!"), "The command output should contain 'Hello, World!'");
    }
}