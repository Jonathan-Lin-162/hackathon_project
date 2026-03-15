
package io.github.Jonathan_Lin_162.hackathon_project.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.UIManager;

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
import javax.swing.JTextPane;
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
    private JButton themeToggle;
    private boolean darkMode = true;
    private StringBuilder aiBuffer = new StringBuilder();

    
    private JLabel label2;
    private JScrollPane scrollPane2;
    private JTextPane textarea2; // for the ai

    
    public Window() {        
        frame = new JFrame("Offline AI Assistant");
        frame.setSize(600, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainPanel = new JPanel();
        
        themeToggle = new JButton("Light Mode");
        themeToggle.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
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
                chat.setSystemPrompt("Give concise answer.");
                //chat = new OllamaChat("Give concise answer.");
            }
        });
        radioBtn2.addActionListener(e -> {
            if (radioBtn2.isSelected()) {
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
        buttonPanel.add(themeToggle);
        buttonPanel.setBackground(new Color(245, 245, 245));
        
        label = new JLabel("Enter your question: ");
        textarea = new JTextArea(5, 30);
        textarea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        scrollPane = new JScrollPane(textarea);
        
        
        label2 = new JLabel("AI response: ");
        textarea2 = new JTextPane();
        textarea2.setContentType("text/html");
        textarea2.setEditable(false);
        scrollPane2 = new JScrollPane(textarea2);
        
        textarea.setLineWrap(true);
        textarea.setWrapStyleWord(true);

        
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
        
        themeToggle.addActionListener(e -> {

            try {

            	if (darkMode) {
            	    UIManager.setLookAndFeel(new FlatDarkLaf());
            	    themeToggle.setText("Light Mode");

            	    mainPanel.setBackground(new Color(30, 30, 30));
            	    buttonPanel.setBackground(new Color(30,30,30));
            	    radioPanel.setBackground(new Color(30, 30, 30));

            	    radioBtn1.setBackground(new Color(30, 30, 30));
            	    radioBtn1.setForeground(Color.WHITE);

            	    radioBtn2.setBackground(new Color(30, 30, 30));
            	    radioBtn2.setForeground(Color.WHITE);

            	    header.setForeground(Color.WHITE);
            	    label.setForeground(new Color(212, 212, 212));
            	    label2.setForeground(new Color(212, 212, 212));

            	    textarea.setForeground(Color.WHITE);
            	    textarea.setBackground(new Color(45, 45, 45));
            	    textarea.setCaretColor(Color.WHITE);

            	    textarea2.setForeground(Color.WHITE);
            	    textarea2.setBackground(new Color(45, 45, 45));
            	    textarea2.setCaretColor(Color.WHITE);

            	} else {
            	    UIManager.setLookAndFeel(new FlatLightLaf());
            	    themeToggle.setText("Dark Mode");

            	    mainPanel.setBackground(new Color(245, 245, 245));
            	    buttonPanel.setBackground(new Color(245,245,245));
            	    radioPanel.setBackground(new Color(245, 245, 245));

            	    radioBtn1.setBackground(new Color(245, 245, 245));
            	    radioBtn1.setForeground(Color.BLACK);

            	    radioBtn2.setBackground(new Color(245, 245, 245));
            	    radioBtn2.setForeground(Color.BLACK);

            	    header.setForeground(new Color(45, 45, 45));
            	    label.setForeground(new Color(45, 45, 45));
            	    label2.setForeground(new Color(45, 45, 45));

            	    textarea.setForeground(Color.BLACK);
            	    textarea.setBackground(Color.WHITE);
            	    textarea.setCaretColor(Color.BLACK);

            	    textarea2.setForeground(Color.BLACK);
            	    textarea2.setBackground(Color.WHITE);
            	    textarea2.setCaretColor(Color.BLACK);
            	}

            	darkMode = !darkMode;
            	SwingUtilities.updateComponentTreeUI(frame);
            	
            	String html = markdownToHtml(aiBuffer.toString(), darkMode);
                textarea2.setText("<html>" + html + "</html>");

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        });
        
        frame.add(mainPanel);
        
        frame.setVisible(true);
    }

    private String markdownToHtml(String text, boolean darkMode) {

        String html = text;

        // escape html
        html = html.replace("&", "&amp;");
        html = html.replace("<", "&lt;");
        html = html.replace(">", "&gt;");

        // code block ```
        String preBg = darkMode ? "#e1e1e1" : "#3b3a3a"; // dark gray for dark mode
        String preColor = darkMode ? "#000000" : "#ffffff"; // white text in dark mode

        html = html.replaceAll("```([\\s\\S]*?)```",
            "<pre style='background:" + preBg + ";color:" + preColor + 
            ";padding:10px;border-radius:6px;font-family:monospace;'>$1</pre>");

        // inline code `
        String inlineBg = darkMode ? "#b5f5f5" : "#618080"; // slightly darker for inline code
        String inlineColor = darkMode ? "#000000" : "#ffffff";

        html = html.replaceAll("`(.*?)`",
            "<code style='background:" + inlineBg + ";color:" + inlineColor + 
            ";padding:2px 4px;border-radius:4px;font-family:monospace;'>$1</code>");

        // bold
        html = html.replaceAll("\\*\\*(.*?)\\*\\*", "<b>$1</b>");

        // italic
        html = html.replaceAll("\\*(.*?)\\*", "<i>$1</i>");

        // line breaks
        html = html.replace("\n", "<br>");

        return html;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {

        String input = textarea.getText();
        aiBuffer.setLength(0);
        textarea2.setText("Loading..."); 
        
        button.setEnabled(false);
        pause.setEnabled(true);
        stop.setEnabled(true);
        
        new Thread(() -> {

            try {
            	
                App.execute(input, chunk -> {

                    SwingUtilities.invokeLater(() -> {
                    	if (textarea2.getText().equals("Loading...")); {
                    	    textarea2.setText("");
                    	}
                    	aiBuffer.append(chunk);

                    	String html = markdownToHtml(aiBuffer.toString(), darkMode);

                    	textarea2.setText("<html>" + html + "</html>");
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
