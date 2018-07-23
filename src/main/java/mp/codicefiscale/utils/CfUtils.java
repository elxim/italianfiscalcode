package mp.codicefiscale.utils;

import java.text.Normalizer;
import java.util.Optional;

public class CfUtils {

	final static String REGEXPR = "(?:[B-DF-HJ-NP-TV-Z](?:[AEIOU]{2}"+
									"|[AEIOU]X)|[AEIOU]{2}X|[B-DF-HJ-NP-TV-Z]{2}[A-Z]{1})"+
									"{2}[\\dLMNP-V]{2}(?:[A-EHLMPR-T](?:[04LQ][1-9MNP-V]|[1256LMRS][\\dLMNP-V])|[DHPS][37PT][0L]|[ACELMRT][37PT][01LM])(?:[A-MZ][1-9MNP-V][\\dLMNP-V]{2}|[A-M][0L][\\dLMNP-V][\\dMNP-V])"+
									"[A-Z]{1}";

	
	// PSQPLA56B18L050Z
	
	public static String stripAccents(String s) {
		s = Normalizer.normalize(s, Normalizer.Form.NFD);
		s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
		return s;
	}

	public static String removeLastCharRegexOptional(String s) {
		return Optional.ofNullable(s).map(str -> str.replaceAll(".$", "")).orElse(s);
	}

	public static boolean verificaPattern(String cf) {
		return cf.matches(REGEXPR);
	}

}
