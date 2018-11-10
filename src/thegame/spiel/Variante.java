package thegame.spiel;

public enum Variante {
	NORMAL(2,6,1), PROFI(3,6,1), EXTREM(3,5,1);
	
	private int mindestAnzahlKarten;
	private int handKarten;
	private int mindestAnzahlKartenStapelLeer;
	
	private Variante(int mindestAnzahlKarten, int handKarten, int mindestAnzahlKartenStapelLeer) {
		this.mindestAnzahlKarten = mindestAnzahlKarten;
		this.handKarten = handKarten;
		this.mindestAnzahlKartenStapelLeer = mindestAnzahlKartenStapelLeer;
	}
	
	public int getMindestAnzahlKarten() {
		return mindestAnzahlKarten;
	}

	public int getHandKarten() {
		return handKarten;
	}

	public int getMindestAnzahlKartenStapelLeer() {
		return mindestAnzahlKartenStapelLeer;
	}
}
