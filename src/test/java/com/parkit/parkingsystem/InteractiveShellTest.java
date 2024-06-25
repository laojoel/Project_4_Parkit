package com.parkit.parkingsystem;

import com.parkit.parkingsystem.service.InteractiveShell;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InteractiveShellTest {

    //@Mock
    //InteractiveShell interactiveShell;

    @Test
    public void loadMenuTest() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        InteractiveShell.showMenu();
        String out = outContent.toString();
        boolean isContaining = false;
        if (out.contains("select an option") && out.contains("1 ") && out.contains("2 ") && out.contains("3 ")) {isContaining=true;}
        assertTrue(isContaining);
    }

}
