package mp.codicefiscale.model;

public class Omocodici {

		// Tabella Omocodici
		public static char changeNumber(char number) {

			switch (number) {
			case '0':
				return 'L';
			case '1':
				return 'M';
			case '2':
				return 'N';
			case '3':
				return 'P';
			case '4':
				return 'Q';
			case '5':
				return 'R';
			case '6':
				return 'S';
			case '7':
				return 'T';
			case '8':
				return 'U';
			case '9':
				return 'V';
			default:
				return '-';
			}
		}
}
