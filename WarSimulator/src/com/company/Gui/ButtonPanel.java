package com.company.Gui;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ButtonPanel extends JPanel {

	private Thread simThread;

	public ButtonPanel(final JPanel p) {

		JButton b1 = new JButton("Start");
		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				simThread = new Thread(new Runnable() {

					@Override
					public void run() {

						while (!Thread.currentThread().isInterrupted()) {
							// do sth
							//p.updateUI();
						}

					}
				});
				simThread.start();
			}
		});

		JButton b2 = new JButton("Stop");
		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				simThread.interrupt();

			}
		});

		JButton b3 = new JButton("Wyjœcie");
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

	}

}