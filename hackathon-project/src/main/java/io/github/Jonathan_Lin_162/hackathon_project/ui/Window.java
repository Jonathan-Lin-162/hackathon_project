package io.github.Jonathan_Lin_162.hackathon_project.ui;

import javax.swing.JFrame;

public class Window {
    
    public Window() {
        JFrame frame = new JFrame("Project Name");
        
        frame.setSize(700, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame.setVisible(true);
    }
}
