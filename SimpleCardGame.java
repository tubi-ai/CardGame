package com.company;

//package com.company;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.Component;
import java.awt.Font;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

public class SimpleCardGame extends JFrame {
    private JLabel jlb1;
    private JRadioButton radio[], radio1[];
    private JComboBox cardComboBox;
    private JLabel scoreLabel;
    private JLabel resultLabel;
    private JLabel playerCardLabel;
    private JLabel computerCardLabel;
    private int score = 0;
    private ButtonGroup suitGroup;
    private JButton playButton;
    private JButton resetButton;
    private JButton exitButton;
    private final String[] deck = {"ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen", "king"};
    private final String[] suits = {"C", "S", "D", "H"}; // C: Club, S: Spade, D: Diamond, H: Heart
    private final String[] suitNames = {"Club", "Spade", "Diamond", "Heart"};

    public SimpleCardGame() {
        super("Card Game");
        setLayout(new BorderLayout(10, 10));
        initializeComponents();
        setDefaultCards();
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void initializeComponents() {
        // Left panel - for controls
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Score panel
        JPanel scorePanel = new JPanel();
        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 16));
        scorePanel.add(scoreLabel);
        leftPanel.add(scorePanel);
        leftPanel.add(Box.createVerticalStrut(20));

        // Card selection panel
        JPanel cardPanel = new JPanel();
        cardComboBox = new JComboBox<>(deck);
        cardPanel.add(new JLabel("Select Card: "));
        cardPanel.add(cardComboBox);
        leftPanel.add(cardPanel);
        leftPanel.add(Box.createVerticalStrut(20));

        // Suit selection panel
        JPanel suitPanel = new JPanel();
        suitPanel.setBorder(BorderFactory.createTitledBorder("Select Suit"));
        suitGroup = new ButtonGroup();
        
        for (int i = 0; i < suitNames.length; i++) {
            JRadioButton radio = new JRadioButton(suitNames[i]);
            suitGroup.add(radio);
            suitPanel.add(radio);
        }
        leftPanel.add(suitPanel);
        leftPanel.add(Box.createVerticalStrut(20));

        // Play button
        JPanel buttonPanel = new JPanel();
        playButton = new JButton("Play");
        playButton.setFont(new Font("Arial", Font.BOLD, 14));
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playRound();
                
            }
        });
        buttonPanel.add(playButton);
        // reset button
        resetButton = new JButton("Reset");
        resetButton.setFont(new Font("Arial", Font.BOLD, 14));
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetGame();
                
            }
        });
        buttonPanel.add(resetButton);
        // exit button
        exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Arial", Font.BOLD, 14));
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	System.exit(0);
                
            }
        });
        buttonPanel.add(exitButton);
        leftPanel.add(buttonPanel);

        // Right panel - for card images
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Player card section
        JPanel playerSection = new JPanel();
        playerSection.setLayout(new BoxLayout(playerSection, BoxLayout.Y_AXIS));
        JLabel playerTitle = new JLabel("Your Card:");
        playerTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        playerSection.add(playerTitle);
        playerCardLabel = new JLabel();
        playerCardLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        playerSection.add(playerCardLabel);
        rightPanel.add(playerSection);
        rightPanel.add(Box.createVerticalStrut(30));

        // Computer card section
        JPanel computerSection = new JPanel();
        computerSection.setLayout(new BoxLayout(computerSection, BoxLayout.Y_AXIS));
        JLabel computerTitle = new JLabel("Computer's Card:");
        computerTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        computerSection.add(computerTitle);
        computerCardLabel = new JLabel();
        computerCardLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        computerSection.add(computerCardLabel);
        rightPanel.add(computerSection);

        // Add to main panel
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);
    }
    private void setDefaultCards() {
        // Player's card
        ImageIcon playerDefaultCard = loadCardImage("back.jpg");
        playerCardLabel.setIcon(resizeIcon(playerDefaultCard, 200, 300));

        // PC's card
        ImageIcon computerDefaultCard = loadCardImage("back.jpg");
        computerCardLabel.setIcon(resizeIcon(computerDefaultCard, 200, 300));
    }

    private void playRound() {
    	
        String selectedCard = (String) cardComboBox.getSelectedItem();
        String selectedSuit = getSelectedSuit();

        if (selectedSuit == null) {
            JOptionPane.showMessageDialog(this, "Please select a suit!");
            return;
        }

        // Upload image of selected card
        String selectedSuitCode = getSuitCode(selectedSuit);
        String playerCardFilename = selectedCard.toLowerCase() + selectedSuitCode.toLowerCase() + ".jpg";
        ImageIcon playerCardImage = loadCardImage(playerCardFilename);
        playerCardLabel.setIcon(resizeIcon(playerCardImage, 200, 300));

        // Choose a random card and upload your image
        String randomCard = deck[new Random().nextInt(deck.length)];
        String randomSuit = suitNames[new Random().nextInt(suitNames.length)];
        String randomSuitCode = getSuitCode(randomSuit);
        String computerCardFilename = randomCard.toLowerCase() + randomSuitCode.toLowerCase() + ".jpg";
        ImageIcon computerCardImage = loadCardImage(computerCardFilename);
        computerCardLabel.setIcon(resizeIcon(computerCardImage, 200, 300));

        // Calculate score
        int roundScore = calculateScore(selectedCard, selectedSuit, randomCard, randomSuit);
        score += roundScore;
        scoreLabel.setText("Score: " + score);

        // Check win
        if (score >= 21) {
            int choice = JOptionPane.showConfirmDialog(this, 
                "Congratulations! You won! Would you like to play again?",
                "Game Over", 
                JOptionPane.YES_NO_OPTION);
            
            if (choice == JOptionPane.YES_OPTION) {
                resetGame();
            } else {
                System.exit(0);
            }
        }
    }

    private ImageIcon loadCardImage(String filename) {
        try {
            return new ImageIcon(getClass().getResource("/com/company/images/" + filename));
        } catch (Exception e) {
            System.out.println("Error loading image: " + filename);
            return new ImageIcon(); // Return empty icon if not found
        }
    }

    private ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        if (icon.getImage() == null) return icon;
        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImg);
    }

    private String getSuitCode(String suitName) {
        switch (suitName) {
            case "Club": return "C";
            case "Spade": return "S";
            case "Diamond": return "D";
            case "Heart": return "H";
            default: return "";
        }
    }

    private String getSelectedSuit() {
        Enumeration<AbstractButton> buttons = suitGroup.getElements();
        while (buttons.hasMoreElements()) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                return button.getText();
            }
        }
        return null;
    }

    private int calculateScore(String selectedCard, String selectedSuit, 
                             String randomCard, String randomSuit) {
        if (selectedCard.equals(randomCard) && selectedSuit.equals(randomSuit)) {
            return 10;
        } else if (selectedCard.equals(randomCard)) {
            return 5;
        } else if (selectedSuit.equals(randomSuit)) {
            return 2;
        }
        return -5;
    }

    private void resetGame() {
        score = 0;
        scoreLabel.setText("Score: 0");
        setDefaultCards();
        suitGroup.clearSelection();  // Clear radio button selection
        cardComboBox.setSelectedIndex(0);  // Reset card selection
        
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SimpleCardGame().setVisible(true);
            }
        });
    }
}