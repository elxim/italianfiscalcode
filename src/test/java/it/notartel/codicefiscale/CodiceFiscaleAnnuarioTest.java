package it.notartel.codicefiscale;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import mp.codicefiscale.CodiceFiscaleApplication;
import mp.codicefiscale.core.CodiceFiscale;
import mp.codicefiscale.model.Constants.Sesso;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=CodiceFiscaleApplication.class)
public class CodiceFiscaleAnnuarioTest {
	
	@Autowired
	private CodiceFiscale cf;

	@Before
	public void init() {
		
	}
	
	
	//@Ignore
	@Test
	public  void test() throws IOException {

		Map<String, String> notai = loadAnnuarioProperties();
				
		AtomicInteger counterError = new AtomicInteger(0);
		AtomicInteger counterGood = new AtomicInteger(0);
		notai.forEach((k, v) -> {
			
			//System.out.print(k+"\n");
			List<String> dati = getTokens(v);
			//System.out.println(k + "=" + dati);

			
			cf.setCognomi(Arrays.asList(dati.get(0)));
			cf.setNomi(Arrays.asList(dati.get(1)));
			cf.setDataNascita(dati.get(2));
			cf.setSex((dati.get(3).equalsIgnoreCase("M"))?Sesso.M:Sesso.F);
			cf.setLuogoNascita((dati.get(5).trim().equalsIgnoreCase("EE"))?dati.get(6):dati.get(4));
			cf.setSiglaProvincia(dati.get(5));
			
			try {
				
				String cfRetrived = cf.calculate();
				if (k.toUpperCase().equals(cfRetrived)) {
					//System.out.println(counterGood.getAndIncrement()+") "+k.toUpperCase() + "=" + dati);
					//System.out.println(counterGood.getAndIncrement());
				}
				else
					System.err.println(counterError.getAndIncrement()+") "+cfRetrived+":"+k.toUpperCase() + "=" + dati);
					//System.err.println(counterError.getAndIncrement());
				//assertEquals(k.toUpperCase(),cf.build().calculate());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		});

	}

	
	//@Ignore
	@Test
	public void check() throws IOException {

		Map<String, String> notai = loadAnnuarioProperties();
	
		AtomicInteger counterError = new AtomicInteger(0);
		AtomicInteger counterGood = new AtomicInteger(0);
		notai.forEach((k, v) -> {
			//System.out.print(k+"\n");
			List<String> dati = getTokens(v);
			//System.out.println(k + "=" + dati);
			
			CodiceFiscale cf = new CodiceFiscale();

				
				if (cf.check(k)) {
					//System.out.println(counterGood.getAndIncrement()+") "+k.toUpperCase() + "=" + dati);
					//System.out.println(counterGood.getAndIncrement());
				}
				else
					System.err.println(counterError.getAndIncrement()+") "+k.toUpperCase() + "=" + dati);
					//System.err.println(counterError.getAndIncrement());
				//assertEquals(k.toUpperCase(),cf.build().calculate());

			
			
		});

	}

	
	
	
	private List<String> getTokens(String str) {
		List<String> tokens = new ArrayList<>();
		StringTokenizer tokenizer = new StringTokenizer(str, ":");
		while (tokenizer.hasMoreElements()) {
			tokens.add(tokenizer.nextToken());
		}
		return tokens;
	}

	// Legge i codici degli stati esteri
	private static Map<String, String> loadAnnuarioProperties() throws IOException {
		Map<String, String> propertyMap = new HashMap<String, String>();

		Properties prop = new Properties();
		try (InputStream inputStream = CodiceFiscale.class.getResourceAsStream("/annuario.properties")) {

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
