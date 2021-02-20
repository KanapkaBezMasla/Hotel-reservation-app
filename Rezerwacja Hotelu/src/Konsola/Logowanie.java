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
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class Logowanie extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	private String haslo;
	private String login;
	//Uzytkownik user;
	
	JLabel loginLabel   	= new JLabel("Login: ");
	JLabel hasloLabel   	= new JLabel("Haslo: ");
	JTextField loginField   = new JTextField(10);
	JPasswordField hasloField   = new JPasswordField(10);
	
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
		
		loginLabel.setLabelFor(loginField);
		hasloLabel.setLabelFor(hasloField);
		
		
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
		//System.out.println(event.getID());

		try {
			if (eventSource == zalogujButton) {
				Aplikacja.turysta = turystaRadio.isSelected();
				Aplikacja.pracownik = pracownikRadio.isSelected();
				if (warunkiLogowania()) {
					PreparedStatement st;
					PreparedStatement st1;
					ResultSet rs;
					ResultSet rs1;
					
					login = loginField.getText();
					haslo = new String (hasloField.getPassword());	
					
					if (turystaRadio.isSelected() ) {
						
						String query = "SELECT * FROM login, client "
								+ "WHERE login.username = ? AND login.password = ? AND login.login_id = client.login_id";
						st = Aplikacja.getConnection().prepareStatement(query);
						st.setString(1, login);
						st.setString(2, haslo);
						rs = st.executeQuery();
						
						if(rs.next()) {
							Aplikacja.login = login;
							//logowanie turysty się udało, wyświtlić kolejny ekran
	                        System.out.println("Udało się zalogować");
	                        
	                        this.dispose();
	                        // int loginid = rs.getInt("login.login_id");
							System.out.println("Udało się zalogować");
							//user = new Uzytkownik(login, haslo, loginid);
							AplikacjaTurysty clientForm = new AplikacjaTurysty();
	                        clientForm.setVisible(true);
	                        //clientForm.pack();
	                        clientForm.setLocationRelativeTo(null);
							
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
							Aplikacja.login = login;
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
	
	boolean warunkiLogowania() {
		//boolean warunki = false;
		if (new String (hasloField.getPassword()) == "") return false;
		//System.out.println("a");
        if (loginField.getText() == "") return false;
		//System.out.println("b");
        if (!(Aplikacja.pracownik ^ Aplikacja.turysta)) return false;
		//System.out.println("c");
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
