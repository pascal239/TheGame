package thegane.bestandteile.stapel;

public class Karte {
	
	/** Wert der Karte*/
	private int wert;
	
	/**
	 * legt neue Karte an
	 * @param wert Wert der Karte
	 */
	public Karte(int wert) {
		this.wert = wert;
	}

	public int getWert() {
		return wert;
	}

	public void setWert(int wert) {
		this.wert = wert;
	}
}
