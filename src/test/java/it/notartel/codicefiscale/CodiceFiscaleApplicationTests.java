package it.notartel.codicefiscale;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import mp.codicefiscale.utils.CfUtils;

@RunWith(SpringRunner.class)
public class CodiceFiscaleApplicationTests {

	@Test
	public void contextLoads() {
		
		assertTrue("ERRORE DI VALUTAZIONE",CfUtils.verificaPattern("PZZMHL76H23F839G"));
		assertFalse("ERRORE DI VALUTAZIONE",CfUtils.verificaPattern("12345676H23F839G"));
		assertTrue("ERRORE DI VALUTAZIONE",CfUtils.verificaPattern("PZZXXX45H23F839G"));
		assertFalse("ERRORE DI VALUTAZIONE",CfUtils.verificaPattern("ZZPM7LH6H23F839G"));
	}

}

