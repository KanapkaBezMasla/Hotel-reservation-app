package Konsola;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;

public class AplikacjaPracownika extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;

	Font font = new Font("SERIF", Font.BOLD, 12);

	JButton wyszukajRezerwacjeButton = new JButton("wyszukaj rezerwacje");
	JButton wyszukajPokojeButton = new JButton("wyszukaj pokoje");
	JToolBar glownyToolBar = new  JToolBar();
	JPanel panel = new JPanel();
	
	public AplikacjaPracownika() {
		setTitle("Logowanie");  
		Aplikacja.zalogowanoPoprawnie = false;
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(607, 573);
		setResizable(false);
		setLocationRelativeTo(null);

		wyszukajRezerwacjeButton.setFont(font);
		wyszukajPokojeButton.setFont(font);


		wyszukajRezerwacjeButton.addActionListener(this);
		wyszukajPokojeButton.addActionListener(this);

		panel.add(wyszukajRezerwacjeButton);
		panel.add(wyszukajPokojeButton);
		glownyToolBar.add(panel);
		getContentPane().add(glownyToolBar, BorderLayout.NORTH);
		
		setVisible(true);
	}



	@Override
	public void actionPerformed(ActionEvent event) {
		Object eventSource = event.getSource();
		try {
			if(eventSource==wyszukajRezerwacjeButton) {
				
			}
			if(eventSource==wyszukajPokojeButton) {
				
			}
		}catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "B³¹d", JOptionPane.ERROR_MESSAGE);
		}
		
	}
}
