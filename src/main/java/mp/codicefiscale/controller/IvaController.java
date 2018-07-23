package mp.codicefiscale.controller;

import java.util.Collections;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.log4j.Log4j;


@Controller
@Log4j
public class IvaController {
	
	
	
	
	/**
	 * Verifica la correttezza della partita 
	 * 
	 * @param cf
	 * @return
	 */
	@RequestMapping(value = "/controllaiva/{iva}", method = RequestMethod.GET)
	@ResponseBody
	@CrossOrigin
	public Map<String, Boolean> check(@PathVariable("iva") String iva2Validate) {
		
		log.info("/controllaiva ".concat(iva2Validate));


		String result = ControllaPIVA(iva2Validate);
		
		
		log.info("check -> ".concat(result.length()>0?"false":"true"));
		

		return (result.length()>0 ? Collections.singletonMap(result, false) : Collections.singletonMap("valid", true));
	}

	
	
	
	static String ControllaPIVA(String pi)
	{
		int i, c, s;
		if( pi.length() == 0 )  return "";
		if( pi.length() != 11 )
			return "lenght<>11";
		for( i=0; i<11; i++ ){
			if( pi.charAt(i) < '0' || pi.charAt(i) > '9' )
				return "nonumeric";
		}
		s = 0;
		for( i=0; i<=9; i+=2 )
			s += pi.charAt(i) - '0';
		for( i=1; i<=9; i+=2 ){
			c = 2*( pi.charAt(i) - '0' );
			if( c > 9 )  c = c - 9;
			s += c;
		}
		if( ( 10 - s%10 )%10 != pi.charAt(10) - '0' )
			return "cin";
		
		if (pi.equals("00000000000")) return "00000000000 invalid";
		
		return "";
	}
	
	

}
