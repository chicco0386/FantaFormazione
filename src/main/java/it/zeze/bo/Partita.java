package it.zeze.bo;

public class Partita {

	private String squadraCasa;
	private String squadraFuoriCasa;

	public String getSquadraAvversaria(String squadra) {
		String squadraAvvToReturn = "";
		if (squadraCasa.equalsIgnoreCase(squadra)) {
			squadraAvvToReturn = squadraFuoriCasa.toLowerCase();
		} else if (squadraFuoriCasa.equalsIgnoreCase(squadra)) {
			squadraAvvToReturn = squadraCasa.toUpperCase();
		}
		return squadraAvvToReturn;
	}

	public boolean isInCasa(String squadra) {
		if (squadraCasa.equalsIgnoreCase(squadra)) {
			return true;
		}
		return false;
	}
	
	public boolean isFuoriCasa(String squadra) {
		if (squadraFuoriCasa.equalsIgnoreCase(squadra)) {
			return true;
		}
		return false;
	}

	public String getSquadraCasa() {
		return squadraCasa;
	}

	public void setSquadraCasa(String squadraCasa) {
		this.squadraCasa = squadraCasa;
	}

	public String getSquadraFuoriCasa() {
		return squadraFuoriCasa;
	}

	public void setSquadraFuoriCasa(String squadraFuoriCasa) {
		this.squadraFuoriCasa = squadraFuoriCasa;
	}

}
