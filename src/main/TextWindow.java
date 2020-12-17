package main;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.awt.Point;

public class TextWindow {

    public JPanel customBar;
    public JPanel m_Panel;
    public String programPath = System.getenv("APPDATA") + "/STexts/";
    public JTextArea textArea;
    public String windowTitle = "Untitled";

    private JFrame m_Frame;
    private String fileName = null;

    public TextWindow() {
        m_Frame = new JFrame();
        m_Frame.setSize(500, 500);
        m_Frame.setTitle("FOda");
        m_Frame.setUndecorated(true);
        m_Frame.setLayout(new BorderLayout());
        m_Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        CreateContext();
        m_Frame.setVisible(true);
    }

    public TextWindow(String fileName) {
        m_Frame = new JFrame();
        m_Frame.setSize(500, 500);
        m_Frame.setTitle("FOda");
        m_Frame.setUndecorated(true);
        m_Frame.setLayout(new BorderLayout());
        m_Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.fileName = fileName;
        CreateContext();
        m_Frame.setVisible(true);
    }

    private void CreateContext() {
        m_Panel = new JPanel();
        m_Panel.setLayout(new BorderLayout());
        CreateTextBox();
        CreateMainBar();
        customBar = new JPanel();
        customBar.setLayout(new BorderLayout());
        CustomTitleBar();
        m_Frame.add(customBar, BorderLayout.NORTH);
        m_Frame.add(m_Panel);
    }

    public void CreateTextBox() {
        textArea = new JTextArea(10, 10);
        Font textFont = new Font("Arial", Font.PLAIN, 18);
        if (fileName != null) textArea.setText(LoadText());
        textArea.setFont(textFont);
        textArea.setBorder(null);
        textArea.setLineWrap(true);
        textArea.setBackground(new Color(47, 54, 64));
        textArea.setForeground(Color.WHITE);
        textArea.setCaretColor(Color.WHITE);
        textArea.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                if ((e.getKeyCode() == KeyEvent.VK_S) && e.getModifiersEx() == KeyEvent.CTRL_DOWN_MASK) {
                    SaveText(textArea.getText());
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
            @Override
            public void keyTyped(KeyEvent e) {}
        });
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(null);
        m_Panel.add(scrollPane, BorderLayout.CENTER);
    }

    public void CreateMainBar() {
        JPanel mainBar = new JPanel();
        mainBar.setLayout(new BorderLayout());
        mainBar.setBackground(new Color(53, 59, 72));
        JButton addButton = new JButton("+");
        addButton.setOpaque(true);
        addButton.setBackground(Color.BLACK);
        addButton.setForeground(Color.WHITE);
        addButton.setPreferredSize(new Dimension(45, 30));
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(0);
            }
        });
        JTextField titleBar = new JTextField();
        titleBar.setPreferredSize(new Dimension(100, 30));
        titleBar.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                windowTitle = titleBar.getText();
            }
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        if (fileName != null) titleBar.setText(fileName);
        mainBar.add(addButton, BorderLayout.EAST);
        mainBar.add(titleBar, BorderLayout.WEST);
        m_Panel.add(mainBar, BorderLayout.NORTH);
    }

    public void CustomTitleBar() {
        Point compPosition = new Point(0, 0);
        customBar.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
            @Override
            public void mousePressed(MouseEvent e) {
                compPosition.setLocation(e.getX(), e.getY());
            }
            @Override
            public void mouseReleased(MouseEvent e) {}
        });
        customBar.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseMoved(MouseEvent e) {

            }
            @Override
            public void mouseDragged(MouseEvent e) {
                m_Frame.setLocation(e.getXOnScreen() - compPosition.x, e.getYOnScreen() - compPosition.y);
            }
        });
        final JButton exit = new JButton("X");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                m_Frame.dispose();
            }
        });
        customBar.add(exit, BorderLayout.EAST);
        customBar.setBackground(new Color(53, 59, 72));
    }

    public void SaveText(String text) {
        try {
            FileWriter file = new FileWriter(programPath + "/" + windowTitle + ".st");
            file.write(text);
            file.close();
        } catch(IOException e) {}
    }

    public String LoadText() {
        File defaultFolder = new File(programPath);
        if (!defaultFolder.exists()) {
            defaultFolder.mkdir();
            return null;
        }
        try {   
            File file = new File(programPath + "/" + fileName + ".st");
            if (file.exists()) {
                String text = null;
                Scanner scanner = new Scanner(file);
                while (scanner.hasNextLine()) {
                    text = scanner.nextLine();
                }
                scanner.close();
                return text;
            }
        } catch(FileNotFoundException e) {
        } catch (IOException e) {
        }
        return null;
    }

}