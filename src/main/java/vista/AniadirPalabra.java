package vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

public class AniadirPalabra extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AniadirPalabra frame = new AniadirPalabra();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AniadirPalabra() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 300, 200);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel titulo = new JLabel("AÑADIR PALABRA Al CRUCIGRAMA");
		titulo.setBounds(62, 11, 181, 14);
		contentPane.add(titulo);
		
		JTextField palabraAgregada = new JTextField();
		palabraAgregada.setBounds(132, 63, 111, 20);
		palabraAgregada.setColumns(10);
		contentPane.add(palabraAgregada);
		
		JButton btnNewButton = new JButton("AÑADIR");
		btnNewButton.setBounds(170, 127, 89, 23);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("CANCELAR");
		btnNewButton_1.setBounds(32, 127, 89, 23);
		contentPane.add(btnNewButton_1);
		
		JLabel lblNewLabel = new JLabel("Escriba la palabra:");
		lblNewLabel.setBounds(32, 66, 101, 14);
		contentPane.add(lblNewLabel);
		
		
	}
}
