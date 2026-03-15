
package io.github.Jonathan_Lin_162.hackathon_project.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import io.github.Jonathan_Lin_162.hackathon_project.core.App;
import io.github.Jonathan_Lin_162.hackathon_project.core.OllamaChat;

public class Window implements ActionListener {
    // Container for the application
    private JFrame frame;
    private JLabel label;
    private JScrollPane scrollPane;
    private JTextArea textarea;
    private JPanel mainPanel;
    private JButton button;
    private JLabel header;
    private JButton pause;
    private JButton stop;
    private JPanel buttonPanel;
    private JPanel radioPanel;
    private ButtonGroup btnGroup;
    private JRadioButton radioBtn1;
    private JRadioButton radioBtn2;
    private OllamaChat chat;

    
    private JLabel label2;
    private JScrollPane scrollPane2;
    private JTextArea textarea2; // for the ai

    
    public Window() {        
        frame = new JFrame("Offline AI Assistance");
        frame.setSize(400, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainPanel = new JPanel();
        
        header = new JLabel("What are you working on?");
        header.setAlignmentX(Component.CENTER_ALIGNMENT);
        header.setFont(new Font("Roboto", Font.BOLD, 25));
        header.setForeground(new Color(45, 45, 45));
        
        chat = new OllamaChat();
        chat.style("Give concise answer.");
        
        label = new JLabel("Enter your question: ");
        textarea = new JTextArea(5, 30);
        scrollPane = new JScrollPane(textarea);
        btnGroup = new ButtonGroup();
        radioBtn1 = new JRadioButton();
        radioBtn2 = new JRadioButton();
        radioBtn1.setText("Short");
        radioBtn1.setSelected(true);
        radioBtn2.setText("Long");
        radioBtn1.setBounds(50, 50, 100, 30);
        radioBtn2.setBounds(50, 80, 100, 30);
        btnGroup.add(radioBtn1);
        btnGroup.add(radioBtn2);
        radioPanel = new JPanel();
        radioPanel.setMaximumSize(new Dimension(400, 60));
        radioPanel.setLayout(new BoxLayout(radioPanel, BoxLayout.X_AXIS));
        radioPanel.setBorder(BorderFactory.createTitledBorder("Select answer length"));
        radioPanel.add(radioBtn1);
        radioPanel.add(Box.createRigidArea(new Dimension(0,10)));
        radioPanel.add(radioBtn2);
        radioBtn1.addActionListener(e -> {
            if (radioBtn1.isSelected()) {
                System.out.println("Radio button 1 pressed");
                chat.setSystemPrompt("Give concise answer.");
                //chat = new OllamaChat("Give concise answer.");
            }
        });
        radioBtn2.addActionListener(e -> {
            if (radioBtn2.isSelected()) {
                System.out.println("Radio button 2 pressed");
                chat.setSystemPrompt("Give answer long");
               // chat = new OllamaChat("");
            }
        });
        
        button = new JButton("Go");
        button.addActionListener(this);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        pause = new JButton("Pause");
        pause.setAlignmentX(Component.CENTER_ALIGNMENT);
        pause.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        stop = new JButton("Stop");
        stop.setAlignmentX(Component.CENTER_ALIGNMENT);
        stop.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        buttonPanel = new JPanel();
        buttonPanel.add(button);
        buttonPanel.add(pause);
        buttonPanel.add(stop);
        buttonPanel.setBackground(new Color(245, 245, 245));
        
        label = new JLabel("Enter your question: ");
        textarea = new JTextArea(5, 30);
        scrollPane = new JScrollPane(textarea);
        
        
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
        pause.setEnabled(false);
        stop.setEnabled(false);
        
        
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(245, 245, 245));
        
        mainPanel.add(header);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(label);
        mainPanel.add(Box.createRigidArea(new Dimension(0,10)));
        mainPanel.add(scrollPane);
        mainPanel.add(Box.createRigidArea(new Dimension(0,10)));
        mainPanel.add(buttonPanel);
        mainPanel.add(radioPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0,10)));
        mainPanel.add(label2);
        mainPanel.add(Box.createRigidArea(new Dimension(0,10)));
        mainPanel.add(scrollPane2);
        
        pause.addActionListener(e -> {
        	if (pause.getText().equals("Pause")) {
        		App.pause();
        		pause.setText("Resume");
        	} else {
        		App.resume();
        		pause.setText("Pause");
        	}
        });
        
        stop.addActionListener(e -> {
        	App.stop();
        	textarea2.setText("");
        	button.setEnabled(true);
        	pause.setEnabled(false);
        });
        
        frame.add(mainPanel);
        
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String input = textarea.getText();
        textarea2.setText(""); // clear old response
        
        button.setEnabled(false);
        pause.setEnabled(true);
        stop.setEnabled(true);
        
        new Thread(() -> {

            try {

                App.execute(input, chunk -> {

                    SwingUtilities.invokeLater(() -> {
                        textarea2.append(chunk);
                    });

                }, chat.getSystemPrompt());

            } catch (Exception ex) {
                ex.printStackTrace();
            }
            
            SwingUtilities.invokeLater(() -> button.setEnabled(true));
            SwingUtilities.invokeLater(() -> pause.setEnabled(false));
            SwingUtilities.invokeLater(() -> stop.setEnabled(false));

        }).start();
    }
    
    

}
