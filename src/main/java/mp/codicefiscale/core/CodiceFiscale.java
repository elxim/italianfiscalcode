package mp.codicefiscale.core;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import mp.codicefiscale.model.Cin;
import mp.codicefiscale.model.Constants;
import mp.codicefiscale.model.Constants.Sesso;
import mp.codicefiscale.model.Mesi;
import mp.codicefiscale.model.Omocodici;
import mp.codicefiscale.service.IstatProxyService;
import mp.codicefiscale.utils.CfUtils;

/**
 * 
 * Classe per il calcolo del codice fiscale
 * 
 * @TODO il controllo dei caratteri accentati
 * 
 * @author mpizza
 *
 */
@AllArgsConstructor
@Data
@Builder
@Component
public class CodiceFiscale {
	
	final static String CONSONANTS = "bcdfghjklmnpqrstvwxyz" + "BCDFGHJKLMNPQRSTVWXYZ";

	private List<String> cognomi;

	private List<String> nomi;
	private String dataNascita;
	private Sesso sex;
	private String luogoNascita;
	private String siglaProvincia;
	
	@Autowired
	IstatProxyService istat;
	
	public CodiceFiscale() {
		
	}

	public List<String> calculateOmocodici(String cf) {

		List<String> omocodici = new ArrayList<String>();

		// Prima sostituzione
		StringBuilder cfomonimi1 = new StringBuilder(cf);
		cfomonimi1.setCharAt(14, Omocodici.changeNumber(cf.charAt(14)));
		String cin = step7(cfomonimi1.toString().substring(0, 15));
		cfomonimi1.setCharAt(15, cin.charAt(0));
		// Seconda sostituzione
		StringBuilder cfomonimi2 = new StringBuilder(cf);
		cfomonimi2.setCharAt(13, Omocodici.changeNumber(cf.charAt(13)));
		cin = step7(cfomonimi2.toString().substring(0, 15));
		cfomonimi2.setCharAt(15, cin.charAt(0));
		// Terza sostituzione
		StringBuilder cfomonimi3 = new StringBuilder(cf);
		cfomonimi3.setCharAt(12, Omocodici.changeNumber(cf.charAt(12)));
		cin = step7(cfomonimi3.toString().substring(0, 15));
		cfomonimi3.setCharAt(15, cin.charAt(0));
		// Quarta sostituzione
		StringBuilder cfomonimi4 = new StringBuilder(cf);
		cfomonimi4.setCharAt(10, Omocodici.changeNumber(cf.charAt(10)));
		cin = step7(cfomonimi4.toString().substring(0, 15));
		cfomonimi4.setCharAt(15, cin.charAt(0));
		// Quinta sostituzione
		StringBuilder cfomonimi5 = new StringBuilder(cf);
		cfomonimi5.setCharAt(9, Omocodici.changeNumber(cf.charAt(9)));
		cin = step7(cfomonimi5.toString().substring(0, 15));
		cfomonimi5.setCharAt(15, cin.charAt(0));
		// Sesta sostituzione
		StringBuilder cfomonimi6 = new StringBuilder(cf);
		cfomonimi6.setCharAt(7, Omocodici.changeNumber(cf.charAt(7)));
		cin = step7(cfomonimi6.toString().substring(0, 15));
		cfomonimi6.setCharAt(15, cin.charAt(0));
		// Settima sostituzione
		StringBuilder cfomonimi7 = new StringBuilder(cf);
		cfomonimi7.setCharAt(6, Omocodici.changeNumber(cf.charAt(6)));
		cin = step7(cfomonimi7.toString().substring(0, 15));
		cfomonimi7.setCharAt(15, cin.charAt(0));
		
		
		
		
		omocodici.add(cfomonimi1.toString());
		omocodici.add(cfomonimi2.toString());
		omocodici.add(cfomonimi3.toString());
		omocodici.add(cfomonimi4.toString());
		omocodici.add(cfomonimi5.toString());
		omocodici.add(cfomonimi6.toString());
		omocodici.add(cfomonimi7.toString());
		
		
		return omocodici;

	}

	public boolean check(String cf) {

		String cf15 = cf.substring(0, 15).toUpperCase();
		String cin = cf.substring(15, 16);

		return cin.equalsIgnoreCase(step7(cf15));

	}

	public String calculate() throws JsonProcessingException, IOException {

		String step1 = step1(cognomi);
		String step2 = step2(nomi);
		String step3 = step3(dataNascita);
		String step4 = step4(dataNascita);
		String step5 = step5(dataNascita);
		String step6 = step6(luogoNascita, siglaProvincia);

		String cf15 = step1.concat(step2).concat(step3).concat(step4).concat(step5).concat(step6);

		// Carattere di controllo
		String step7 = step7(cf15);

		return cf15.concat(step7);
	}

	/*
	 * codice fiscale 16 caratteri
	 * 
	 * step 1 : cognome step 2 : nome step 3 : anno di nascita step 4 : mese di
	 * nascita step 5 : sesso giorno di nascita step 6 : luogo di nascita step 7
	 * : carattere di controllo
	 * 
	 * 
	 */

	/*
	 * Output di 3 lettere per il cognome. Bisogna prendere la prima, la seconda
	 * e la terza consonante. Però potrebbe anche succedere che ci siano solo
	 * due consonanti oppure una sola; in tal caso dopo aver preso le consonanti
	 * si inizia a prendere anche le vocali. Se ancora mancano altre lettere per
	 * completare la nostra stringa di tre caratteri si aggiunge la lettera X. I
	 * cognomi composti da più parole vanno considerati come se fossero una sola
	 * parola.
	 */
	private static String step1(@NonNull List<String> cognomi) {

		String cognome = "";
		String cognomeUnico = cognomi.stream().map(Object::toString).collect(Collectors.joining());
		cognomeUnico = cognomeUnico.toUpperCase();

		// Prendo tutte le consonanti
		for (int i = 0; i < cognomeUnico.length(); i++) {
			if (CONSONANTS.indexOf(cognomeUnico.charAt(i)) != -1)
				cognome = cognome + cognomeUnico.charAt(i);
		} // end for
		// se non ho ottenuto i 3 caratteri aggiungo le vocali
		if (cognome.length() < 3)
			for (int i = 0; i < cognomeUnico.length(); i++) {
				if (CONSONANTS.indexOf(cognomeUnico.charAt(i)) == -1 && cognome.length() < 3)
					cognome = cognome + cognomeUnico.charAt(i);
			}

		// se ancora non ho ottenuto i 3 caratteri aggiungo il carattere X
		while (cognome.length() < 3)
			cognome += 'X';
		// Restituisco solo i primi 3 caratteri
		return CfUtils.stripAccents(cognome.substring(0, 3));
	}

	/*
	 * Il procedimento che si utilizza per ricavare le tre lettere del nome, è
	 * uguale a quello del cognome con l'unica differenza che adesso dobbiamo
	 * prendere la prima, la terza e la quarta consonante. Nel caso non ci sono
	 * quattro consonanti, si prendono le prime tre e se ci sono meno di tre
	 * consonanti si effettua lo stesso procedimento del cognome.
	 */
	private String step2(@NonNull List<String> nomi) {

		String nome = "";

		String nomeUnico = nomi.stream().map(Object::toString).collect(Collectors.joining());

		nomeUnico = nomeUnico.toUpperCase();

		int consonants = 0;
		int contaConsonanti = 0;

		for (int i = 0; i < nomeUnico.length(); i++) {
			if (CONSONANTS.indexOf(nomeUnico.charAt(i)) != -1)
				contaConsonanti++;
		}

		// Prendo tutte le consonanti tranne la seconda se c'è la terza e la
		// quarta
		for (int i = 0; i < nomeUnico.length(); i++) {

			if (CONSONANTS.indexOf(nomeUnico.charAt(i)) != -1) {
				consonants++;

				if (consonants == 3 && contaConsonanti > 3) {

					nome = CfUtils.removeLastCharRegexOptional(nome) + nomeUnico.charAt(i);
				} else
					nome = nome + nomeUnico.charAt(i);
			}
		} // end for

		// se non ho ottenuto i 3 caratteri aggiungo le vocali
		if (nome.length() < 3)
			for (int i = 0; i < nomeUnico.length(); i++) {
				if (CONSONANTS.indexOf(nomeUnico.charAt(i)) == -1 && nome.length() < 3) {
					nome = nome + nomeUnico.charAt(i);
				}
			}

		// se ancora non ho ottenuto i 3 caratteri aggiungo il carattere X

		while (nome.length() < 3) {
			nome += 'X';
		}

		// Restituisco solo i primi 3 caratteri
		return CfUtils.stripAccents(nome.substring(0, 3));
	}

	/*
	 * I due numeri dell'anno di nascita sono semplicemente le sue ultime due
	 * cifre.
	 */
	private String step3(String data) {
		LocalDate dataNascita = LocalDate.parse(data, Constants.formatter);

		return String.valueOf(dataNascita.getYear()).substring(2);

	}

	/*
	 * Inserisco il carattere preso dalla tabella delle lettere corrispondenti
	 * ai mesi
	 */
	private String step4(String data) {
		LocalDate dataNascita = LocalDate.parse(data, Constants.formatter);

		return Mesi.getMonth(dataNascita);

	}

	/*
	 * Giorno di nascita e sesso (due cifre): si prendono le due cifre del
	 * giorno di nascita (se è compreso tra 1 e 9 si pone uno zero come prima
	 * cifra); per i soggetti di sesso femminile, a tale cifra va sommato il
	 * numero 40. In questo modo il campo contiene la doppia informazione giorno
	 * di nascita e sesso.
	 */
	private String step5(String data) {
		LocalDate dataNascita = LocalDate.parse(data, Constants.formatter);

		if (sex.equals(Sesso.M))
			return String.valueOf(new DecimalFormat("00").format(dataNascita.getDayOfMonth()));
		else
			return String.valueOf(dataNascita.getDayOfMonth() + 40);

	}

	
	private String step6(String comune, String sigla) throws JsonProcessingException, IOException {

		String codice = "----";
		
		sigla = sigla.toUpperCase();

		if (sigla.length() == 2 && !sigla.equalsIgnoreCase("EE")) 
			codice = istat.getCodiceComune(comune, sigla);
		 else { // Cerco nella mappa degli stati esteri
			Map<String, String> codiciEsteri = loadStatiEsteriProperties();
			String statoNormalizato = comune.trim().replaceAll("[^a-zA-Z]", "").toLowerCase();
			if (codiciEsteri.containsKey(statoNormalizato))
				codice = "Z" + codiciEsteri.get(statoNormalizato);
		}

		return codice;
	}

	// step7 calcolo del codice di controllo
	private static String step7(String cf) {

		// Parte Dispari

		int cin = 0;
		int counter = 0;

		do { // il modulo
			if (counter % 2 == 0)
				cin += Cin.cinDispari(cf.charAt(counter++));
			else
				cin += Cin.cinPari(cf.charAt(counter++));
		} while (counter < 15);

		return Cin.convertCin(cin);
	}

	// Legge i codici degli stati esteri
	private static Map<String, String> loadStatiEsteriProperties() throws IOException {
		Map<String, String> propertyMap = new HashMap<String, String>();
		Properties prop = new Properties();
		try (InputStream inputStream = CodiceFiscale.class.getResourceAsStream("/codicistatiesteri.properties")) {
			prop.load(inputStream);
			Enumeration<?> keys = prop.propertyNames();
			while (keys.hasMoreElements()) {
				String key = (String) keys.nextElement();
				propertyMap.put(key.toLowerCase().trim(), prop.getProperty(key));
			}
			return propertyMap;
		}
	}
}
