
package io.github.Jonathan_Lin_162.hackathon_project.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import io.github.Jonathan_Lin_162.hackathon_project.core.App;

public class Window implements ActionListener {
    // Container for the application
    private JFrame frame;
    private JLabel label;
    private JScrollPane scrollPane;
    private JTextArea textarea;
    private JPanel mainPanel;
    private JButton button;
    private JLabel header;

    
    private JLabel label2;
    private JScrollPane scrollPane2;
    private JTextArea textarea2; // for the ai

    
    public Window() {        
        frame = new JFrame("Project Name");
        frame.setSize(400, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainPanel = new JPanel();
        
        header = new JLabel("What are you working on?");
        header.setAlignmentX(Component.CENTER_ALIGNMENT);
        header.setFont(new Font("Roboto", Font.BOLD, 25));
        header.setForeground(new Color(45, 45, 45));
        
        
        label = new JLabel("Enter your question: ");
        textarea = new JTextArea(5, 30);
        scrollPane = new JScrollPane(textarea);
        button = new JButton("Go");
        button.addActionListener(this);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        label2 = new JLabel("AI response: ");
        textarea2 = new JTextArea(5, 30);
        scrollPane2 = new JScrollPane(textarea2);
        
        textarea.setLineWrap(true);
        textarea.setWrapStyleWord(true);

        textarea2.setLineWrap(true);
        textarea2.setWrapStyleWord(true);
        
        textarea.setBorder(new EmptyBorder(10,10,10,10));
        textarea2.setBorder(new EmptyBorder(10,10,10,10));
        
        textarea2.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textarea2.setEditable(false);
        textarea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setFont(new Font("Roboto", Font.BOLD, 15));
        label2.setAlignmentX(Component.CENTER_ALIGNMENT);
        label2.setFont(new Font("Roboto", Font.BOLD, 15));
        
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(245, 245, 245));
        
        mainPanel.add(header);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(label);
        mainPanel.add(Box.createRigidArea(new Dimension(0,10)));
        mainPanel.add(scrollPane);
        mainPanel.add(Box.createRigidArea(new Dimension(0,10)));
        mainPanel.add(button);
        mainPanel.add(Box.createRigidArea(new Dimension(0,10)));
        mainPanel.add(label2);
        mainPanel.add(Box.createRigidArea(new Dimension(0,10)));
        mainPanel.add(scrollPane2);
        
        frame.add(mainPanel);
        
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String input = textarea.getText();
        textarea2.setText(""); // clear old response

        new Thread(() -> {

            try {

                App.execute(input, chunk -> {

                    SwingUtilities.invokeLater(() -> {
                        textarea2.append(chunk);
                    });

                });

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }).start();
    }

}
