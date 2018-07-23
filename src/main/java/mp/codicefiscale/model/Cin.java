package mp.codicefiscale.model;

public class Cin {

	// Converti Cin
	public static String convertCin(int cin) {

		switch (cin % 26) {
		case 0:
			return "A";
		case 1:
			return "B";
		case 2:
			return "C";
		case 3:
			return "D";
		case 4:
			return "E";
		case 5:
			return "F";
		case 6:
			return "G";
		case 7:
			return "H";
		case 8:
			return "I";
		case 9:
			return "J";
		case 10:
			return "K";
		case 11:
			return "L";
		case 12:
			return "M";
		case 13:
			return "N";
		case 14:
			return "O";
		case 15:
			return "P";
		case 16:
			return "Q";
		case 17:
			return "R";
		case 18:
			return "S";
		case 19:
			return "T";
		case 20:
			return "U";
		case 21:
			return "V";
		case 22:
			return "W";
		case 23:
			return "X";
		case 24:
			return "Y";
		case 25:
			return "Z";
		default:
			return "?";

		}

	}

	// Calocolo cin Dispari
	public static int cinDispari(char c) {
		switch (c) {
		case '0':
			return 1;
		case '1':
			return 0;
		case '2':
			return 5;
		case '3':
			return 7;
		case '4':
			return 9;
		case '5':
			return 13;
		case '6':
			return 15;
		case '7':
			return 17;
		case '8':
			return 19;
		case '9':
			return 21;
		case 'A':
			return 1;
		case 'B':
			return 0;
		case 'C':
			return 5;
		case 'D':
			return 7;
		case 'E':
			return 9;
		case 'F':
			return 13;
		case 'G':
			return 15;
		case 'H':
			return 17;
		case 'I':
			return 19;
		case 'J':
			return 21;
		case 'K':
			return 2;
		case 'L':
			return 4;
		case 'M':
			return 18;
		case 'N':
			return 20;
		case 'O':
			return 11;
		case 'P':
			return 3;
		case 'Q':
			return 6;
		case 'R':
			return 8;
		case 'S':
			return 12;
		case 'T':
			return 14;
		case 'U':
			return 16;
		case 'V':
			return 10;
		case 'W':
			return 22;
		case 'X':
			return 25;
		case 'Y':
			return 24;
		case 'Z':
			return 23;
		default:
			return -1;
		}

	}

	// Calocolo cin Pari
	public static int cinPari(char c) {
		switch (c) {
		case '0':
			return 0;
		case '1':
			return 1;
		case '2':
			return 2;
		case '3':
			return 3;
		case '4':
			return 4;
		case '5':
			return 5;
		case '6':
			return 6;
		case '7':
			return 7;
		case '8':
			return 8;
		case '9':
			return 9;
		case 'A':
			return 0;
		case 'B':
			return 1;
		case 'C':
			return 2;
		case 'D':
			return 3;
		case 'E':
			return 4;
		case 'F':
			return 5;
		case 'G':
			return 6;
		case 'H':
			return 7;
		case 'I':
			return 8;
		case 'J':
			return 9;
		case 'K':
			return 10;
		case 'L':
			return 11;
		case 'M':
			return 12;
		case 'N':
			return 13;
		case 'O':
			return 14;
		case 'P':
			return 15;
		case 'Q':
			return 16;
		case 'R':
			return 17;
		case 'S':
			return 18;
		case 'T':
			return 19;
		case 'U':
			return 20;
		case 'V':
			return 21;
		case 'W':
			return 22;
		case 'X':
			return 23;
		case 'Y':
			return 24;
		case 'Z':
			return 25;
		default:
			return -1;
		}

	}

}
