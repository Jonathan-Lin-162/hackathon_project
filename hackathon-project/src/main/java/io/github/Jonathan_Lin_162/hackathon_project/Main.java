
package io.github.Jonathan_Lin_162.hackathon_project;

import java.io.IOException;

import javax.swing.*;

import javax.swing.border.EmptyBorder;

import java.awt.*;

import javax.swing.SwingUtilities;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.UIManager;

import io.github.Jonathan_Lin_162.hackathon_project.ui.Window;

public class Main {

    public static void main(String[] args) throws IOException {

    	
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Window();
            }
        });
        

    }

}
