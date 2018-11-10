package thegame.spiel;

import java.util.ArrayList;
import java.util.List;

import thegane.bestandteile.spieler.*;
import thegane.bestandteile.stapel.*;

public class Spiel {
	/** Spieler */
	private Spieler[] spieler;

	/** Ziehstapel */
	private List<Karte> ziehstapel = new ArrayList<>();

	/** Anzahl an bereits gespielten Zügen */
	private int spielzuege;

	private Variante variante;

	/** 4 Ablegestapel */
	private HochzaehlStapel hochzaehlStapel1 = new HochzaehlStapel("1. Hochzählstapel");
	private HochzaehlStapel hochzaehlStapel2 = new HochzaehlStapel("2. Hochzählstapel");
	private RunterzaehlStapel runterzaehlStapel1 = new RunterzaehlStapel("1. Runterzählstapel");
	private RunterzaehlStapel runterzaehlStapel2 = new RunterzaehlStapel("2. Runterzählstapel");
	private Stapel[] stapel = { hochzaehlStapel1, hochzaehlStapel2, runterzaehlStapel1, runterzaehlStapel2 };

	/**
	 * legt neues Spiel an
	 * 
	 * @param anzahl
	 *            Anzahl der Computermitspieler
	 */
	public Spiel(int anzahl, Variante variante) {
		this.variante = variante;
		this.spieler = new Spieler[anzahl + 1];
		spieler[spieler.length - 1] = new User("Realer Spieler");
		for (int i = 0; i < spieler.length - 1; i++) {
			spieler[i] = new Bot("Bot" + (i + 1));
		}
	}

	/**
	 * das eigentliche Spiel
	 */
	public void startGame() {
		System.out.println("The Game beginnt!");
		boolean spiel = true;
		ziehstapelfuellen();
		anfangsKartenZiehen();
		System.out.println();
		akutelleStapelAusgeben();
		bestenSpielerSetzen();

		while (spiel) {
			for (Spieler aktuellerSpieler : spieler) {
				System.out.println();
				System.out.println((getSpielzuege() + 1) + ". Spielzug");
				if (aktuellerSpieler != null) {
					aktuellerSpieler.ziehen(this);
					if (aktuellerSpieler.getKarten().size() > 0) {
						stapel = aktuellerSpieler.ablegen(stapel, this);
					} else {
						aktuellerSpieler = null;
					}
					spiel = (aktuellerSpieler.getGelegteKarten() >= variante.getMindestAnzahlKarten()) ? true : false;
					aktuellerSpieler.setGelegteKarten(0);
					spielzuege++;
				}
			}
		}

		if (gesamtKarten() != 0) {
			System.out.println();
			System.out.println("GAME OVER, Sie haben TheGame verloren!");
			System.out.println("Nicht abgelegte Karten: " + (gesamtKarten() + ziehstapel.size()));
			if ((gesamtKarten() + ziehstapel.size()) <= 10) {
				System.out.println("Schade, es waren weniger als 10 Karten übrig. Beim nächsten mal klappt es bestimmt!");
			} 
		} else {
			System.out.println("Glückwunsch! Sie haben TheGame gewonnen!");
		}

	}

	/** befüllt den Ziehstapel */
	private void ziehstapelfuellen() {
		for (int i = 2; i < 100; i++) {
			ziehstapel.add(new Karte(i));
		}
		System.out.println("Der Ziehstapel ist nun einsatzbereit!");
	}

	/** gibt aktuelle Stapel aus */
	public void akutelleStapelAusgeben() {
		System.out.println("Aktuelle Stapel: ");
		int i = 1;
		for (Stapel aktuellerStapel : stapel) {
			if (aktuellerStapel instanceof HochzaehlStapel) {
				System.out.print(i + ": Hochzähl Stapel: ");
			} else if (aktuellerStapel instanceof RunterzaehlStapel) {
				System.out.print(i + ": Runterzähl Stapel: ");
			}
			System.out.println(aktuellerStapel.peek().getWert());
			i++;
		}
		System.out.println("Ziehstapel: " + getZiehstapel().size() +  " Karten verbleibend");
		System.out.println();
	}

	/** anfangskarten werden gezogen **/
	public void anfangsKartenZiehen() {
		for (Spieler aktuellerSpieler : spieler) {
				ziehstapel = aktuellerSpieler.ziehen(this);
		}
		System.out.println("Jeder Spieler hat seine Startkarten erhalten!");
	}

	public void bestenSpielerSetzen() {
		Spieler besterSpieler = spieler[0];
		Karte besteKarte = spieler[0].getKarten().get(0);
		Stapel besterStapel = stapel[0];
		
		for (Spieler aktuellerSpieler : spieler) {
			for (Stapel aktuellerStapel : stapel) {
				for (Karte aktuelleKarte : aktuellerSpieler.getKarten()) {
					if (Math.abs(aktuellerSpieler.abstand(aktuelleKarte, aktuellerStapel)) < 
							Math.abs(aktuellerSpieler.abstand(besteKarte,besterStapel))) {
						besteKarte = aktuelleKarte;
						besterStapel = aktuellerStapel;
						besterSpieler = aktuellerSpieler;
					}

				}
			}
		}
		
		Spieler temp = spieler[0];
		spieler[0] = besterSpieler;
		for (int i = 1; i < spieler.length; i++) {
			if (spieler[i].getName().equals(besterSpieler.getName())) {
				spieler[i] = temp;
			}
		}
		
		System.out.println("Der Spieler " + spieler[0].getName() + " beginnt, da er die besten Karten hat!");
	}

	public int gesamtKarten() {
		int summe = 0;
		for (Spieler aktuellerSpieler : spieler) {
			summe += aktuellerSpieler.getKarten().size();
		}
		return summe;
	}

	public int getSpielzuege() {
		return spielzuege;
	}

	public void setSpielzuege(int spielzuege) {
		this.spielzuege = spielzuege;
	}

	public Stapel[] getStapel() {
		return stapel;
	}

	public Variante getVariante() {
		return variante;
	}

	public List<Karte> getZiehstapel() {
		return ziehstapel;
	}

}
