package main;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.event.*;
import java.awt.Point;
import java.awt.GridLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Scanner;

public class Main implements Runnable {

    public JPanel customBar;
    public JPanel filesPanel;
    public JFrame m_Frame;
    public JPanel m_Panel;
    public String m_Text;
    public String programPath = System.getenv("APPDATA") + "/STexts/";

    private int filesQuantity;

    public Main() {
        m_Frame = new JFrame();
        m_Frame.setSize(500, 500);
        m_Frame.setTitle("FOda");
        m_Frame.setUndecorated(true);
        m_Frame.setLayout(new BorderLayout());
        m_Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        CreateContext();
        m_Frame.setVisible(true);
    }

    
    public static void main(String[] args) {
        Main main = new Main();
        main.start();
    }
    
    public synchronized void start() {
        Thread thread = new Thread(this);
        thread.start();
    }

    public void run() {
        while (true) {
            if (filesQuantity != FilesQuantity()) {
                System.out.println(0);
                filesPanel.removeAll();
                LoadFilesPanel();
                filesPanel.revalidate();
                filesPanel.repaint();
            }
        }
    }

    private void CreateContext() {
        m_Panel = new JPanel();
        m_Panel.setLayout(new BorderLayout());
        CreateMainBar();
        customBar = new JPanel();
        customBar.setLayout(new BorderLayout());
        CustomTitleBar();
        m_Frame.add(customBar, BorderLayout.NORTH);
        filesPanel = new JPanel();
        LoadFilesPanel();
        m_Panel.add(filesPanel, BorderLayout.CENTER);
        m_Frame.add(m_Panel);
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
                CreateWindow();
            }
        });
        mainBar.add(addButton, BorderLayout.EAST);
        m_Panel.add(mainBar, BorderLayout.NORTH);
    }

    public void CreateWindow() {
        TextWindow textWindow = new TextWindow();
    }

    public void LoadWindow(String fileName) {
        TextWindow textWindow = new TextWindow(fileName);
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
                System.exit(0);
            }
        });
        customBar.add(exit, BorderLayout.EAST);
        customBar.setBackground(new Color(53, 59, 72));
    }

    public void LoadFilesPanel() {
        JButton[] panelButtons = GetAllFiles();
        filesPanel.setLayout(new GridLayout(panelButtons.length, 1));
        for (int i = 0; i < panelButtons.length; i++) {
            filesPanel.add(panelButtons[i], BorderLayout.CENTER);
        }
    }

    public JButton[] GetAllFiles() {
        File defaultFolder = new File(programPath);
        if (!defaultFolder.exists()) defaultFolder.mkdir();
        String[] allFiles = defaultFolder.list();
        File[] fileNames = defaultFolder.listFiles();
        filesQuantity = allFiles.length;
        JButton[] panelButtons = new JButton[allFiles.length];
        for (int i = 0; i < allFiles.length; i++) {
            String fileName = fileNameHandler(fileNames[i].getName());
            panelButtons[i] = new JButton(fileName);
            panelButtons[i].setOpaque(true);
            panelButtons[i].setBackground(Color.BLACK);
            panelButtons[i].setForeground(Color.WHITE);
            panelButtons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    LoadWindow(fileName);
                }
            });
        }
        return panelButtons;
    }

    public String fileNameHandler(String file) {
        if (file == null) return null;
        int dotPosition = file.lastIndexOf(".");
        if (dotPosition == -1) return file;
        return file.substring(0, dotPosition);
    }

    public int FilesQuantity() {
        File defaultFolder = new File(programPath);
        return defaultFolder.list().length;
    }

}
