package mp.codicefiscale.model;

import java.time.LocalDate;

public class Mesi {

	/*
	 * Ritorna la lettera del mese
	 */
	public static String getMonth(LocalDate dataNascita) {

		String mese = "";

		switch (dataNascita.getMonth()) {
		case JANUARY:
			mese = "A";
			break;
		case FEBRUARY:
			mese = "B";
			break;
		case MARCH:
			mese = "C";
			break;
		case APRIL:
			mese = "D";
			break;
		case MAY:
			mese = "E";
			break;
		case JUNE:
			mese = "H";
			break;
		case JULY:
			mese = "L";
			break;
		case AUGUST:
			mese = "M";
			break;
		case SEPTEMBER:
			mese = "P";
			break;
		case OCTOBER:
			mese = "R";
			break;
		case NOVEMBER:
			mese = "S";
			break;
		case DECEMBER:
			mese = "T";
			break;
		default:
			break;
		}

		return mese;
	}
}
