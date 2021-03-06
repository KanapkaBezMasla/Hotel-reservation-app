package Konsola;

import static Konsola.Aplikacja.pracownik;
import static Konsola.Aplikacja.turysta;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class LogowanieTest {
	Aplikacja aplikacja;
	

	@Before
	 public void setUp(){
	 aplikacja = new Aplikacja();
	 }

	
	@Test
	 public void testWarunkiLogowania() {
		Logowanie log = new Logowanie();
		log.hasloField.setText("przykladowy");
		log.loginField.setText("przykladowy2");
		assertFalse(log.warunkiLogowania()); 
		pracownik = true;
		assertTrue(log.warunkiLogowania()); 
		turysta = true;
		assertFalse(log.warunkiLogowania()); 
		pracownik = false;
		assertTrue(log.warunkiLogowania());
	 }
}
