package com.alientation.chess.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import com.alientation.chess.game.Util;
import com.alientation.chess.game.init.Game;
import com.alientation.chess.game.settings.AISetting;
import com.alientation.chess.game.settings.BoardSetting;
import com.alientation.chess.game.settings.GameSetting;

public class StartWindow implements Runnable{

	@Override
	public void run() {
		GameSetting gs = new GameSetting();
		
		
		//Window
		final JFrame startWindow = new JFrame("AlienChess");
		startWindow.setLocation(300,100);
		startWindow.setResizable(false);
		startWindow.setSize(300,800);
		
		
		//Outer most vertical box
		//final Box outerLayerBox = Box.createVerticalBox();
		final Box outerLayerBox = createBox(gs, startWindow);
		startWindow.add(outerLayerBox);
		
		
		/*
		 * In future make editable board setting
		 */
		
		gs.boardSetting = new BoardSetting();
		
		startWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		startWindow.setVisible(true);
	}
	
	public Box createBox(GameSetting gs, JFrame startWindow) {
		
		final Box outerLayerBox = Box.createVerticalBox();
		
		//Title
		final JPanel titlePanel = new JPanel();
		final JLabel titleLabel = new JLabel("AlienChess v" + Game.version);
		titlePanel.add(titleLabel);
		outerLayerBox.add(titlePanel);
		
		//Black
		final JPanel blackPanel = new JPanel();
		blackPanel.setLayout(new BoxLayout(blackPanel, BoxLayout.Y_AXIS));
		
		
		final JLabel blackPiece = new JLabel();
		blackPiece.setIcon(new ImageIcon(Util.loadImage(Game.LARGE_BLACK_PAWN)));
		//blackPiece.setAlignmentX(Component.CENTER_ALIGNMENT);
		blackPanel.add(blackPiece);
		blackPanel.add(Box.createHorizontalStrut(5));
		outerLayerBox.add(blackPanel, BorderLayout.EAST);
		
		final JPanel blackAIPanel = new JPanel();
		blackAIPanel.setLayout(new BoxLayout(blackAIPanel, BoxLayout.Y_AXIS));
		blackPanel.add(blackAIPanel);
		
		//Black AI
		final JCheckBox blackAICheckBox = new JCheckBox("Is Black AI", false);
		blackAICheckBox.setMaximumSize(blackAICheckBox.getPreferredSize());
		blackAICheckBox.setAlignmentX(Component.LEFT_ALIGNMENT);
		blackPanel.add(blackAICheckBox);
		blackPanel.add(Box.createHorizontalStrut(5));
		
		final JComboBox<String> blackAIList = new JComboBox<String>(Game.AI_LIST);
		blackPanel.add(blackAIList);
		blackPanel.add(Box.createHorizontalStrut(5));
		blackAIList.setEnabled(false);
		blackAIList.setMaximumSize(blackAIList.getPreferredSize());
		blackAIList.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		final JButton blackAISettings = new JButton("AI Settings");
		blackPanel.add(blackAISettings);
		blackAISettings.setEnabled(false);
		blackAISettings.setMaximumSize(blackAISettings.getPreferredSize());
		blackAISettings.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		blackPanel.add(Box.createHorizontalStrut(5));
		
		
		//Black Player name
		final Box blackNameInput = Box.createHorizontalBox();
		final JLabel blackNameLabel = new JLabel("Black Name: ");
		blackNameLabel.setMaximumSize(blackNameLabel.getPreferredSize());
		blackNameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		final JTextField blackInput = new JTextField("Black", 10);
		blackInput.setMaximumSize(blackInput.getPreferredSize());
		blackInput.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		blackNameInput.add(blackNameLabel);
		blackNameInput.add(Box.createVerticalGlue());
		blackNameInput.add(blackInput);
		blackNameInput.setMaximumSize(blackNameInput.getPreferredSize());
		blackNameInput.setAlignmentX(Component.LEFT_ALIGNMENT);
		blackPanel.add(blackNameInput);
		blackPanel.add(Box.createHorizontalGlue());
		
		
		//Black Event Listeners
		blackAICheckBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == 1) {
					//Checked
					blackAIList.setEnabled(true);
					blackInput.setEnabled(false);
					blackAISettings.setEnabled(true);
					blackInput.setText((String) blackAIList.getSelectedItem());
					gs.blackAI = (String) blackAIList.getSelectedItem();
					gs.blackName = (String) blackAIList.getSelectedItem();
					gs.blackAISetting = new AISetting();
					gs.isBlackAI = true;
				} else {
					blackInput.setEnabled(true);
					blackInput.setText("Black");
					blackAIList.setEnabled(false);
					blackAISettings.setEnabled(false);
					
					gs.blackAI = null;
					gs.blackAISetting = null;
					gs.blackName = null;
					gs.isBlackAI = false;
				}
			}
		});
		
		
		blackAIList.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				gs.blackAI = (String) blackAIList.getSelectedItem();
				gs.blackAISetting = new AISetting();
			}
		});
		
		
		blackAISettings.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				/*
				 * Open AI Window for black
				 */
				final JFrame blackAISettingWindow = createAISettingWindow("BLACK",gs);
				
				
				
				
				
				blackAISettingWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				blackAISettingWindow.setVisible(true);
			}
		});
		
		outerLayerBox.add(Box.createHorizontalStrut(20));
		outerLayerBox.add(new JSeparator(JSeparator.HORIZONTAL));
		outerLayerBox.add(Box.createHorizontalStrut(20));
		
		//White
		final JPanel whitePanel = new JPanel();
		whitePanel.setLayout(new BoxLayout(whitePanel, BoxLayout.Y_AXIS));
		
		
		final JLabel whitePiece = new JLabel();
		whitePiece.setIcon(new ImageIcon(Util.loadImage(Game.LARGE_WHITE_PAWN)));
		whitePanel.add(whitePiece);
		whitePanel.add(Box.createHorizontalStrut(15));
		outerLayerBox.add(whitePanel, BorderLayout.EAST);
		
		final JPanel whiteAIPanel = new JPanel();
		whiteAIPanel.setLayout(new BoxLayout(whiteAIPanel, BoxLayout.Y_AXIS));
		whitePanel.add(whiteAIPanel);
		
		//white AI
		final JCheckBox whiteAICheckBox = new JCheckBox("Is White AI", false);
		whiteAICheckBox.setMaximumSize(whiteAICheckBox.getPreferredSize());
		whiteAICheckBox.setAlignmentX(Component.LEFT_ALIGNMENT);
		whitePanel.add(whiteAICheckBox);
		whitePanel.add(Box.createHorizontalStrut(10));
		
		final JComboBox<String> whiteAIList = new JComboBox<String>(Game.AI_LIST);
		whitePanel.add(whiteAIList);
		whitePanel.add(Box.createHorizontalStrut(10));
		whiteAIList.setEnabled(false);
		whiteAIList.setMaximumSize(whiteAIList.getPreferredSize());
		whiteAIList.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		final JButton whiteAISettings = new JButton("AI Settings");
		whitePanel.add(whiteAISettings);
		whiteAISettings.setEnabled(false);
		whiteAISettings.setMaximumSize(whiteAISettings.getPreferredSize());
		whiteAISettings.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		whitePanel.add(Box.createHorizontalStrut(10));
		
		
		//white Player name
		final Box whiteNameInput = Box.createHorizontalBox();
		final JLabel whiteNameLabel = new JLabel("White Name: ");
		whiteNameLabel.setMaximumSize(whiteNameLabel.getPreferredSize());
		whiteNameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		final JTextField whiteInput = new JTextField("White", 10);
		whiteInput.setMaximumSize(whiteInput.getPreferredSize());
		whiteInput.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		whiteNameInput.add(whiteNameLabel);
		whiteNameInput.add(Box.createVerticalGlue());
		whiteNameInput.add(whiteInput);
		whiteNameInput.setMaximumSize(whiteNameInput.getPreferredSize());
		whiteNameInput.setAlignmentX(Component.LEFT_ALIGNMENT);
		whitePanel.add(whiteNameInput);
		whitePanel.add(Box.createHorizontalGlue());
		
		
		//white Event Listeners
		whiteAICheckBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == 1) {
					//Checked
					whiteAIList.setEnabled(true);
					whiteInput.setEnabled(false);
					whiteAISettings.setEnabled(true);
					whiteInput.setText((String) whiteAIList.getSelectedItem());
					gs.whiteAI = (String) whiteAIList.getSelectedItem();
					gs.whiteName = (String) whiteAIList.getSelectedItem();
					gs.whiteAISetting = new AISetting();
					gs.isWhiteAI = true;
				} else {
					whiteInput.setEnabled(true);
					whiteInput.setText("White");
					whiteAIList.setEnabled(false);
					whiteAISettings.setEnabled(false);
					
					gs.whiteAI = null;
					gs.whiteAISetting = null;
					gs.whiteName = null;
					gs.isWhiteAI = false;
				}
			}
		});
		
		
		whiteAIList.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				gs.whiteAI = (String) whiteAIList.getSelectedItem();
				gs.whiteAISetting = new AISetting();
			}
		});
		
		
		whiteAISettings.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				/*
				 * Open AI Window for white
				 */
				final JFrame whiteAISettingWindow = createAISettingWindow("BLACK",gs);
				
				
				
				
				
				whiteAISettingWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				whiteAISettingWindow.setVisible(true);
			}
		});
		
		final String[] minSecInts = new String[60];
        for (int i = 0; i < 60; i++) {
            if (i < 10) {
                minSecInts[i] = "0" + Integer.toString(i);
            } else {
                minSecInts[i] = Integer.toString(i);
            }
        }
        
        
        outerLayerBox.add(Box.createHorizontalStrut(20));
		outerLayerBox.add(new JSeparator(JSeparator.HORIZONTAL));
		outerLayerBox.add(Box.createHorizontalStrut(20));
        
        
        final JComboBox<String> seconds = new JComboBox<String>(minSecInts);
        final JComboBox<String> minutes = new JComboBox<String>(minSecInts);
        final JComboBox<String> hours = 
                new JComboBox<String>(new String[] {"0","1","2","3"});
        
        Box timerSettings = Box.createHorizontalBox();
        
        hours.setMaximumSize(hours.getPreferredSize());
        minutes.setMaximumSize(minutes.getPreferredSize());
        seconds.setMaximumSize(minutes.getPreferredSize());
        
        timerSettings.add(hours);
        timerSettings.add(Box.createHorizontalStrut(10));
        timerSettings.add(minutes);
        timerSettings.add(Box.createHorizontalStrut(10));
        timerSettings.add(seconds);
        
        timerSettings.add(Box.createVerticalGlue());
        
        outerLayerBox.add(timerSettings);
		
		
        Box buttons = Box.createHorizontalBox();
        final JButton quit = new JButton("Quit");
        
        quit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              startWindow.dispose();
            }
          });
        
        final JButton instr = new JButton("Instructions");
        
        instr.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(startWindow,
                        "To begin a new game, input player names\n" +
                        "next to the pieces. Set the clocks and\n" +
                        "click \"Start\". Setting the timer to all\n" +
                        "zeroes begins a new untimed game.",
                        "How to play",
                        JOptionPane.PLAIN_MESSAGE);
            }
          });
        
        final JButton start = new JButton("Start");
        
        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	gs.whiteName = whiteInput.getText();
            	gs.blackName = blackInput.getText();
            	
                gs.hh = Integer.parseInt((String) hours.getSelectedItem());
                gs.mm = Integer.parseInt((String) minutes.getSelectedItem());
                gs.ss = Integer.parseInt((String) seconds.getSelectedItem());
                
                
                new GameWindow(gs);
                startWindow.dispose();
            }
          });
        
        buttons.add(start);
        buttons.add(Box.createHorizontalStrut(10));
        buttons.add(instr);
        buttons.add(Box.createHorizontalStrut(10));
        buttons.add(quit);
        outerLayerBox.add(buttons);
        
        outerLayerBox.add(Box.createGlue());
        
		return outerLayerBox;
	}
	
	public JFrame createAISettingWindow(String team, GameSetting gs) {
		String name = gs.whiteName;
		String ai = gs.whiteAI;
		if (team.equals("BLACK")) {
			name = gs.blackName;
			ai = gs.blackAI;
		}
		final JFrame AISettingWindow = new JFrame(name + " Settings");
		switch(ai) {
		case "DummyBot v0.0.a":
			
			break;
		}
		return AISettingWindow;
	}
}
