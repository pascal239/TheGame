package thegane.bestandteile.stapel;

public class HochzaehlStapel extends Stapel{
	/**
	 * legt neuen HochzaehlStapel an
	 */
	public HochzaehlStapel(String name) {
		super(name);
		this.stapel[0] = new Karte(1);
	}
}
