package Konsola;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Properties;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;



public class AplikacjaTurysty extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;

	int dzienPrzyjazdu, dzienWyjazdu, rokPrzyjazdu, rokWyjazdu, minCena, maxCena, liczbaOsob;
	String miasto, miesiacPrzyjazdu, miesiacWyjazdu;
	
	// Font dla etykiet o stalej szerokosci znakow
	private Font font = new Font("SERIF", Font.BOLD, 12);
	//Font fontDUZE = new Font("SERIF", Font.BOLD, 30);

	// Etykiety wyswietlane na panelu w glownym oknie aplikacji
	private JLabel miastoLabel = new JLabel("    Miasto: ");
	private JLabel poczatekRezerwacjiLabel  = new JLabel("  Poczatek rezerwacji: ");
	private JLabel koniecRezerwacjiLabel    = new JLabel("  Koniec rezerwacji: ");
	private JLabel cenaMinLabel      		= new JLabel("Min cena: ");
	private JLabel cenaMaxLabel       		= new JLabel("Max cena: ");
	private JLabel liczbaOsobLabel      	= new JLabel("Liczba osob: ");
	
	private String dataPoczatek;
	private String dataKoniec;

	private JTextField miastoField = new JTextField(10);
	private JTextField minCenaField = new JTextField(10);
	private JTextField maxCenaField = new JTextField(10);
	private JTextField liczbaOsobField = new JTextField(10);
	public JFrame frame;
	private static JTable table, table2, tableActive;
	
	DefaultTableModel model = new DefaultTableModel();
	DefaultTableModel model2 = new DefaultTableModel();
	DefaultTableModel model3 = new DefaultTableModel();

	String[] columnNames = {"Nazwa hotelu", "Cena za noc", "Miasto", "L. os.", "Room_ID", "Pocz. rezerwacji", "Kon. rezerwacji"};
	String[] columnNames2 = {"Nazwa hotelu", "Cena za noc", "Miasto", "L. os.", "Pocz. rezerwacji", "Kon. rezerwacji"};
	String[] dniMiesiaca = {"01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16",
			"17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
	String[] miesiace = {"01","02","03","04","05","06","07","08","09","10","11","12"};
	//"Styczel","Luty","Marzec","Kwiecien","Maj","Czerwiec","Lipiec","Sierpien","Wrzesien","Pazdziernik","Listopad","Grudzien"
	JComboBox<String> poczatekDzienComboBox = new JComboBox<>(dniMiesiaca);
	JComboBox<String> koniecDzienComboBox = new JComboBox<>(dniMiesiaca);
	JComboBox<String> poczatekMiesiacComboBox = new JComboBox<>(miesiace);
	JComboBox<String> koniecMiesiacComboBox = new JComboBox<>(miesiace);
	JComboBox<String> poczatekRokComboBox;
	JComboBox<String> koniecRokComboBox;
//	DefaultTableModel model;
//	JTable table = new JTable( model );
//	JScrollPane scrollPane = new JScrollPane( table );

	// Przyciski wylwietlane na panelu w glownym oknie aplikacji
	private JButton sprawdzTerminyButton    = new JButton("Sprawdz dostepne terminy");
	private JButton aktywneRezerwacjeButton   = new JButton("Zobacz aktywne rezerwacje");
	private JButton przeszleRezerwacjeButton   = new JButton("Zobacz przeszle rezerwacje");
	private JButton szukajTerminuButton = new JButton("Szukaj");
	private JButton rezerwacja = new JButton("Rezerwuj");
	private JButton anulujButton = new JButton("Anuluj");
	
	private JPanel panel = new JPanel();
	private JPanel tabela = new JPanel();
	private JPanel tabelaPrzeszle = new JPanel();
	private JPanel tabelaAktywne = new JPanel();
	private JPanel aktywne = new JPanel();
	//JPanel przeszle = new JPanel();
	
	public AplikacjaTurysty() throws SQLException{
		
		Properties p = new Properties();
		p.put("text.year", "Year");
		p.put("text.month", "Month");
		p.put("text.day", "Day");
		
		
		
		String[] rok = new String[3]; 
		rok[0] = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
		rok[1] = String.valueOf(Calendar.getInstance().get(Calendar.YEAR)+1);
		rok[2] = String.valueOf(Calendar.getInstance().get(Calendar.YEAR)+2);
		poczatekRokComboBox = new JComboBox<>(rok);
		koniecRokComboBox = new JComboBox<>(rok);
		setTitle("Rezerwacja Hotelu");  
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(700, 720);
		setResizable(false);
		setLocationRelativeTo(null);
		miastoLabel.setBounds(335, 31, 56, 17);

		//tablice////////////////////////////////////////////////////////////////////////////////////
		model.setColumnIdentifiers(columnNames);
		model2.setColumnIdentifiers(columnNames2);
		model3.setColumnIdentifiers(columnNames2);
		
		table = new JTable();
		table.setModel(model); 
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setFillsViewportHeight(true);
		JScrollPane scroll = new JScrollPane(table);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); 
		
		table2 = new JTable();
		table2.setModel(model2); 
		table2.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table2.setFillsViewportHeight(true);
		JScrollPane scroll2 = new JScrollPane(table2);
		scroll2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		tableActive = new JTable();
		tableActive.setModel(model3); 
		tableActive.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tableActive.setFillsViewportHeight(true);
		JScrollPane scroll3 = new JScrollPane(tableActive);
		scroll3.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll3.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		///////////////////////////////////////////////////////////////////////////////////////////////
		miastoLabel.setFont(font);
		koniecRezerwacjiLabel.setBounds(335, 6, 150, 17);
		koniecRezerwacjiLabel.setFont(font);
		cenaMaxLabel.setBounds(184, 31, 57, 17);
		cenaMaxLabel.setFont(font);
		liczbaOsobLabel.setBounds(488, 31, 70, 17);
		liczbaOsobLabel.setFont(font);



		szukajTerminuButton.addActionListener(this);
		sprawdzTerminyButton.addActionListener(this);
		aktywneRezerwacjeButton.addActionListener(this);
		przeszleRezerwacjeButton.addActionListener(this);
		rezerwacja.addActionListener(this);
		anulujButton.addActionListener(this);


		getContentPane().setLayout(new BorderLayout()); 
		JPanel pasek = new JPanel();
		JToolBar glowneToolBar = new JToolBar();
		
		pasek.add(sprawdzTerminyButton);
		pasek.add(aktywneRezerwacjeButton);
		pasek.add(przeszleRezerwacjeButton);
		glowneToolBar.add(pasek);
		
		getContentPane().add(glowneToolBar, BorderLayout.NORTH);
//		getContentPane().add(scrollPane);
		panel.setLayout(null);
		panel.add(miastoLabel);
		miastoField.setBounds(390, 30, 86, 20);
		miastoField.setText("Warsaw");
		panel.add(miastoField);
		poczatekRezerwacjiLabel.setBounds(19, 6, 150, 17);
		poczatekRezerwacjiLabel.setFont(font);
		panel.add(poczatekRezerwacjiLabel);
		poczatekDzienComboBox.setBounds(141, 5, 50, 20);
		panel.add(poczatekDzienComboBox);
		poczatekMiesiacComboBox.setBounds(200, 5, 50, 20);
		panel.add(poczatekMiesiacComboBox);
		poczatekRokComboBox.setBounds(259, 5, 70, 20);
		panel.add(poczatekRokComboBox);
		//panel.add(datePickerBegin);
		panel.add(koniecRezerwacjiLabel);
		koniecDzienComboBox.setBounds(446, 5, 50, 20);
		panel.add(koniecDzienComboBox);
		koniecMiesiacComboBox.setBounds(505, 5, 50, 20);
		panel.add(koniecMiesiacComboBox);
		koniecRokComboBox.setBounds(564, 5, 70, 20);
		panel.add(koniecRokComboBox);
		//panel.add(datePickerEnd);
		
		minCenaField.setBounds(86, 30, 86, 20);
		minCenaField.setText("0");
		panel.add(minCenaField);
		cenaMinLabel.setBounds(21, 31, 57, 17);
		cenaMinLabel.setFont(font);
		//cenaMinLabel.setLabelFor(minCenaField);
		panel.add(cenaMinLabel);
		//cenaMaxLabel.setLabelFor(maxCenaField);
		panel.add(cenaMaxLabel);
		maxCenaField.setBounds(251, 30, 86, 20);
		maxCenaField.setText("1000");
		panel.add(maxCenaField);
		//miastoLabel.setLabelFor(miastoField);
		panel.add(miastoLabel);
		panel.add(miastoField);
		//liczbaOsobLabel.setLabelFor(liczbaOsobField);
		panel.add(liczbaOsobLabel);
		liczbaOsobField.setBounds(564, 30, 86, 20);
		liczbaOsobField.setText("4");
		panel.add(liczbaOsobField);
		table.setFillsViewportHeight(true);
		

		szukajTerminuButton.setBounds(314, 59, 89, 23);
		panel.add(szukajTerminuButton);
		rezerwacja.setBounds(314, 90, 89, 23);
		panel.add(rezerwacja);
		
		tabela.add(scroll, BorderLayout.CENTER);
		tabelaPrzeszle.add(scroll2, BorderLayout.CENTER);
		tabelaAktywne.add(scroll3, BorderLayout.CENTER);
		getContentPane().add(panel, BorderLayout.CENTER); 
		getContentPane().add(tabela, BorderLayout.SOUTH);
		
		aktywne.setLayout(null);
		aktywne.add(anulujButton);
		anulujButton.setBounds(290, 5, 100, 40);
		///////////////////////////////////////////////////////////////////////////////
	/*	JLabel miastoLabel0 = 	new JLabel("Miasto                            ");
		JLabel hotelLabel0 = 	new JLabel("Hotel         ");
		JLabel peopleLabel0 = 	new JLabel("Liczba osob      ");
		JLabel beginDateLabel0 = new JLabel("Poczatek pobytu                    ");
		JLabel endDateLabel0 = 	new JLabel("Koniec pobytu           ");
		
		Box box0 = Box.createHorizontalBox();
		box0 = new Box(BoxLayout.X_AXIS);
		
		box0.add(miastoLabel0);
		box0.add(hotelLabel0);
		box0.add(peopleLabel0);
		box0.add(beginDateLabel0);
		box0.add(endDateLabel0);	
		przeszle.add(box0);
		
		miastoLabel0.setFont(font);
		hotelLabel0.setFont(font);
		peopleLabel0.setFont(font);
		beginDateLabel0.setFont(font);
		endDateLabel0.setFont(font);
		// wyborczyk
		// demokracja_kosztuje
		
		//String query1 = "SELECT town, hotel_name, people_number,  begin_date, end_date"
		//		+ "FROM reservation NATURAL JOIN room NATURAL JOIN hotel NATURAL JOIN address NATURAL JOIN client NATURAL JOIN login "
		//		+ "WHERE login.username = ?;";
		String query1 = "call getClientReservations(?);";
		
		PreparedStatement sel_rev =  Aplikacja.getConnection().prepareStatement(query1);
		sel_rev.setString(1, Aplikacja.login);
		ResultSet rs1 = sel_rev.executeQuery();	
		while(rs1.next()) {	
			JLabel miastoLabel2 = 	new JLabel(rs1.getString(1) + "  ");
			JLabel hotelLabel = 	new JLabel(rs1.getString(2)+ "  ");
			JLabel peopleLabel = 	new JLabel(rs1.getString(3)+ "  ");
			JLabel beginDateLabel = new JLabel(rs1.getString(4)+ "  ");
			JLabel endDateLabel = 	new JLabel(rs1.getString(5)+ "  ");
			String endDate = rs1.getString(5); 
			LocalDateTime now = LocalDateTime.now(); 
			//System.out.println(Integer.valueOf(endDate.substring(0, 4)));
			//System.out.println(Integer.valueOf(endDate.substring(5, 7)));
			LocalDateTime reservationDate = LocalDateTime.of(Integer.valueOf(endDate.substring(0, 4)),
					Integer.valueOf(endDate.substring(5, 7)), Integer.valueOf(endDate.substring(8, 10)), 12, 0);
			//System.out.println(now.getDayOfMonth()); 
	/*		if(reservationDate.isBefore(now)){
				Box box = Box.createHorizontalBox();
				box = new Box(BoxLayout.X_AXIS);
				
				box.add(miastoLabel2);
				box.add(hotelLabel);
				box.add(peopleLabel);
				box.add(beginDateLabel);
				box.add(endDateLabel);	
				przeszle.add(box);
				
				miastoLabel2.setFont(fontDUZE);
				hotelLabel.setFont(fontDUZE);
				peopleLabel.setFont(fontDUZE);
				beginDateLabel.setFont(fontDUZE);
				endDateLabel.setFont(fontDUZE);
			}
			//showTablePastData();
			
		} */
		
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
				
				String rokPoczatek = (String)poczatekRokComboBox.getSelectedItem();
				String miesiacPoczatek = (String)poczatekMiesiacComboBox.getSelectedItem();
				String dzienPoczatek = (String)poczatekDzienComboBox.getSelectedItem();
				
				dataPoczatek = rokPoczatek+"-"+miesiacPoczatek+"-"+dzienPoczatek;
				
				LocalDateTime now = LocalDateTime.now(); 
				LocalDateTime dataPoczatekLocalDataTime = LocalDateTime.of(Integer.valueOf(dataPoczatek.substring(0, 4)),
						Integer.valueOf(dataPoczatek.substring(5, 7)), Integer.valueOf(dataPoczatek.substring(8, 10)), 23, 0);
				if(dataPoczatekLocalDataTime.isBefore(now)){
					JOptionPane.showMessageDialog(this, "Prosze wybrac poprawny termin!");
					return;
				}
				model.setRowCount(0);
				String rokKoniec = (String)koniecRokComboBox.getSelectedItem();
				String miesiacKoniec = (String)koniecMiesiacComboBox.getSelectedItem();
				String dzienKoniec = (String)koniecDzienComboBox.getSelectedItem();
				
				dataKoniec = rokKoniec+"-"+miesiacKoniec+"-"+dzienKoniec;
				
				System.out.println("przed showtable");
				showTableData();
				
			}
			if (eventSource == aktywneRezerwacjeButton) {
				panel.setVisible(false);
				tabela.setVisible(false);
				//przeszle.setVisible(false);
				tabelaPrzeszle.setVisible(false);
				
				getContentPane().add(aktywne, BorderLayout.CENTER);
				getContentPane().add(tabelaAktywne, BorderLayout.SOUTH);
				tabelaAktywne.setVisible(true);
				aktywne.setVisible(true);
				showTableActiveData();
			}
			if (eventSource == anulujButton) {
				int row = tableActive.getSelectedRow();
				if (row >= 0) {
					Integer a = 0, b = 1, c = 2, d =3, e =4, f =5;
					String hot_n  = tableActive.getValueAt(row, a).toString(); 
					String pric  =  tableActive.getValueAt(row, b).toString(); 
					String city  =  tableActive.getValueAt(row, c).toString();
					String nop  =  tableActive.getValueAt(row, d).toString();
					int row2 = tableActive.getSelectedRow();
					String begD  =  tableActive.getValueAt(row2, e).toString();
					String endD  =  tableActive.getValueAt(row2, f).toString();
					System.out.println("dkow");
					int clientid = getClientId(Aplikacja.login);
					System.out.println("Tu dziala");
					String query = "CALL findReservation(?,?, ?, ?, ?, ? ,?)";
					System.out.println("dkow");
					PreparedStatement st = Aplikacja.getConnection().prepareStatement(query);
					st.setInt(1, clientid);
					System.out.println("hmmmmm");
					st.setDate(2, Date.valueOf(begD));
					System.out.println(begD);
					st.setDate(3, Date.valueOf(endD));
					System.out.println(endD);
					st.setString(4, hot_n);
					st.setString(5, city);
					st.setInt(6, Integer.parseInt(nop));
					st.setFloat(7, Float.parseFloat(pric));
					ResultSet toDelete = st.executeQuery();
					toDelete.next();
					System.out.println("co tu sie dzieje");
					
					String query2 = "CALL deleteReservation(?)";
					PreparedStatement deletionStatement = Aplikacja.getConnection().prepareStatement(query2);
					System.out.println(toDelete.getInt("rev_number"));
					deletionStatement.setInt(1, toDelete.getInt("rev_number"));
					deletionStatement.executeQuery();
					showTableActiveData();
					
				} else { 
					JOptionPane.showMessageDialog(this, "Prosze wybrac rezerwacje");
				}
			}
			if (eventSource == przeszleRezerwacjeButton) {
				panel.setVisible(false);
				tabela.setVisible(false);
				tabelaAktywne.setVisible(false);
				aktywne.setVisible(false);
				//getContentPane().add(przeszle, BorderLayout.CENTER);
				
				getContentPane().add(tabelaPrzeszle, BorderLayout.CENTER);
				//przeszle.setVisible(true);
				tabelaPrzeszle.setVisible(true);
				showTablePastData();
			}
			if (eventSource == sprawdzTerminyButton) {
				//przeszle.setVisible(false);
				aktywne.setVisible(false);
				tabelaPrzeszle.setVisible(false);
				tabelaAktywne.setVisible(false);
				
				getContentPane().add(panel, BorderLayout.CENTER);
				getContentPane().add(tabela, BorderLayout.SOUTH);
				panel.setVisible(true);
				tabela.setVisible(true);
			}
			
			if (eventSource == rezerwacja) {
				miasto = miastoField.getText();
				minCena = Integer.parseInt(minCenaField.getText());
				maxCena = Integer.parseInt(maxCenaField.getText());
				liczbaOsob = Integer.parseInt(liczbaOsobField.getText());
				
				String rokPoczatek = (String)poczatekRokComboBox.getSelectedItem();
				String miesiacPoczatek = (String)poczatekMiesiacComboBox.getSelectedItem();
				String dzienPoczatek = (String)poczatekDzienComboBox.getSelectedItem();
				
				dataPoczatek = rokPoczatek+"-"+miesiacPoczatek+"-"+dzienPoczatek;
				
				System.out.println(dataPoczatek);
				LocalDateTime now = LocalDateTime.now(); 
				LocalDateTime dataPoczatekLocalDataTime = LocalDateTime.of(Integer.valueOf(dataPoczatek.substring(0, 4)),
						Integer.valueOf(dataPoczatek.substring(5, 7)), Integer.valueOf(dataPoczatek.substring(8, 10)), 12, 0);
				if(dataPoczatekLocalDataTime.isAfter(now)){
				//model.setRowCount(0);
					String rokKoniec = (String)koniecRokComboBox.getSelectedItem();
					String miesiacKoniec = (String)koniecMiesiacComboBox.getSelectedItem();
					String dzienKoniec = (String)koniecDzienComboBox.getSelectedItem();
					
					dataKoniec = rokKoniec+"-"+miesiacKoniec+"-"+dzienKoniec;
					int row = table.getSelectedRow();
					//System.out.println("rzad: "+row);
					if (row >= 0) {
						//int nameCol = 0;
						int idCol = 4;
						//int row = table.getSelectedRow();
						//System.out.println("rzad: " + row); 
						//String hname = (String) table.getValueAt(row, nameCol);
						int roomid = (int) table.getValueAt(row, idCol);
						int clientid = getClientId(Aplikacja.login);
						
						System.out.println("clientid: "+clientid);
						
						String query = "INSERT INTO reservation(client_number, room_id, begin_date, end_date, begin_hour, end_hour) "
								+ "VALUES (?, ?, ?, ?, '12:00:00', '12:00:00')";
						
						PreparedStatement st;
						st = Aplikacja.getConnection().prepareStatement(query);
						
						st.setInt(1, clientid);
						st.setInt(2, roomid);
						st.setString(3, dataPoczatek);
						st.setString(4, dataKoniec);
						
						st.executeUpdate();
					}
					else 
						JOptionPane.showMessageDialog(this, "Nie wolno rezerwowac hotelu w przeszlosci!");
					
				} 
				else
					JOptionPane.showMessageDialog(this, "Prosze wybrac hotel");
				
				model.setRowCount(0);
			}
		} catch (Exception e) {
			
			System.out.println("Ten blad");
			System.out.println(e.getMessage());
			JOptionPane.showMessageDialog(this, e.getMessage(), "Blad", JOptionPane.ERROR_MESSAGE);
		}
	}	
	
	public void showTableData() {
		//frame = new JFrame("Database Search Result");
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.setLayout(new BorderLayout()); 
//TableModel tm = new TableModel();
		
//DefaultTableModel model = new DefaultTableModel(tm.getData1(), tm.getColumnNames()); 
//table = new JTable(model);
		
//		String textvalue = textbox.getText();
		String name;
		int price;
		String city;
		int nop, roomid;
		//System.out.println("W showtable, przed try");
		try
		{ 

			String query = "select hotel.hotel_name, address.town, room.price, room.people_number, room.room_id " + 
					"from hotel, room, address " + 
					"WHERE hotel.address_id = address.address_id " + 
					"AND hotel.hotel_id = room.hotel_id " + "AND room.people_number >= ? AND address.town = ? " +
					"AND NOT EXISTS (SELECT * FROM reservation WHERE room.room_id = reservation.room_id  AND ((reservation.begin_date between ? and ? OR reservation.end_date between ? and ?)"
					+ "OR (reservation.begin_date < ? AND reservation.end_date > ?))) AND room.price between ? and ?";
			
			PreparedStatement st;

			st = Aplikacja.getConnection().prepareStatement(query);
			
			st.setInt(1, liczbaOsob);
			st.setString(2, miasto);
			st.setString(3, dataPoczatek);
			st.setString(4, dataKoniec);
			st.setString(5, dataPoczatek);
			st.setString(6, dataKoniec);
			st.setString(7, dataPoczatek);
			st.setString(8, dataKoniec);
			st.setInt(9, minCena);
			st.setInt(10, maxCena);
			
			ResultSet rs = st.executeQuery();
			int i =0;
			while(rs.next())
			{
				System.out.println("query sie wykonalo");
				name = rs.getString("hotel.hotel_name");
				price = rs.getInt("room.price");
				city = rs.getString("address.town");
				nop = rs.getInt("room.people_number");
				roomid = rs.getInt("room.room_id");
				model.addRow(new Object[]{name, price, city, nop, roomid, dataPoczatek, dataKoniec});
				i++; 
			}
			if(i <1)
			{
				JOptionPane.showMessageDialog(null, "Brak wyników wyszukiwania","Błąd",
						JOptionPane.ERROR_MESSAGE);
			}
			if(i ==1)
			{
				System.out.println(i+" Record Found");
			}
			else
			{
				System.out.println(i+" Records Found");
			}
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(null, ex.getMessage(),"Error",
					JOptionPane.ERROR_MESSAGE);
		}
		
		
		//panel.revalidate();
		//panel.repaint();
		//getContentPane().add(panel, BorderLayout.CENTER);
		//frame.setSize(400,300);
}
	/*
	public class DateLabelFormatter extends AbstractFormatter {

		private static final long serialVersionUID = 1L;
		private String datePattern = "yyyy-MM-dd";
	    private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

	    @Override
	    public Object stringToValue(String text) throws ParseException, java.text.ParseException {
	        return dateFormatter.parseObject(text);
	    }

	    @Override
	    public String valueToString(Object value) throws ParseException {
	        if (value != null) {
	            Calendar cal = (Calendar) value;
	            return dateFormatter.format(cal.getTime());
	        }

	        return "";
	    }

	}*/
	
	public void showTablePastData() {
		try
		{ 
			model2.setRowCount(0);
			String query = "call getClientReservations(?);";		
			PreparedStatement sel_rev =  Aplikacja.getConnection().prepareStatement(query);
			sel_rev.setString(1, Aplikacja.login);
			ResultSet rs1 = sel_rev.executeQuery();
			int i =0;
			while(rs1.next())
			{
				String endDateS = rs1.getString(5);
				LocalDateTime now = LocalDateTime.now(); 
				LocalDateTime reservationDate = LocalDateTime.of(Integer.valueOf(endDateS.substring(0, 4)),
						Integer.valueOf(endDateS.substring(5, 7)), Integer.valueOf(endDateS.substring(8, 10)), 12, 0);
				if(reservationDate.isBefore(now)){
					String city = rs1.getString(1);
					String hotelName =   rs1.getString(2);
					String nop1 =  rs1.getString(3);
					String beginDateS = rs1.getString(4);
					String price = rs1.getString(6);
					model2.addRow(new Object[]{hotelName, price, city, nop1, beginDateS, endDateS});
					i++; 
				}
			}
			if(i <1)
			{
				JOptionPane.showMessageDialog(null, "Brak wyników wyszukiwania","Błąd",
						JOptionPane.ERROR_MESSAGE);
			}
			if(i ==1)
			{
				System.out.println(i+" Record Found");
			}
			else
			{
				System.out.println(i+" Records Found");
			}
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(null, ex.getMessage(),"Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void showTableActiveData() {
		try
		{ 
			model3.setRowCount(0);
			String query = "call getClientReservations(?);";		
			PreparedStatement sel_rev =  Aplikacja.getConnection().prepareStatement(query);
			sel_rev.setString(1, Aplikacja.login);
			ResultSet rs1 = sel_rev.executeQuery();
			int i =0;
			while(rs1.next())
			{
				String endDateS = rs1.getString(5);
				LocalDateTime now = LocalDateTime.now(); 
				LocalDateTime reservationDate = LocalDateTime.of(Integer.valueOf(endDateS.substring(0, 4)),
						Integer.valueOf(endDateS.substring(5, 7)), Integer.valueOf(endDateS.substring(8, 10)), 12, 0);
				if(reservationDate.isAfter(now)){
					String city = rs1.getString(1);
					String hotelName =   rs1.getString(2);
					int nop1 =  rs1.getInt(3);
					String beginDateS = rs1.getString(4);
					String price = rs1.getString(6);
					model3.addRow(new Object[]{hotelName, price, city, nop1, beginDateS, endDateS});
					i++; 
				}
			}
			if(i <1)
			{
				JOptionPane.showMessageDialog(null, "Brak wyników wyszukiwania","Błąd",
						JOptionPane.ERROR_MESSAGE);
			}
			if(i ==1)
			{
				System.out.println(i+" Record Found");
			}
			else
			{
				System.out.println(i+" Records Found");
			}
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(null, ex.getMessage(),"Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public int getClientId(String login) throws SQLException {
		
		String query = "SELECT client.client_number FROM client, login WHERE login.username = ? AND login.login_id = client.login_id";
		System.out.println("Przed wykonaniem qury");
		PreparedStatement ps;
		ps = Aplikacja.getConnection().prepareStatement(query);
		ps.setString(1, login);
		ResultSet rs = ps.executeQuery();
		System.out.println("Po wykonaniem qury");
		rs.next();
		int id = rs.getInt("client.client_number");
		
		return id;
	}
} 
