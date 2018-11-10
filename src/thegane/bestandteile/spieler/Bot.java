package thegane.bestandteile.spieler;

import java.util.ArrayList;

import thegame.spiel.Spiel;
import thegane.bestandteile.stapel.*;

public class Bot extends Spieler {
	/**
	 * legt neuen Bot an
	 * 
	 * @param name
	 *            Name
	 */
	public Bot(String name) {
		super(name);
	}

	/**
	 * legt Karten ab
	 * 
	 * @param stapel
	 *            alle Stapel (2HR + 2RZ)
	 */
	@Override // mind. anzahl an karten �bergeben
	public Stapel[] ablegen(Stapel[] stapel, Spiel spiel) {

		// pr�ft und legt den 10er schritt zuerst
		for (Stapel aktuellerStapel : stapel) {
			for (int i = 0; i < karten.size(); i++) {
				// Abstand zehnerschritt sch�n und gut, man muss aber auch
				// schauen, dass der zehner abstand
				// in die richtige Richtung geht, sonst ergibt legen keinen sinn
				if (aktuellerStapel instanceof HochzaehlStapel) {
					if (zehnerSchritt(karten.get(i), aktuellerStapel)
							&& karten.get(i).getWert() < aktuellerStapel.peek().getWert()) {
						karteAblegen(karten.get(i), aktuellerStapel);
						ablegen(stapel, spiel);
						System.out.println("10er R�ckschritt!");
					}
				} else if (aktuellerStapel instanceof RunterzaehlStapel) {
					if (zehnerSchritt(karten.get(i), aktuellerStapel)
							&& karten.get(i).getWert() > aktuellerStapel.peek().getWert()) {
						karteAblegen(karten.get(i), aktuellerStapel);
						ablegen(stapel, spiel);
						System.out.println("10er R�ckschritt!");
					}
				}
			}
		}

		/*
		 * f�hrt sinnvolles legen so oft (in abh�ngigkeit von gelegten karten
		 * durch 10er schritt oben) aus, dass min anzahl von 2 mal ablegen
		 * erreicht wird
		 */
		int mindestAnzahl = (spiel.getZiehstapel().size() > 0) ? spiel.getVariante().getMindestAnzahlKarten()
				: spiel.getVariante().getMindestAnzahlKartenStapelLeer();

		while (getGelegteKarten() < mindestAnzahl) {
			// Beste Kombination zum legen:
			Karte besteKarte = karten.get(0);
			Stapel besterStapel = stapel[0];
			for (Stapel aktuellerStapel : stapel) {
				for (Karte aktuelleKarte : karten) {
					if (aktuellerStapel instanceof HochzaehlStapel && abstand(aktuelleKarte, aktuellerStapel) > 0
							&& aktuelleKarte.getWert() > aktuellerStapel.peek().getWert()) {
						if (abstand(aktuelleKarte, aktuellerStapel) < Math.abs(abstand(besteKarte, besterStapel))) {
							besteKarte = aktuelleKarte;
							besterStapel = aktuellerStapel;
						}
					} else if (aktuellerStapel instanceof RunterzaehlStapel && abstand(aktuelleKarte, aktuellerStapel) < 0
							&& aktuelleKarte.getWert() < aktuellerStapel.peek().getWert()) {
						if (Math.abs(abstand(aktuelleKarte, aktuellerStapel)) < Math.abs(abstand(besteKarte, besterStapel))) {
							besteKarte = aktuelleKarte;
							besterStapel = aktuellerStapel;
						}
					}
				}
			}
			karteAblegen(besteKarte, besterStapel);
			ablegen(stapel, spiel);
		}

		/*
		 * vor x spiel z�gen: wenn es noch weitere komb. mit distanz kleiner 5
		 * (?) gibt -> ablegen nach x spiel z�gen: wenn es noch weitere komb.
		 * mit distanz kleiner 10 (?) gibt -> ablegen
		 * 
		 */
		for (Stapel aktuellerStapel : stapel) {

			for (int i = 0; i < karten.size(); i++) {
				// f�r den anfang des spiels
				if (aktuellerStapel instanceof HochzaehlStapel && karten.get(i).getWert() > aktuellerStapel.peek().getWert()) {
					if (Math.abs(abstand(karten.get(i), aktuellerStapel)) < 4) {
						karteAblegen(karten.get(i), aktuellerStapel);
						ablegen(stapel, spiel);
						// Beispielwerte f�r fortlaufendes Spie
					}
				} else if (aktuellerStapel instanceof RunterzaehlStapel && karten.get(i).getWert() < aktuellerStapel.peek().getWert()) {
					if (Math.abs(abstand(karten.get(i), aktuellerStapel)) < 4) {
						karteAblegen(karten.get(i), aktuellerStapel);
						ablegen(stapel, spiel);
						// Beispielwerte f�r fortlaufendes Spie
					}
				}

			}
		}

		return stapel;
	}

	/**
	 * @param karte
	 *            aktuelle Karte
	 * @param stapel
	 *            aktueller Stapel
	 * @return 10er Schritt m�glich
	 */
	public boolean zehnerSchritt(Karte karte, Stapel stapel) {
		return ((karte.getWert() - stapel.peek().getWert()) == 10) ? true : false;
	}

}
