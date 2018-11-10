package thegame.spiel.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import thegane.bestandteile.spieler.Bot;
import thegane.bestandteile.spieler.Spieler;
import thegane.bestandteile.spieler.User;
import thegane.bestandteile.stapel.HochzaehlStapel;
import thegane.bestandteile.stapel.Karte;
import thegane.bestandteile.stapel.RunterzaehlStapel;
import thegane.bestandteile.stapel.Stapel;

public class test {
	
	@Test
	public void testHochzaehlStapel(){
		Stapel x=new HochzaehlStapel("Stapel");
		assertEquals(1,x.peek().getWert());
		x.push(new Karte(2));
		
		assertEquals(2,x.peek().getWert());
		assertEquals(2,x.size());
	}
	

	
	@Test
	public void testRunterzaehlStapel(){
		Stapel x=new RunterzaehlStapel("Stapel2");
		assertEquals(100,x.peek().getWert());
		x.push(new Karte(98));
		
		assertEquals(98,x.peek().getWert());
		assertEquals(2, x.size());
	}
	@Test
	public void testZehnerschritte(){
		Bot x =new Bot("Bot");
		Karte k = new Karte(20);
		Stapel s = new HochzaehlStapel("Stapel");
		
		s.push(new Karte(10));
		assertEquals(true,x.zehnerSchritt(k, s));
	}

	@Test
	public void testZugMoeglich(){
		User spieler=new User("User");
		Stapel [] s={new HochzaehlStapel("1"),
				new HochzaehlStapel("2"),
				new RunterzaehlStapel("3"),
				new RunterzaehlStapel("4")};
	    s[0].push(new Karte(92));
	    s[1].push(new Karte(91));
	    s[2].push(new Karte(4));
	    s[3].push(new Karte(5));
	    
	    ArrayList<Karte> karten= new ArrayList<>();
	    karten.add(new Karte(95));
	    karten.add(new Karte(90));
	    karten.add(new Karte(60));
	    karten.add(new Karte(54));
	    karten.add(new Karte(30));
	    karten.add(new Karte(63));
	    
	    spieler.setKarten(karten);
	    
	    assertEquals(false,spieler.zugMoeglich(s));
	}
}
