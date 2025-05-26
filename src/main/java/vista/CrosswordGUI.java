package vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import business.CrosswordGenerator;
import model.Crossword;
import java.awt.*;
import java.util.List;

public class CrosswordGUI extends JFrame {
	private JPanel contentPane;
	private JButton generateButton;
	private JPanel boardPanel;
	private JPanel bottomPanel;
	private JTextArea textArea;
	private JScrollPane descriptionScroll;

	public CrosswordGUI(Crossword crossword, int size) {
		setTitle("Generador de Crucigrama");
		setBounds(100, 100, 800, 1000);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		generateButton = new JButton("Generar Crucigrama");
		generateButton.setBounds(250, 250, 160, 23);
		generateButton.addActionListener(e -> generateCrossword(crossword, size));
		contentPane.add(generateButton);

		boardPanel = new JPanel();
		boardPanel.setBounds(0, 0, 700, 600);
		contentPane.add(boardPanel);

		// parte inferior
		bottomPanel = new JPanel();

		bottomPanel.setBounds(0, 600, 700, 200);
		contentPane.add(bottomPanel);

		setVisible(true);

		textArea = new JTextArea();
		textArea.setBounds(0, 0, 700, 180);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);

		descriptionScroll = new JScrollPane();
		descriptionScroll.setBounds(0, 0, 700, 180);

		descriptionScroll.add(textArea);
		descriptionScroll.setViewportView(textArea);
		bottomPanel.add(descriptionScroll);
	}

	private void generateCrossword(Crossword crossword, int size) {
		List<String> validWords = crossword.getWords();

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
			JOptionPane.showMessageDialog(this,
					"No se pudieron colocar las siguientes palabras:\n" + String.join(", ", notPlaced));
		}
		generateDescriptionArea(generator.getHorizontalWords(), generator.getVerticalWords(), crossword);
		generateButton.setVisible(false);
		boardPanel.revalidate();
		boardPanel.repaint();

	}

	public void generateDescriptionArea(List<String> horizontal, List<String> vertical, Crossword crossword) {
		String description = "Palabras Horizontales \n";
		int contador = 1;
		for (String string : horizontal) {
			description = description + contador + ".- "
					+ crossword.getDescription(string) + "\n";
			contador++;

		}
		contador = 1;
		description += "Palabras Verticales \n";
		
		for (String string : vertical) {
			description = description + contador + ".- "
					+ crossword.getDescription(string) + "\n";
			contador++;

		}
		textArea.setText(description);
	}
}
