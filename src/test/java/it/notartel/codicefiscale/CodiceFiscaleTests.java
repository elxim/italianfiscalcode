package it.notartel.codicefiscale;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

import mp.codicefiscale.core.CodiceFiscale;
import mp.codicefiscale.core.CodiceFiscale.CodiceFiscaleBuilder;
import mp.codicefiscale.model.Constants.Sesso;
import mp.codicefiscale.service.IstatProxyService;

//@RunWith(SpringRunner.class)
//@SpringBootTest
//@ContextConfiguration(classes = RadsProperties.class)
public class CodiceFiscaleTests {

	@Test
	public void testStep1() throws JsonProcessingException, IOException {

		CodiceFiscaleBuilder cf1 = CodiceFiscale.builder().cognomi(Arrays.asList("Pizza"))
				.nomi(Arrays.asList("Michele")).dataNascita("23-06-1976").sex(Sesso.M).luogoNascita("Napoli")
				.siglaProvincia("NA");
		CodiceFiscaleBuilder cf2 = CodiceFiscale.builder().cognomi(Arrays.asList("Espostio"))
				.nomi(Arrays.asList("Michela")).dataNascita("13-09-1979").sex(Sesso.F).luogoNascita("Napoli")
				.siglaProvincia("NA");

		CodiceFiscaleBuilder cf3 = CodiceFiscale.builder().cognomi(Arrays.asList("Fo"))
				.nomi(Arrays.asList("Gian", "Luca")).dataNascita("26-11-2009").sex(Sesso.M).luogoNascita("Roma")
				.siglaProvincia("RM");
		CodiceFiscaleBuilder cf4 = CodiceFiscale.builder().cognomi(Arrays.asList("D'Ardia")).nomi(Arrays.asList("Anna"))
				.dataNascita("13-09-1979").sex(Sesso.F).luogoNascita("Ottaviano").siglaProvincia("IT");

		assertEquals("PZZMHL76H23F839G".toUpperCase(), cf1.build().calculate());
		assertEquals("SPSMHL79P53F839M".toUpperCase(), cf2.build().calculate());
		assertEquals("FOXGLC09S26H501T".toUpperCase(), cf3.build().calculate());
		assertEquals("DRDNNA79P53G190R".toUpperCase(), cf4.build().calculate());

		
		List<String> omocodici = new ArrayList<String>();
		
		omocodici.add("PZZMHL76H23F83VD");
		omocodici.add("PZZMHL76H23F8P9S");
		omocodici.add("PZZMHL76H23FU39D");
		omocodici.add("PZZMHL76H2PF839C");
		omocodici.add("PZZMHL76HN3F839R");
		omocodici.add("PZZMHL7SH23F839S");
		omocodici.add("PZZMHLT6H23F839G");

		
		assertEquals(omocodici, cf1.build().calculateOmocodici("PZZMHL76H23F839G"));

	}

	@Test
	public void testIstatProxy() throws JsonProcessingException, IOException {
		IstatProxyService istat = new IstatProxyService();

		String codiceOmonimi = istat.getCodiceComune("Francavilla al Mare", "CH");
		String codiceOttaviano = istat.getCodiceComune("Ottaviano", "NA");
		String codiceNapoli = istat.getCodiceComune("Napoli", "Na");
		String codiceRoma = istat.getCodiceComune("Roma", "RM");

		assertEquals("D763", codiceOmonimi);
		assertEquals("G190", codiceOttaviano);
		assertEquals("F839", codiceNapoli);
		assertEquals("H501", codiceRoma);

	}

}
