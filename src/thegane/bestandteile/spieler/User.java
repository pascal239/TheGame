package thegane.bestandteile.spieler;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import thegame.spiel.Spiel;
import thegane.bestandteile.stapel.HochzaehlStapel;
import thegane.bestandteile.stapel.Karte;
import thegane.bestandteile.stapel.RunterzaehlStapel;
import thegane.bestandteile.stapel.Stapel;

public class User extends Spieler {
	/**
	 * legt neuen User an
	 * 
	 * @param name
	 *            Name
	 */
	public User(String name) {
		super(name);
	}

	/**
	 * User legt Karten ab
	 */
	@Override
	public Stapel[] ablegen(Stapel[] stapel, Spiel spiel) {
		Scanner scanner = new Scanner(System.in);
		int gewuenschteKarte = 0;
		int gewuenschterStapel = 0;
		String wunsch;
		boolean legen = true;
		boolean error = false;

		while (legen && karten.size() > 0 && zugMoeglich(stapel)) {
			System.out.println("Welche Karte möchten sie auf welchen Stapel legen?");
			System.out.println("Ihre Karten: ");
			kartenStapelAusgabe(spiel);
			System.out.println("Die wie vielte Karte wollen Sie legen?");
			
			//Karte abfragen
			do {
				try {
					gewuenschteKarte = scanner.nextInt();
					error = false;
				} catch (InputMismatchException ex) {
					System.out.println("Ungültige Karte, bitte erneut eingeben: ");
					scanner.nextLine();
					error = true;
				}
			} while (error == true);

			while (gewuenschteKarte < 0 || gewuenschteKarte > karten.size()) {
				System.out.println("Ungültige Karte, bitte erneut eingeben: ");
				gewuenschteKarte = scanner.nextInt();
			}

			//Stapel abfragen
			System.out.println("Auf welchen Stapel?");
			do {
				try {
					gewuenschterStapel = scanner.nextInt();
					error = false;
				} catch (InputMismatchException ex) {
					System.out.println("Ungültige Karte, bitte erneut eingeben: ");
					scanner.nextLine();
					error = true;
				}
			} while (error == true);

			while (gewuenschterStapel < 1 || gewuenschterStapel > 4) {
				System.out.println("Ungültiger Stapel, bitte erneut eingeben: ");
				gewuenschterStapel = scanner.nextInt();
			}

			//Prüft ob Zug überhaupt möglich
			if (stapel[gewuenschterStapel - 1] instanceof HochzaehlStapel
					&& karten.get(gewuenschteKarte - 1).getWert() > stapel[gewuenschterStapel - 1].peek().getWert()) {
				karteAblegen(karten.get(gewuenschteKarte - 1), stapel[gewuenschterStapel - 1]);
			} else if (stapel[gewuenschterStapel - 1] instanceof RunterzaehlStapel
					&& karten.get(gewuenschteKarte - 1).getWert() < stapel[gewuenschterStapel - 1].peek().getWert()) {
				karteAblegen(karten.get(gewuenschteKarte - 1), stapel[gewuenschterStapel - 1]);
			} else {
				System.out.println("Ungültiger Zug, bitte neue Wahl treffen!");
				continue;
			}
			
			//weiter Karten legen?
			if (!(getGelegteKarten() < spiel.getVariante().getMindestAnzahlKarten())) {
				kartenStapelAusgabe(spiel);
				System.out.println("Wollen Sie eine weitere Karte ablegen? 'Ja' / 'Nein'");
				wunsch = scanner.next();
				legen = (wunsch.equalsIgnoreCase("ja")) ? true : false;
			} 
		}

		return stapel;
	}

	public void kartenStapelAusgabe(Spiel spiel) {
		int i = 1;
		for (Karte karte : karten) {
			System.out.print(i + ": " + karte.getWert() + "  ");
			i++;
		}
		System.out.println();
		spiel.akutelleStapelAusgeben();
	}

	public boolean zugMoeglich(Stapel[] stapel) {
		int moeglicheZuege = 0;
		List<Karte> kartenKopie=karten;
		for (Stapel aktuellerStapel : stapel) {
			for (int i=0; i<kartenKopie.size();i++) {
				if (aktuellerStapel instanceof HochzaehlStapel && kartenKopie.get(i).getWert() > aktuellerStapel.peek().getWert()) {
					moeglicheZuege++;
					kartenKopie.remove(kartenKopie.get(i));
				} else if (aktuellerStapel instanceof RunterzaehlStapel
						&& kartenKopie.get(i).getWert() < aktuellerStapel.peek().getWert()) {
					moeglicheZuege++;
					kartenKopie.remove(kartenKopie.get(i));
				}
			}
		}
		return (moeglicheZuege >= 2) ? true : false;
	}

}
