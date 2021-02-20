package Konsola;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;



public class AplikacjaTurysty extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;

	private static final String GREETING_MESSAGE = 
			"Program Person - wersja okienkowa\n" + 
	        "Autor: Mateusz Semper i Kamil Jedras\n" + 
			"Data:  styczeñ 2021 r.\n";
	int dzienPrzyjazdu, dzienWyjazdu, rokPrzyjazdu, rokWyjazdu, minCena, maxCena, liczbaOsob;
	String miasto, miesiacPrzyjazdu, miesiacWyjazdu;
	
	
	// Font dla etykiet o sta³ej szerokoœci znaków
	Font font = new Font("SERIF", Font.BOLD, 12);

	// Etykiety wyœwietlane na panelu w g³ównym oknie aplikacji
	JLabel miastoLabel = new JLabel("    Miasto: ");
	JLabel poczatekRezerwacjiLabel  = new JLabel("  Pocz¹tek rezerwacji: ");
	JLabel koniecRezerwacjiLabel    = new JLabel("  Koniec rezerwacji: ");
	JLabel cenaMinLabel      		= new JLabel("Min cena: ");
	JLabel cenaMaxLabel       		= new JLabel("Max cena: ");
	JLabel liczbaOsobLabel      	= new JLabel("Liczba osób: ");

	JTextField miastoField = new JTextField(10);
	JTextField minCenaField = new JTextField(10);
	JTextField maxCenaField = new JTextField(10);
	JTextField liczbaOsobField = new JTextField(10);
	
	String[] dniMiesiaca = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16",
			"17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
	String[] miesiace = {"Styczeñ","Luty","Marzec","Kwiecieñ","Maj","Czerwiec","Lipiec","Sierpieñ","Wrzesieñ","PaŸdziernik","Listopad","Grudzieñ"};
	
	JComboBox poczatekDzienComboBox = new JComboBox(dniMiesiaca);
	JComboBox koniecDzienComboBox = new JComboBox(dniMiesiaca);
	JComboBox poczatekMiesiacComboBox = new JComboBox(miesiace);
	JComboBox koniecMiesiacComboBox = new JComboBox(miesiace);
	JComboBox poczatekRokComboBox;
	JComboBox koniecRokComboBox;

	// Przyciski wyœwietlane na panelu w g³ównym oknie aplikacji
	JButton sprawdzTerminyButton    = new JButton("Sprawdz dostepne terminy");
	JButton aktywneRezerwacjeButton   = new JButton("Zobacz aktywne rezerwacje");
	JButton przeszleRezerwacjeButton   = new JButton("Zobacz przesz³e rezerwacje");
	JButton szukajTerminuButton = new JButton("Szukaj");
	JPanel panel = new JPanel();
	JPanel aktywne = new JPanel();
	JPanel przeszle = new JPanel();
	
	public AplikacjaTurysty(){
		
		String[] rok = new String[3]; 
		rok[0] = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
		rok[1] = String.valueOf(Calendar.getInstance().get(Calendar.YEAR)+1);
		rok[2] = String.valueOf(Calendar.getInstance().get(Calendar.YEAR)+2);
		poczatekRokComboBox = new JComboBox(rok);
		koniecRokComboBox = new JComboBox(rok);
		setTitle("Rezerwacja Hotelu");  
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(800, 720);
		setResizable(false);
		setLocationRelativeTo(null);
		miastoLabel.setBounds(335, 31, 56, 17);


		miastoLabel.setFont(font);
		koniecRezerwacjiLabel.setBounds(335, 6, 105, 17);
		koniecRezerwacjiLabel.setFont(font);
		cenaMaxLabel.setBounds(184, 31, 57, 17);
		cenaMaxLabel.setFont(font);
		liczbaOsobLabel.setBounds(488, 31, 69, 17);
		liczbaOsobLabel.setFont(font);



		szukajTerminuButton.addActionListener(this);
		sprawdzTerminyButton.addActionListener(this);
		aktywneRezerwacjeButton.addActionListener(this);
		przeszleRezerwacjeButton.addActionListener(this);


		getContentPane().setLayout(new BorderLayout()); 
		JPanel pasek = new JPanel();
		JToolBar glowneToolBar = new JToolBar();
		
		pasek.add(sprawdzTerminyButton);
		pasek.add(aktywneRezerwacjeButton);
		pasek.add(przeszleRezerwacjeButton);
		glowneToolBar.add(pasek);
		
		getContentPane().add(glowneToolBar, BorderLayout.NORTH);
		panel.setLayout(null);
		panel.add(miastoLabel);
		miastoField.setBounds(390, 30, 86, 20);
		panel.add(miastoField);
		poczatekRezerwacjiLabel.setBounds(21, 6, 115, 17);
		poczatekRezerwacjiLabel.setFont(font);
		panel.add(poczatekRezerwacjiLabel);
		poczatekDzienComboBox.setBounds(141, 5, 38, 20);
		panel.add(poczatekDzienComboBox);
		poczatekMiesiacComboBox.setBounds(184, 5, 81, 20);
		panel.add(poczatekMiesiacComboBox);
		poczatekRokComboBox.setBounds(270, 5, 50, 20);
		panel.add(poczatekRokComboBox);
		panel.add(koniecRezerwacjiLabel);
		koniecDzienComboBox.setBounds(446, 5, 38, 20);
		panel.add(koniecDzienComboBox);
		koniecMiesiacComboBox.setBounds(494, 5, 81, 20);
		panel.add(koniecMiesiacComboBox);
		koniecRokComboBox.setBounds(585, 5, 50, 20);
		panel.add(koniecRokComboBox);
		minCenaField.setBounds(86, 30, 86, 20);
		panel.add(minCenaField);
		cenaMinLabel.setBounds(21, 31, 55, 17);
		cenaMinLabel.setFont(font);
		panel.add(cenaMinLabel);
		panel.add(cenaMaxLabel);
		maxCenaField.setBounds(251, 30, 86, 20);
		panel.add(maxCenaField);
		panel.add(miastoLabel);
		panel.add(miastoField);
		panel.add(liczbaOsobLabel);
		liczbaOsobField.setBounds(564, 30, 86, 20);
		panel.add(liczbaOsobField);

		szukajTerminuButton.setBounds(314, 59, 89, 23);
		panel.add(szukajTerminuButton);
		getContentPane().add(panel, BorderLayout.CENTER); 
		
		setVisible(true);
	}

	
	@Override
	public void actionPerformed(ActionEvent event) {
		Object eventSource = event.getSource();

		try {
			if (eventSource == szukajTerminuButton) { 
				dzienPrzyjazdu = poczatekDzienComboBox.getItemCount();
				dzienWyjazdu = koniecDzienComboBox.getItemCount();
				rokPrzyjazdu = poczatekRokComboBox.getItemCount();
				rokWyjazdu = koniecRokComboBox.getItemCount();
				miasto = miastoField.getText();
				minCena = Integer.parseInt(minCenaField.getText());
				maxCena = Integer.parseInt(maxCenaField.getText());
				liczbaOsob = Integer.parseInt(liczbaOsobField.getText());
				miesiacPrzyjazdu = poczatekMiesiacComboBox.getName();
				miesiacWyjazdu = koniecMiesiacComboBox.getName();
				
			}
			if (eventSource == aktywneRezerwacjeButton) {
				panel.setVisible(false);
				przeszle.setVisible(false);
				aktywne.setVisible(true);
			}
			if (eventSource == przeszleRezerwacjeButton) {
				panel.setVisible(false);
				aktywne.setVisible(false);
				przeszle.setVisible(true);
			}
			if (eventSource == sprawdzTerminyButton) {
				przeszle.setVisible(false);
				aktywne.setVisible(false);
				panel.setVisible(true);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "B³¹d", JOptionPane.ERROR_MESSAGE);
		}
		
		// Aktualizacja zawartoœci wszystkich pól tekstowych.
		//showCurrentPerson();
	}	
} 



