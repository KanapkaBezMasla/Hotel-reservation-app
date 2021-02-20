package Konsola;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class Logowanie extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	private String haslo;
	private String login;
	
	
	
	
	JLabel loginLabel   	= new JLabel("Login: ");
	JLabel hasloLabel   	= new JLabel("Haslo: ");
	JTextField loginField   = new JTextField(10);
	JTextField hasloField   = new JTextField(10);
	
	JRadioButton turystaRadio = new JRadioButton("turysta");
	JRadioButton pracownikRadio = new JRadioButton("pracownik");
	
	JButton zalogujButton    = new JButton("Zaloguj");
	JButton registerButton   = new JButton("Zarejestruj się");

	
	Font font = new Font("SERIF", Font.BOLD, 12);

	
	public Logowanie() {
		setTitle("Logowanie");  
		Aplikacja.zalogowanoPoprawnie = false;
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(180, 200);
		setResizable(false);
		setLocationRelativeTo(null);

		loginLabel.setFont(font);
		hasloLabel.setFont(font);
		loginField.setFont(font);
		hasloField.setFont(font);

		zalogujButton.addActionListener(this);
		registerButton.addActionListener(this);
		JPanel panel = new JPanel();

		panel.add(loginLabel);
		panel.add(loginField);
		panel.add(hasloLabel);
		panel.add(hasloField);
		panel.add(zalogujButton);
		panel.add(registerButton);
		panel.add(turystaRadio);
		panel.add(pracownikRadio);
		
		setContentPane(panel);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		Object eventSource = event.getSource();

		try {
			if (eventSource == zalogujButton) {
				System.out.println("jesteśmy przed ifie");
				Aplikacja.turysta = turystaRadio.isSelected();
				Aplikacja.pracownik = pracownikRadio.isSelected();
				if (warunkiLogowania()) {
					PreparedStatement st;
					PreparedStatement st1;
					ResultSet rs;
					ResultSet rs1;
					
					login = loginField.getText();
					haslo = hasloField.getText();
					System.out.println("jesteśmy w ifie");	
					
					if (turystaRadio.isSelected() ) {
						
						String query = "SELECT * FROM login, client "
								+ "WHERE login.username = ? AND login.password = ? AND login.login_id = client.login_id";
						st = Aplikacja.getConnection().prepareStatement(query);
						st.setString(1, login);
						st.setString(2, haslo);
						rs = st.executeQuery();
						
						if(rs.next()) {
							
							//logowanie turysty się udało, wyświtlić kolejny ekran
	                        System.out.println("Udało się zalogować");
	                        AplikacjaTurysty clientForm = new AplikacjaTurysty();
	                        clientForm.setVisible(true);
	                        //clientForm.pack();
	                        clientForm.setLocationRelativeTo(null);
	                        this.dispose();
							System.out.println("Udało się zalogować");
							
						} else {
							
							//błąd logowania
							System.out.println("Nie udało się zalogować");
						}
						
					}
					
					if (pracownikRadio.isSelected()) {
						
						String query1 = "SELECT * FROM employee, login "
								+ "WHERE employee.login_id = login.login_id AND login.username = ? AND login.password = ?";
						st1 = Aplikacja.getConnection().prepareStatement(query1);
						st1.setString(1, login);
						st1.setString(2, haslo);
						rs1 = st1.executeQuery();
						
						if(rs1.next()) {
							
							AplikacjaPracownika workerForm = new AplikacjaPracownika();
	                        workerForm.setVisible(true);
	                        //workerForm.pack();
	                        workerForm.setLocationRelativeTo(null);
	                        this.dispose();
							System.out.println("Udało się zalogować");
							
						} else {
							
							//błąd logowania
							System.out.println("Nie udało się zalogować");
						}
						
					}
					/*
					System.out.println(Aplikacja.login);
					if(warunkiLogowania()) {
						Aplikacja.zalogowanoPoprawnie = true;
						dispose();
					} */
			} 
				else {
					System.out.println("Nie spełniono warunków logowania.");
				}
			
			}
			
			if (eventSource == registerButton) {
				
				Rejestracja form = new Rejestracja();
				form.setVisible(true);
				//form.pack();
				form.setLocationRelativeTo(null);
				this.dispose();
				
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	private boolean warunkiLogowania() {
		boolean warunki = false;
		if (hasloField.getText() == "") return false;
        if (loginField.getText() == "") return false;
        if (!(Aplikacja.pracownik ^ Aplikacja.turysta)) return false;
        return true;
        /*
		warunki = Aplikacja.pracownik ^ Aplikacja.turysta;
		if(Aplikacja.login=="")
			warunki = false;
		if(haslo == null)
			warunki = false;
		
		return warunki;*/
	}
}
