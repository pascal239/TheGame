 package thegane.bestandteile.stapel;

public abstract class Stapel {
	/**
	 * Stapel von 2 bis 100
	 */
	protected Karte[] stapel = new Karte[99];
	
	//aktuelle oberste, belegte Position auf dem Stack
	private int pos = 0;
	
	private String name;
	
	public Stapel(String name) {
		this.name = name;
	}

	/**
	 * legt eine neue Zahl auf den Stapel
	 * @param neues Objekt
	 */
	public void push(Karte karte) {
		if (pos < stapel.length) {
			stapel[++pos] = karte;
		}
	}

	/**
	 * gibt oberste Zahl zurück
	 * @return oberstes Objekt
	 */
	public Karte peek() {
		return stapel[pos];
	}
	
	/**
	 * liefert Anzahl der Karten die draufliegen
	 * @return Größe
	 */
	public int size() {
		return pos + 1;
	}
	public String getName() {
		return name;
	}
	
}
