package thegane.bestandteile.spieler;

import java.util.ArrayList;
import java.util.List;

import thegame.spiel.Spiel;
import thegane.bestandteile.stapel.Karte;
import thegane.bestandteile.stapel.Stapel;

public abstract class Spieler {
	/** Name des Spielers */
	private String name;

	// von spielart abhängig
	protected List<Karte> karten = new ArrayList<>();
	
	
	public void setKarten(List<Karte> karten) {
		this.karten = karten;
	}

	/** Anzahl der während des Zugs gelegten Karten */
	private int gelegteKarten;

	/**
	 * legt einen neuen Spieler an
	 * @param name Name des Spielers
	 */
	public Spieler(String name) {
		this.name = name;
	}

	/**
	 * legt Karten ab
	 * @param stapel alle Stapel (2HR + 2RZ)
	 * @param spiel Spiel
	 */
	public abstract Stapel[] ablegen(Stapel[] stapel, Spiel spiel);

	/**
	 * Karte ziehen
	 * @param ziehstapel Ziehstapel
	 */
	public List<Karte> ziehen(Spiel spiel) {
		int r;
		int i = karten.size();
		while (i < spiel.getVariante().getHandKarten() && spiel.getZiehstapel().size() > 0) {
			r = (int) (Math.random() * (spiel.getZiehstapel().size()));
			karten.add(spiel.getZiehstapel().get(r));
			spiel.getZiehstapel().remove(r);
			i++;
		}
		return spiel.getZiehstapel();
	}
	
	/**
	 * berechnet Abstand zwischen Karte und oberste Karte des Stapels
	 * 
	 * @param karte
	 *            aktuelle Karte
	 * @param stapel
	 *            aktueller Stapel
	 * @return Abstand
	 */
	public int abstand(Karte karte, Stapel stapel) {
		return karte.getWert() - stapel.peek().getWert();
	}
	
	public List<Karte> getKarten() {
		return karten;
	}

	public void karteAblegen(Karte aktuelleKarte, Stapel aktuellerStapel) {
		aktuellerStapel.push(aktuelleKarte);
		karten.remove(aktuelleKarte);
		System.out.println(this.getName() + " hat auf den " + aktuellerStapel.getName() + " eine " + aktuelleKarte.getWert() + " gelegt.");
		setGelegteKarten(getGelegteKarten() + 1);
	}
	
	public boolean containsKarte(int wertKarte) {
		for(Karte karte : karten) {
			if(karte.getWert() == wertKarte) {
				return true;
			}
		}
		return false;
	}
	
	public int getGelegteKarten() {
		return gelegteKarten;
	}

	public void setGelegteKarten(int gelegteKarten) {
		this.gelegteKarten = gelegteKarten;
	}
	
	public String getName() {
		return name;
	}

}
