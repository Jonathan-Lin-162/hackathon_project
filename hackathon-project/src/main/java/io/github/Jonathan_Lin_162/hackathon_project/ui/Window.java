package io.github.Jonathan_Lin_162.hackathon_project.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import io.github.Jonathan_Lin_162.hackathon_project.core.App;

public class Window implements ActionListener {
    // Container for the application
    private JFrame frame;
    private JLabel label;
    private JScrollPane scrollPane;
    private JTextArea textarea;
    private JPanel mainPanel;
    private JButton button;

    
    public Window() {        
        frame = new JFrame("Project Name");
        frame.setSize(700, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainPanel = new JPanel();
        
        label = new JLabel("Enter your question: ");
        textarea = new JTextArea(5, 30);
        scrollPane = new JScrollPane(textarea);
        button = new JButton("Go");
        button.addActionListener(this);
        
        mainPanel.add(label);
        mainPanel.add(scrollPane);
        mainPanel.add(button);
        
        frame.add(mainPanel);
        
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String input = textarea.getText();
        System.out.println(input);
        try {
            App.execute(input);
        } catch (Exception e1) {
			e1.printStackTrace();
		}

    }

}