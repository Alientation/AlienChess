package com.alientation.chess.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import com.alientation.chess.game.Board;
import com.alientation.chess.game.Clock;
import com.alientation.chess.game.settings.GameSetting;
import com.alientation.chess.test.StartMenu;

public class GameWindow {
	private JFrame gameWindow;
	
	private Clock blackClock;
	private Clock whiteClock;
	
	private Timer timer;
	
	private Board board;
	
	private GameSetting gameSetting;
	
	public GameWindow(GameSetting gs) {
		this.gameSetting = gs;
		
		this.blackClock = new Clock(this.gameSetting.hh, this.gameSetting.mm, this.gameSetting.ss);
		this.whiteClock = new Clock(this.gameSetting.hh, this.gameSetting.mm, this.gameSetting.ss);
		
		this.gameWindow = new JFrame("Alien Chess Game");
		
		/*
		 * TODO: Add window icon for the esthetics
		 */
		
		this.gameWindow.setLocation(100,100);
		this.gameWindow.setLayout(new BorderLayout(20,20));
		
		JPanel gameData = gameDataPanel();
		gameData.setSize(gameData.getPreferredSize());
		this.gameWindow.add(gameData, BorderLayout.NORTH);
		
		this.board = new Board(this, Board.STANDARD_LAYOUT, gs);
		
		this.gameWindow.add(this.board, BorderLayout.CENTER);
		
		this.gameWindow.add(buttons(), BorderLayout.SOUTH);
		
		this.gameWindow.setMinimumSize(this.gameWindow.getPreferredSize());
		this.gameWindow.setSize(this.gameWindow.getPreferredSize());
		this.gameWindow.setResizable(false);
		
		this.gameWindow.pack();
		this.gameWindow.setVisible(true);
		this.gameWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	
	private JPanel gameDataPanel() {
		JPanel gameData = new JPanel();
		gameData.setLayout(new GridLayout(3,2,0,0));
		
		
		JLabel w = new JLabel(this.gameSetting.whiteName);
		JLabel b = new JLabel(this.gameSetting.blackName);
		
		w.setHorizontalAlignment(JLabel.CENTER);
		b.setHorizontalAlignment(JLabel.CENTER);
		w.setVerticalAlignment(JLabel.CENTER);
		b.setVerticalAlignment(JLabel.CENTER);
		
		w.setSize(w.getMinimumSize());
		b.setSize(b.getMinimumSize());
		
		gameData.add(w);
		gameData.add(b);
		
		final JLabel bTime = new JLabel(blackClock.getTime());
		final JLabel wTime = new JLabel(whiteClock.getTime());
		
		bTime.setHorizontalAlignment(JLabel.CENTER);
		bTime.setVerticalAlignment(JLabel.CENTER);
		wTime.setHorizontalAlignment(JLabel.CENTER);
		wTime.setVerticalAlignment(JLabel.CENTER);
		
		if (!(this.gameSetting.hh == 0 && this.gameSetting.mm == 0 && this.gameSetting.ss == 0)) {
			this.timer = new Timer(1000, null);
			this.timer.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					boolean turn = board.getWhiteTurn();
					if (turn) {
						whiteClock.decrement();
						wTime.setText(whiteClock.getTime());
						
						if (whiteClock.outOfTime()) {
							timer.stop();
							int n = JOptionPane.showConfirmDialog(gameWindow, gameSetting.blackName + " wins by time! Play a new Game? \n Choocing \"No\" quits the game.", gameSetting.blackName + " wins!", JOptionPane.YES_NO_OPTION);
							if (n == JOptionPane.YES_OPTION) {
								new GameWindow(gameSetting);
								gameWindow.dispose();
							} else gameWindow.dispose();
						}
					} else {
						blackClock.decrement();
						bTime.setText(blackClock.getTime());
						
						if (blackClock.outOfTime()) {
							timer.stop();
							int n = JOptionPane.showConfirmDialog(gameWindow, gameSetting.whiteName + " wins by time! Play a new Game? \n Choocing \"No\" quits the game.", gameSetting.whiteName + " wins!", JOptionPane.YES_NO_OPTION);
							if (n == JOptionPane.YES_OPTION) {
								new GameWindow(gameSetting);
								gameWindow.dispose();
							} else gameWindow.dispose();
						}
					}
				}
			});
			this.timer.start();
		} else {
			wTime.setText("Untimed game");
			bTime.setText("Untimed game");
		}
		
		gameData.add(wTime);
		gameData.add(bTime);
		
		gameData.setPreferredSize(gameData.getMinimumSize());
		
		return gameData;
	}
	
	private JPanel buttons() {
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(1,3,10,0));
		
		final JButton quit = new JButton("Quit");
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int n = JOptionPane.showConfirmDialog(gameWindow, "Are you sure you want to quit?", "Confirm quit", JOptionPane.YES_NO_OPTION);
				if (n == JOptionPane.YES_OPTION) {
					if (timer != null) timer.stop();
					gameWindow.dispose();
				}
			}
		});
		
		final JButton newGame = new JButton("New Game");
		newGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int n = JOptionPane.showConfirmDialog(gameWindow, "Are you sure you want to begin a new game?", "Confirm new game", JOptionPane.YES_NO_OPTION);
				if (n == JOptionPane.YES_OPTION) {
					if (timer != null) timer.stop();
					SwingUtilities.invokeLater(new StartWindow());
					gameWindow.dispose();
				}
			}
		});
		
		final JButton instr = new JButton("How to play");
		instr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (gameSetting.isBlackAI) {
					if (gameSetting.isWhiteAI) {
						JOptionPane.showMessageDialog(gameWindow, "Sit back and relax and watch these two bots (" + gameSetting.whiteName + " vs. " + gameSetting.blackName + ") play each other. Either your here watching out of boredom (i can promise you 100 percent you won't know whats going on), or your actually testing different AIs.", "How to play", JOptionPane.PLAIN_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(gameWindow, "All I can say is good luck, your gonna need it to attempt to beat " + gameSetting.blackName + ". If you lose, don't fret; your supposed to lose. If you win, please contact me.", "How to play", JOptionPane.PLAIN_MESSAGE);
					}
				} else if (gameSetting.isWhiteAI) {
					JOptionPane.showMessageDialog(gameWindow, "All I can say is good luck, your gonna need it to attempt to beat " + gameSetting.whiteName + ". If you lose, don't fret; your supposed to lose. If you win, please contact me.", "How to play", JOptionPane.PLAIN_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(gameWindow, "Well well, how did you manage to play with a real person. You actually have friends??? If your here looking for a tutorial, then you'll be disappointed. Life's just not fair.", "How to play", JOptionPane.PLAIN_MESSAGE);
				}
			}
		});
		
		buttons.add(instr);
		buttons.add(newGame);
		buttons.add(quit);
		
		buttons.setPreferredSize(buttons.getMinimumSize());
		
		return buttons;
	}
	
	public void checkmateOccurred(String name) {
		if (name.equals(this.gameSetting.whiteName)) {
			if (this.timer != null) this.timer.stop();
			int n = 0;
			if (this.gameSetting.isWhiteAI) {
				if (this.gameSetting.isBlackAI) {
					n = JOptionPane.showConfirmDialog(this.gameWindow, "White wins by checkmate! Set up a new game? \n Choosing \"No\" lets you look at the final situation.\n\n Get this stupid bot named " + gameSetting.blackName + " outta here, they suck!", "White wins!", JOptionPane.YES_NO_OPTION);
				} else {
					n = JOptionPane.showConfirmDialog(this.gameWindow, "White wins by checkmate! Set up a new game? \n Choosing \"No\" lets you look at the final situation." + "\n\nOMG, its official. Your on your way to becoming a GRANDMASTER. Unless my bot is just trash, which it probably is. Please contact me, I need to make sure this doesn't happen again.", "White wins!", JOptionPane.YES_NO_OPTION);
				}
			} else if (this.gameSetting.isBlackAI) {
				n = JOptionPane.showConfirmDialog(this.gameWindow, "White wins by checkmate! Set up a new game? \n Choosing \"No\" lets you look at the final situation." + "\n\nOMG, its official. Your on your way to becoming a GRANDMASTER. Unless my bot is just trash, which it probably is. Please contact me, I need to make sure this doesn't happen again.", "White wins!", JOptionPane.YES_NO_OPTION);
			} else {
				n = JOptionPane.showConfirmDialog(this.gameWindow, "White wins by checkmate! Set up a new game? \n Choosing \"No\" lets you look at the final situation.", "White wins!", JOptionPane.YES_NO_OPTION);
			}
			if (n == JOptionPane.YES_OPTION) {
				SwingUtilities.invokeLater(new StartMenu());
				this.gameWindow.dispose();
			}
		} else {
			if (this.timer != null) timer.stop();
			int n = 0;
			if (this.gameSetting.isWhiteAI) {
				if (this.gameSetting.isBlackAI) {
					n = JOptionPane.showConfirmDialog(this.gameWindow, "Black wins by checkmate! Set up a new game? \n Choosing \"No\" lets you look at the final situation.\n\n Get this stupid bot named " + gameSetting.whiteName + " outta here, they suck!", "Black wins!", JOptionPane.YES_NO_OPTION);
				} else {
					n = JOptionPane.showConfirmDialog(this.gameWindow, "Black wins by checkmate! Set up a new game? \n Choosing \"No\" lets you look at the final situation." + "\n\nOMG, its official. Your on your way to becoming a GRANDMASTER. Unless my bot is just trash, which it probably is. Please contact me, I need to make sure this doesn't happen again.", "Black wins!", JOptionPane.YES_NO_OPTION);
				}
			} else if (this.gameSetting.isBlackAI) {
				n = JOptionPane.showConfirmDialog(this.gameWindow, "Black wins by checkmate! Set up a new game? \n Choosing \"No\" lets you look at the final situation." + "\n\nOMG, its official. Your on your way to becoming a GRANDMASTER. Unless my bot is just trash, which it probably is. Please contact me, I need to make sure this doesn't happen again.", "Black wins!", JOptionPane.YES_NO_OPTION);
			} else {
				n = JOptionPane.showConfirmDialog(this.gameWindow, "Black wins by checkmate! Set up a new game? \n Choosing \"No\" lets you look at the final situation.", "Black wins!", JOptionPane.YES_NO_OPTION);
			}
			if (n == JOptionPane.YES_OPTION) {
				SwingUtilities.invokeLater(new StartMenu());
				this.gameWindow.dispose();
			}
		}
	}
	
	public void stalemateOccurred() {
		if (this.timer != null) this.timer.stop();
		int n = 0;
		if (this.gameSetting.isWhiteAI) {
			if (this.gameSetting.isBlackAI) {
				n = JOptionPane.showConfirmDialog(this.gameWindow, "Draw by stalemate! Set up a new game? \n Choosing \"No\" lets you look at the final situation.\n\n Guess both bots just suck!", "Draw!", JOptionPane.YES_NO_OPTION);
			} else {
				n = JOptionPane.showConfirmDialog(this.gameWindow, "Draw by stalemate! Set up a new game? \n Choosing \"No\" lets you look at the final situation." + "\n\nOMG, its official. Your on your way to becoming a GRANDMASTER. Unless my bot is just trash, which it probably is. Please contact me, I need to make sure this doesn't happen again.", "Draw!", JOptionPane.YES_NO_OPTION);
			}
		} else if (this.gameSetting.isBlackAI) {
			n = JOptionPane.showConfirmDialog(this.gameWindow, "Draw by stalemate! Set up a new game? \n Choosing \"No\" lets you look at the final situation." + "\n\nOMG, its official. Your on your way to becoming a GRANDMASTER. Unless my bot is just trash, which it probably is. Please contact me, I need to make sure this doesn't happen again.", "Draw!", JOptionPane.YES_NO_OPTION);
		} else {
			n = JOptionPane.showConfirmDialog(this.gameWindow, "Draw by stalemate! Set up a new game? \n Choosing \"No\" lets you look at the final situation.", "Draw!", JOptionPane.YES_NO_OPTION);
		}
		if (n == JOptionPane.YES_OPTION) {
			SwingUtilities.invokeLater(new StartMenu());
			this.gameWindow.dispose();
		}
	}
	
}
