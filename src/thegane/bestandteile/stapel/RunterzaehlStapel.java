package thegane.bestandteile.stapel;

public class RunterzaehlStapel extends Stapel{
	/**
	 * legt neuen RunterzaehlStapel an
	 */
	public RunterzaehlStapel(String name) {
		super(name);
		this.stapel[0] = new Karte(100);
	}
}
