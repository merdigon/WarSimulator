package com.company.Gui;

import com.company.Battle;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class ButtonPanel extends JPanel {

    private Thread simThread;
    private JLabel redArchers;
    private JLabel redWarriors;
    private JLabel redCavalry;
    private JLabel blueArchers;
    private JLabel blueWarriors;
    private JLabel blueCavalry;

    public ButtonPanel(final JPanel p, final Battle b) {

        JButton b1 = new JButton("Start");
        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                b.startLifeCycle();
            }
        });

        JButton b2 = new JButton("Stop");
        b2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                b.stopLifeCycle();
            }
        });

        JButton b3 = new JButton("Wyjście");
        b3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);

            }
        });

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(2, 2, 2, 2);
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridwidth = 2;
        add(b1, gbc);

        gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridwidth = 2;
        add(b2, gbc);
        gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridwidth = 2;
        add(b3, gbc);

        redArchers = new JLabel("Łucznicy: 0",
                JLabel.CENTER);
        redArchers.setForeground(Color.RED);
        redWarriors = new JLabel("Piechota: 0",
                JLabel.CENTER);
        redWarriors.setForeground(Color.RED);
        redCavalry = new JLabel("Konnica: 0",
                JLabel.CENTER);
        redCavalry.setForeground(Color.RED);
        blueArchers = new JLabel("Łucznicy: 0",
                JLabel.CENTER);
        blueArchers.setForeground(Color.BLUE);
        blueWarriors = new JLabel("Piechota: 0",
                JLabel.CENTER);
        blueWarriors.setForeground(Color.BLUE);
        blueCavalry = new JLabel("Konnica: 0",
                JLabel.CENTER);
        blueCavalry.setForeground(Color.BLUE);
        gbc.gridy++;
        add(new JLabel("________", JLabel.CENTER), gbc);
        gbc.gridy++;
        add(redArchers, gbc);
        gbc.gridy++;
        add(redWarriors, gbc);
        gbc.gridy++;
        add(redCavalry, gbc);
        gbc.gridy++;
        add(new JLabel("________", JLabel.CENTER), gbc);
        gbc.gridy++;
        add(blueArchers, gbc);
        gbc.gridy++;
        add(blueWarriors, gbc);
        gbc.gridy++;
        add(blueCavalry, gbc);

    }

    public void setRedArchersLabel(int count) {
        redArchers.setText("Łucznicy: " + count);
    }

    public void setRedWarriorsLabel(int count) {
        redWarriors.setText("Piechota: " + count);
    }

    public void setRedCavalryLabel(int count) {
        redCavalry.setText("Konnica: " + count);
    }

    public void setBlueArchersLabel(int count) {
        blueArchers.setText("Łucznicy: " + count);
    }

    public void setBlueWarriorsLabel(int count) {
        blueWarriors.setText("Piechota: " + count);
    }

    public void setBlueCavalryLabel(int count) {
        blueCavalry.setText("Konnica: " + count);
    }

}