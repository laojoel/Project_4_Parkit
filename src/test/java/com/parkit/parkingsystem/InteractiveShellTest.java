package com.parkit.parkingsystem;

import com.parkit.parkingsystem.service.InteractiveShell;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class InteractiveShellTest {

    @Test
    public void loadMenuTest() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        InteractiveShell.showMenu();
        String out = outContent.toString();
        boolean isContaining = out.contains("select an option") && out.contains("1 ") && out.contains("2 ") && out.contains("3 ");
        assertTrue(isContaining);
    }

}
