package mp.codicefiscale.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.extern.log4j.Log4j;
import mp.codicefiscale.core.CodiceFiscale;
import mp.codicefiscale.model.CfRequest;
import mp.codicefiscale.model.CfResponse;
import mp.codicefiscale.model.Constants.Sesso;
import mp.codicefiscale.utils.CfUtils;

@Controller
@Log4j
public class CfController {

	@Autowired
	CodiceFiscale cf;

	/**
	 * Redirect sulla pagina di Swagger
	 * 
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView home() {
		return new ModelAndView("redirect:swagger-ui.html");
	}

	/**
	 * Metodo principale per il calcolo del codice fiscale
	 * 
	 * @param richiesta
	 * @return
	 * @throws JsonProcessingException
	 * @throws IOException
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/calcola")
	@ResponseBody
	@CrossOrigin
	public CfResponse calculate(@Valid @RequestBody CfRequest richiesta) throws JsonProcessingException, IOException {

		
		log.info("/calcola ".concat(richiesta.toString()));
		
		cf.setCognomi(Arrays.asList(richiesta.getCognome()));
		cf.setNomi(Arrays.asList(richiesta.getNome()));
		cf.setDataNascita(richiesta.getDataNascita());
		cf.setSex(richiesta.getSesso().equalsIgnoreCase("M") ? Sesso.M : Sesso.F);
		cf.setLuogoNascita(richiesta.getLuogoNascita());
		cf.setSiglaProvincia(richiesta.getSiglaProvincia());

		CfResponse response = new CfResponse();

		String cfRetrived = cf.calculate();

		response.setCf(cfRetrived);

		response.setCfOmocodici(cf.calculateOmocodici(cfRetrived));
		
		log.info("cf -> ".concat(response.toString()));

		return response;

	}

	/**
	 * Verifica la correttezza del codice fiscale
	 * 
	 * @param cf
	 * @return
	 */
	@RequestMapping(value = "/controlla/{cf}", method = RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public Map<String, Boolean> check(@PathVariable("cf") String cf2Validate) {
		
		log.info("/controlla ".concat(cf2Validate));

		boolean regexp = CfUtils.verificaPattern(cf2Validate.toUpperCase());
		int lenght = cf2Validate.length();

		if (lenght != 16) {
			log.info("check -> ".concat("lenght<>16: false"));
			return Collections.singletonMap("lenght<>16", false);
		}
		if (!regexp) {
			log.info("check -> ".concat("cfregexp: false"));
			return Collections.singletonMap("cfregexp", false);
		}

		boolean check = cf.check(cf2Validate.toUpperCase());
		
		log.info("check -> ".concat(check?"success":"cin:false"));
		

		return (check ? Collections.singletonMap("success", true) : Collections.singletonMap("cin", false));
	}

	@RequestMapping(method = RequestMethod.GET, value = "/echoRequest")
	@ResponseBody
	public CfRequest echoRequest() {
		CfRequest requestExample = new CfRequest();
		requestExample.setCognome("PIZZA");
		requestExample.setNome("MICHELE");
		requestExample.setDataNascita("23-06-1976");
		requestExample.setSesso("M");
		requestExample.setLuogoNascita("NAPOLI");
		requestExample.setSiglaProvincia("NA");
		return requestExample;

	}

	@RequestMapping(method = RequestMethod.GET, value = "/echoReponse")
	@ResponseBody
	public CfResponse echoResponse() throws JsonProcessingException, IOException {
		CfRequest requestExample = new CfRequest();
		requestExample.setCognome("PIZZA");
		requestExample.setNome("MICHELE");
		requestExample.setDataNascita("23-06-1976");
		requestExample.setSesso("M");
		requestExample.setLuogoNascita("NAPOLI");
		requestExample.setSiglaProvincia("NA");

		return calculate(requestExample);

	}

}
