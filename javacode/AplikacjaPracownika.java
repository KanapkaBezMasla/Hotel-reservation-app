package Konsola;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;

public class AplikacjaPracownika extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;

	Font font = new Font("SERIF", Font.BOLD, 12);

	JButton wyszukajRezerwacjeButton = new JButton("wyszukaj rezerwacje");
	JButton wyszukajPokojeButton = new JButton("wyszukaj pokoje");
	JButton edycja = new JButton("Edytuj");
	JButton edycja1 = new JButton("Edytuj");
	JToolBar glownyToolBar = new  JToolBar();
	JPanel panel = new JPanel();
	JFrame frame1;
	JFrame frame2;
	JTable table;
	int roomid;
	int resid;
	
	JLabel nameLabel;
	JLabel roomnrLabel;
	JLabel peopleLabel;
	JLabel priceLabel;
	JLabel balconyLabel;
	JLabel familyLabel;
	JLabel bathroomLabel;
	JLabel couplebedLabel;
	JLabel vipLabel;
	
	JTextField nameField;
	JTextField roomnrField;
	JTextField peopleField;
	JTextField priceField;
	JTextField balconyField;
	JTextField familyField;
	JTextField bathroomField;
	JTextField couplebedField;
	JTextField vipField;
	
	JLabel roomidLabel;
	JLabel begindLabel;
	JLabel enddLabel;
	JLabel beginhLabel;
	JLabel endhLabel;
	
	JTextField roomidField;
	JTextField begindField;
	JTextField enddField;
	JTextField beginhField;
	JTextField endhField;
	
	int roomnr;
	int people;
	int price;
	int balcony;
	int family;
	int bathroom;
	int couplebed;
	int vip;
	
	int rid;
	String dateb;
	String datee;
	String hourb;
	String houre;
	
	String hname;
	
	String[] columnNames = {"Hotel name", "Room number", "Price", "Number of people", "Balcony", "Family", "Private bathroom", "Couple bed", "VIP"};
	
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
		edycja.addActionListener(this);
		
		panel.add(wyszukajRezerwacjeButton);
		panel.add(wyszukajPokojeButton);
		//panel.add(edycja);
		
		//edycja.setVisible(false);
		
		glownyToolBar.add(panel);
		getContentPane().add(glownyToolBar, BorderLayout.NORTH);
		
		setVisible(true);
	}



	@Override
	public void actionPerformed(ActionEvent event) {
		Object eventSource = event.getSource();
		try {
			if(eventSource==wyszukajRezerwacjeButton) {
				
				String resnr = JOptionPane.showInputDialog(panel, "Podaj numer rezerwacji");
				resid = Integer.parseInt(resnr);
				
				showTableDataReservation();
				
			}
			if(eventSource==wyszukajPokojeButton) {
				
				String roomnr = JOptionPane.showInputDialog(panel, "Podaj numer pokoju");
				roomid = Integer.parseInt(roomnr);
				
				showTableDataRoom();
				
			}
			if(eventSource==edycja) {
				
				String query = "UPDATE hotel, room "
						+ "SET hotel.hotel_name = ?, room.room_number = ?, room.people_number = ?, room.price = ?, room.balcony = ?, room.family = ?, room.private_bathroom = ?, room.couple_bed = ?, room.vip = ? "
						+ "WHERE hotel.hotel_id = room.hotel_id AND room.room_id = ?";
				
				PreparedStatement st;

				st = Aplikacja.getConnection().prepareStatement(query);
				
				st.setString(1, nameField.getText());
				st.setInt(2, Integer.parseInt(roomnrField.getText()));
				st.setInt(3, Integer.parseInt(peopleField.getText()));
				st.setInt(4, Integer.parseInt(priceField.getText()));
				st.setInt(5, Integer.parseInt(balconyField.getText()));
				st.setInt(6, Integer.parseInt(familyField.getText()));
				st.setInt(7, Integer.parseInt(bathroomField.getText()));
				st.setInt(8, Integer.parseInt(couplebedField.getText()));
				st.setInt(9, Integer.parseInt(vipField.getText()));
				st.setInt(10, roomid);
				 st.executeUpdate();
				
			
			}
			
			if(eventSource==edycja1) {
				if (checkID(Integer.parseInt(roomidField.getText()))) {
				String query = "UPDATE reservation "
						+ "SET room_id = ?, begin_date = ?, end_date = ?, begin_hour = ?, end_hour = ? "
						+ "WHERE rev_number = ?";
				
				PreparedStatement st;

				st = Aplikacja.getConnection().prepareStatement(query);
				
				st.setInt(1, Integer.parseInt(roomidField.getText()));
				st.setString(2, begindField.getText());
				st.setString(3, enddField.getText());
				st.setString(4, beginhField.getText());
				st.setString(5, endhField.getText());
				st.setInt(6, resid);
				
				st.executeUpdate();
				} else JOptionPane.showMessageDialog(this,
					    "Pokój o podanym ID nie istnieje",
					    "Nieprawidłowe dane",
					    JOptionPane.ERROR_MESSAGE);
				
			}
		}catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	public void showTableDataRoom()
	{

		frame1 = new JFrame("Edycja pokoju");
		frame1.setLayout(null); 
		nameLabel = new JLabel("Hotel: ");
		roomnrLabel = new JLabel("Numer pokoju: ");
		peopleLabel = new JLabel("Ilość gości: ");
		priceLabel = new JLabel("Cena: ");
		balconyLabel = new JLabel("Balkon: ");
		familyLabel = new JLabel("Rodzinny: ");
		bathroomLabel = new JLabel("Łazienka: ");
		couplebedLabel = new JLabel("Łóżko małżeńskie: ");
		vipLabel = new JLabel("Vip: ");
		
		nameField = new JTextField();
		roomnrField = new JTextField();
		peopleField = new JTextField();
		priceField = new JTextField();
		balconyField = new JTextField();
		familyField = new JTextField();
		bathroomField = new JTextField();
		couplebedField = new JTextField();
		vipField = new JTextField();
		
		try
		{ 
			String query = "SELECT hotel.hotel_name, room.room_number, room.people_number, room.price, room.balcony, room.family, room.private_bathroom, room.couple_bed, room.vip "
					+ "FROM hotel, room "
				+ "WHERE hotel.hotel_id = room.hotel_id AND room.room_id = ?";
		
			PreparedStatement st;

			st = Aplikacja.getConnection().prepareStatement(query);
		
			st.setInt(1, roomid);
		
			ResultSet rs = st.executeQuery();
		
			int i =0;
			if(rs.next())
			{
				hname = rs.getString(1);
				roomnr = rs.getInt(2);
				people = rs.getInt(3);
				price = rs.getInt(4);
				balcony = rs.getInt(5);
				family = rs.getInt(6);
				bathroom = rs.getInt(7);
				couplebed = rs.getInt(8);
				vip = rs.getInt(9);
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
			
			nameLabel.setBounds(10, 10, 100, 20);
			nameField.setBounds(130, 10, 100, 20);
			//nameLabel.setLabelFor(nameField);
			frame1.add(nameLabel);
			frame1.add(nameField);
			nameField.setText(hname);
			
			roomnrLabel.setBounds(10, 40, 100, 20);
			roomnrField.setBounds(130, 40, 100, 20);
			//roomnrLabel.setLabelFor(roomnrField);
			frame1.add(roomnrLabel);
			frame1.add(roomnrField);
			roomnrField.setText(String.valueOf(roomnr));
			
			peopleLabel.setBounds(10, 70, 100, 20);
			peopleField.setBounds(130, 70, 100, 20);
			//peopleLabel.setLabelFor(peopleField);
			frame1.add(peopleLabel);
			frame1.add(peopleField);
			peopleField.setText(String.valueOf(people));
			
			priceLabel.setBounds(10, 100, 100, 20);
			priceField.setBounds(130, 100, 100, 20);
			//priceLabel.setLabelFor(priceField);
			frame1.add(priceLabel);
			frame1.add(priceField);
			priceField.setText(String.valueOf(price));
			
			balconyLabel.setBounds(10, 130, 100, 20);
			balconyField.setBounds(130, 130, 100, 20);
			//balconyLabel.setLabelFor(balconyField);
			frame1.add(balconyLabel);
			frame1.add(balconyField);
			balconyField.setText(String.valueOf(balcony));
			
			familyLabel.setBounds(10, 160, 100, 20);
			familyField.setBounds(130, 160, 100, 20);
			//familyLabel.setLabelFor(familyField);
			frame1.add(familyLabel);
			frame1.add(familyField);
			familyField.setText(String.valueOf(family));
			
			bathroomLabel.setBounds(10, 190, 100, 20);
			bathroomField.setBounds(130, 190, 100, 20);
			//bathroomLabel.setLabelFor(bathroomField);
			frame1.add(bathroomLabel);
			frame1.add(bathroomField);
			bathroomField.setText(String.valueOf(bathroom));
			
			couplebedLabel.setBounds(10, 220, 100, 20);
			couplebedField.setBounds(130, 220, 100, 20);
			//couplebedLabel.setLabelFor(couplebedField);
			frame1.add(couplebedLabel);
			frame1.add(couplebedField);
			couplebedField.setText(String.valueOf(couplebed));
			
			vipLabel.setBounds(10, 250, 100, 20);
			vipField.setBounds(130, 250, 100, 20);
			//vipLabel.setLabelFor(vipField);
			frame1.add(vipLabel);
			frame1.add(vipField);
			vipField.setText(String.valueOf(vip));
			//frame1.add(edycja);
			edycja.addActionListener(this);
			edycja.setBounds(100, 400, 200, 40);
			frame1.add(edycja);
			frame1.setVisible(true);
			frame1.setSize(400,500);
		}
	
	public void showTableDataReservation()
	{

		frame2 = new JFrame("Edycja rezerwacji");
		frame2.setLayout(null); 
		roomidLabel = new JLabel("ID pokoju: ");
		begindLabel = new JLabel("Data rozpoczęcia: ");
		enddLabel = new JLabel("Data zakończenia: ");
		beginhLabel = new JLabel("Godzina zameldowania: ");
		endhLabel = new JLabel("Godzina wymeldowania: ");
		
		roomidField = new JTextField();
		begindField = new JTextField();
		enddField = new JTextField();
		beginhField = new JTextField();
		endhField = new JTextField();

		try
		{ 
			String query = "SELECT room_id, begin_date, end_date, begin_hour, end_hour "
					+ "FROM reservation "
				+ "WHERE rev_number = ?";
		
			PreparedStatement st;

			st = Aplikacja.getConnection().prepareStatement(query);
		
			st.setInt(1, resid);
		
			ResultSet rs = st.executeQuery();
		
			int i =0;
			if(rs.next())
			{
				rid = rs.getInt(1);
				dateb = rs.getString(2);
				datee = rs.getString(3);
				hourb = rs.getString(4);
				houre = rs.getString(5);
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
			roomidLabel.setBounds(10, 10, 100, 20);
			roomidField.setBounds(130, 10, 100, 20);
			frame2.add(roomidLabel);
			frame2.add(roomidField);
			roomidField.setText(String.valueOf(rid));
			begindLabel.setBounds(10, 40, 100, 20);
			begindField.setBounds(130, 40, 100, 20);
			frame2.add(begindLabel);
			frame2.add(begindField);
			begindField.setText(dateb);
			enddLabel.setBounds(10, 70, 100, 20);
			enddField.setBounds(130, 70, 100, 20);
			frame2.add(enddLabel);
			frame2.add(enddField);
			enddField.setText(datee);
			beginhLabel.setBounds(10, 100, 100, 20);
			beginhField.setBounds(130, 100, 100, 20);
			frame2.add(beginhLabel);
			frame2.add(beginhField);
			beginhField.setText(hourb);
			endhLabel.setBounds(10, 130, 100, 20);
			endhField.setBounds(130, 130, 100, 20);
			frame2.add(endhLabel);
			frame2.add(endhField);
			endhField.setText(houre);
			//frame1.add(edycja);
			edycja1.addActionListener(this);
			edycja1.setBounds(100, 400, 200, 40);
			frame2.add(edycja1);
			frame2.setVisible(true);
			frame2.setSize(400,500);
		}
	
	public boolean checkID (int id) throws SQLException {
		
		String query = "SELECT * FROM room WHERE room_id = ?";
	
		PreparedStatement st;

		st = Aplikacja.getConnection().prepareStatement(query);
	
		st.setInt(1, id);
	
		ResultSet rs = st.executeQuery();
	
		if(rs.next())
		{
			return true;
		} else return false;
		
	}
}
