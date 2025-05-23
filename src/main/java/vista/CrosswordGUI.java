package vista;
import javax.swing.*;

import business.CrosswordGenerator;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class CrosswordGUI extends JFrame {
    private JTextField sizeField;
    private JTextArea wordArea;
    private JButton generateButton;
    private JPanel boardPanel;

    public CrosswordGUI() {
        setTitle("Generador de Crucigrama");
        setSize(700, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Tamaño (10-20):"));
        sizeField = new JTextField(3);
        inputPanel.add(sizeField);

        inputPanel.add(new JLabel("Palabras (una por línea):"));
        wordArea = new JTextArea(5, 20);
        JScrollPane scrollPane = new JScrollPane(wordArea);
        inputPanel.add(scrollPane);

        topPanel.add(inputPanel);

        generateButton = new JButton("Generar Crucigrama");
        generateButton.addActionListener(e -> generateCrossword());
        topPanel.add(generateButton);

        add(topPanel, BorderLayout.NORTH);

        boardPanel = new JPanel();
        add(boardPanel, BorderLayout.CENTER);

        setVisible(true);
    }
     private void generateCrossword() {
        boardPanel.removeAll();
        int size;

        try {
            size = Integer.parseInt(sizeField.getText());
            if (size < 10 || size > 20) {
                JOptionPane.showMessageDialog(this, "El tamaño debe estar entre 10 y 20.");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Tamaño inválido. Introduce un número entre 10 y 20.");
            return;
        }

        List<String> words = Arrays.asList(wordArea.getText().split("\\s+"));
        List<String> validWords = new ArrayList<>();

        for (String word : words) {
            word = word.trim().toLowerCase();
            if (!word.isEmpty()) {
                if (word.length() < 3 || word.length() > 10) {
                    JOptionPane.showMessageDialog(this, "La palabra '" + word + "' no cumple con la longitud (3-10).");
                    return;
                }
                validWords.add(word);
            }
        }

        if (validWords.size() < 4) {
            JOptionPane.showMessageDialog(this, "Debe ingresar al menos 4 palabras.");
            return;
        }

        CrosswordGenerator generator = new CrosswordGenerator(size);
        List<String> notPlaced = generator.generateFlexibleCrossword(validWords);
        char[][] board = generator.getBoard();

        boardPanel.setLayout(new GridLayout(size, size));
        boardPanel.setBackground(new Color(191, 191, 191)); // fondo gris claro

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                char ch = board[i][j];
                JLabel label = new JLabel("", SwingConstants.CENTER);
                label.setOpaque(true);
                label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                label.setFont(new Font("Monospaced", Font.BOLD, 16));

                if (ch == ' ') {
                    label.setBackground(new Color(191, 191, 191)); // fondo vacío
                } else {
                    label.setText(String.valueOf(ch));
                    label.setBackground(Color.WHITE); // fondo con letra
                    label.setForeground(Color.BLACK);
                }

                boardPanel.add(label);
            }
        }

        if (!notPlaced.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se pudieron colocar las siguientes palabras:\n" + String.join(", ", notPlaced));
        }

        boardPanel.revalidate();
        boardPanel.repaint();
    }
}


