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
	@Override // mind. anzahl an karten übergeben
	public Stapel[] ablegen(Stapel[] stapel, Spiel spiel) {

		// prüft und legt den 10er schritt zuerst
		for (Stapel aktuellerStapel : stapel) {
			for (int i = 0; i < karten.size(); i++) {
				// Abstand zehnerschritt schön und gut, man muss aber auch
				// schauen, dass der zehner abstand
				// in die richtige Richtung geht, sonst ergibt legen keinen sinn
				if (aktuellerStapel instanceof HochzaehlStapel) {
					if (zehnerSchritt(karten.get(i), aktuellerStapel)
							&& karten.get(i).getWert() < aktuellerStapel.peek().getWert()) {
						karteAblegen(karten.get(i), aktuellerStapel);
						ablegen(stapel, spiel);
						System.out.println("10er Rückschritt!");
					}
				} else if (aktuellerStapel instanceof RunterzaehlStapel) {
					if (zehnerSchritt(karten.get(i), aktuellerStapel)
							&& karten.get(i).getWert() > aktuellerStapel.peek().getWert()) {
						karteAblegen(karten.get(i), aktuellerStapel);
						ablegen(stapel, spiel);
						System.out.println("10er Rückschritt!");
					}
				}
			}
		}

		/*
		 * führt sinnvolles legen so oft (in abhängigkeit von gelegten karten
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
		 * vor x spiel zügen: wenn es noch weitere komb. mit distanz kleiner 5
		 * (?) gibt -> ablegen nach x spiel zügen: wenn es noch weitere komb.
		 * mit distanz kleiner 10 (?) gibt -> ablegen
		 * 
		 */
		for (Stapel aktuellerStapel : stapel) {

			for (int i = 0; i < karten.size(); i++) {
				// für den anfang des spiels
				if (aktuellerStapel instanceof HochzaehlStapel && karten.get(i).getWert() > aktuellerStapel.peek().getWert()) {
					if (Math.abs(abstand(karten.get(i), aktuellerStapel)) < 4) {
						karteAblegen(karten.get(i), aktuellerStapel);
						ablegen(stapel, spiel);
						// Beispielwerte für fortlaufendes Spie
					}
				} else if (aktuellerStapel instanceof RunterzaehlStapel && karten.get(i).getWert() < aktuellerStapel.peek().getWert()) {
					if (Math.abs(abstand(karten.get(i), aktuellerStapel)) < 4) {
						karteAblegen(karten.get(i), aktuellerStapel);
						ablegen(stapel, spiel);
						// Beispielwerte für fortlaufendes Spie
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
	 * @return 10er Schritt möglich
	 */
	public boolean zehnerSchritt(Karte karte, Stapel stapel) {
		return ((karte.getWert() - stapel.peek().getWert()) == 10) ? true : false;
	}

}
