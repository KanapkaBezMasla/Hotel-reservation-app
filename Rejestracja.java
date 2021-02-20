package Konsola;

import javax.swing.*; 
import java.awt.*; 
import java.awt.event.*; 
import java.awt.event.ActionEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
  
class Rejestracja extends JFrame implements ActionListener { 

 
	private static final long serialVersionUID = 1L;
	private Container c; 
    private JLabel title; 
    private JLabel name; 
    private JTextField tname;
    private JLabel lname;
    private JTextField tlname;
    private JLabel country;
    private JTextField tcountry;
    private JLabel city;
    private JTextField tcity;
    private JLabel street;
    private JTextField tstreet;
    private JLabel mno; 
    private JTextField tmno; 
    private JButton sub; 
    private JButton reset; 
    
    private String imie, nazwisko, login, tel, haslo, sprHaslo;
  
    
    public Rejestracja() 
    { 
        setTitle("Rejestracja"); 
        //setSize(900, 650);
        setBounds(230, 60, 900, 650); 
        setDefaultCloseOperation(EXIT_ON_CLOSE); 
        setResizable(false); 
  
        c = getContentPane(); 
        c.setLayout(null); 
  
        title = new JLabel("Utwórz nowe konto"); 
        title.setFont(new Font("Arial", Font.PLAIN, 30)); 
        title.setSize(300, 30); 
        title.setLocation(300, 30); 
        c.add(title); 
  
        name = new JLabel("Imiê"); 
        name.setFont(new Font("Arial", Font.PLAIN, 20)); 
        name.setSize(100, 20); 
        name.setLocation(100, 100); 
        c.add(name); 
  
        tname = new JTextField(); 
        tname.setFont(new Font("Arial", Font.PLAIN, 15)); 
        tname.setSize(201, 20); 
        tname.setLocation(250, 102); 
        c.add(tname); 
        
        lname = new JLabel("Nazwisko"); 
        lname.setFont(new Font("Arial", Font.PLAIN, 20)); 
        lname.setSize(100, 20); 
        lname.setLocation(100, 150); 
        c.add(lname); 
  
        tlname = new JTextField(); 
        tlname.setFont(new Font("Arial", Font.PLAIN, 15)); 
        tlname.setSize(201, 20); 
        tlname.setLocation(250, 152); 
        c.add(tlname); 
  
        mno = new JLabel("Telefon"); 
        mno.setFont(new Font("Arial", Font.PLAIN, 20)); 
        mno.setSize(100, 20); 
        mno.setLocation(100, 200); 
        c.add(mno); 
  
        tmno = new JTextField(); 
        tmno.setFont(new Font("Arial", Font.PLAIN, 15)); 
        tmno.setSize(201, 20); 
        tmno.setLocation(250, 202); 
        c.add(tmno);
  
  
        country = new JLabel("Login"); 
        country.setFont(new Font("Arial", Font.PLAIN, 20)); 
        country.setSize(100, 20); 
        country.setLocation(100, 246); 
        c.add(country); 
  
        tcountry = new JTextField(); 
        tcountry.setFont(new Font("Arial", Font.PLAIN, 15)); 
        tcountry.setSize(200, 20); 
        tcountry.setLocation(251, 248); 
        c.add(tcountry); 
        
        city = new JLabel("Has³o"); 
        city.setFont(new Font("Arial", Font.PLAIN, 20)); 
        city.setSize(100, 20); 
        city.setLocation(100, 296); 
        c.add(city); 
  
        tcity = new JTextField(); 
        tcity.setFont(new Font("Arial", Font.PLAIN, 15)); 
        tcity.setSize(200, 20); 
        tcity.setLocation(251, 298); 
        c.add(tcity); 
        
        street = new JLabel("Powtórz\n has³o"); 
        street.setFont(new Font("Arial", Font.PLAIN, 20)); 
        street.setSize(132, 20); 
        street.setLocation(100, 345); 
        c.add(street); 
  
        tstreet = new JTextField(); 
        tstreet.setFont(new Font("Arial", Font.PLAIN, 15)); 
        tstreet.setSize(200, 20); 
        tstreet.setLocation(251, 347); 
        c.add(tstreet);

  
        sub = new JButton("ZatwierdŸ"); 
        sub.setFont(new Font("Arial", Font.PLAIN, 15)); 
        sub.setSize(100, 20); 
        sub.setLocation(354, 550); 
        sub.addActionListener(this); 
        c.add(sub); 
  
        reset = new JButton("Reset"); 
        reset.setFont(new Font("Arial", Font.PLAIN, 15)); 
        reset.setSize(100, 20); 
        reset.setLocation(500, 550); 
        reset.addActionListener(this); 
        c.add(reset);  
        
  
        setVisible(true); 
    } 

    public void actionPerformed(ActionEvent e) 
    { 
    	Object eventSource = e.getSource();

    		if (eventSource == sub) {
    			haslo = tcity.getText();
    			sprHaslo = tstreet.getText();
    			if (haslo.equals(sprHaslo)) {
    				tel = tmno.getText();
    				login = tcountry.getText();
    				imie = tname.getText();
    				nazwisko = tlname.getText();
    				
    				String query = "INSERT INTO RezerwacjaHotelu.login (PASSWORD, USERNAME) "
    						+ "VALUES (?, ?);";
    				
    				try {
						PreparedStatement st = Aplikacja.getConnection().prepareStatement(query);
						st.setString(1, login);
						st.setString(2, haslo);
						ResultSet rs = st.executeQuery();
						
						if (rs.next()) {
							String query1 = "SELECT login_id FROM login "
									+ "WHERE login.username = ?";
							PreparedStatement sel_login_id = Aplikacja.getConnection().prepareStatement(query1);
							sel_login_id.setString(1, login);
							ResultSet res_log_id = sel_login_id.executeQuery();
							
							String query2 = "INSERT INTO RezerwacjaHotelu.client (LOGIN_ID, FIRST_NAME, SURNAME, PHONE_NUMBER) "
		    						+ "VALUES (?, ?, ?, ?);";
							PreparedStatement insert_client = Aplikacja.getConnection().prepareStatement(query2);
							int loginID = res_log_id.getRow();
							insert_client.setString(1, String.valueOf(loginID));
							insert_client.setString(2, imie);
							insert_client.setString(3, nazwisko);
							insert_client.setString(4, tel);
							ResultSet res_client = insert_client.executeQuery();
							
							if (!res_client.next())
								throw new SQLException("Nie uda³o sie wprowadzic klienta!");
							
						}else 
							throw new SQLException("Nie uda³o sie wproawdzic loginu i hasla!");
						
						
						
					} catch (SQLException e1) {
						
						e1.printStackTrace();
					}
    				
    				
    			}
    			else {
    				JOptionPane.showMessageDialog(this,
    					    "Zle wprowadzone dane :(",
    					    "Ostrze¿enie",
    					    JOptionPane.WARNING_MESSAGE);
    				System.out.println(haslo);
    				System.out.println(sprHaslo);
    			}
    		}
    		
    		if (eventSource == reset) {
    			String def = "";
    			tname.setText(def);
    			tlname.setText(def);
    			tmno.setText(def);
    			tcountry.setText(def);
    			tcity.setText(def);
    			tstreet.setText(def);
    			
    		}
    	
    } 
} 
  