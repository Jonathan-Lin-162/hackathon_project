package io.github.Jonathan_Lin_162.hackathon_project;

import javax.swing.SwingUtilities;

import io.github.Jonathan_Lin_162.hackathon_project.ui.Window;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Window();
            }
        });

    }

}
