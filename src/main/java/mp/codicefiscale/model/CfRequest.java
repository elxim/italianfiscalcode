package mp.codicefiscale.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

/**
 * Modello della richiesta di un codice fiscale
 * @author mpizza
 *
 */
@Data
public class CfRequest {

	@NotNull(message = "Campo nome mancante.")
    @NotBlank(message = "Campo nome è vuoto.")
	@Size(min = 2, max = 150, message="Il campo nome deve essere compreso tra 2 e 150 caratteri.")
	String nome;
	
	@NotNull(message = "Campo cognome mancante.")
    @NotBlank(message = "Campo cognome è vuoto.")
	@Size(min = 2, max = 150, message="Il campo cognome deve essere compreso tra 2 e 150 caratteri.")
	String cognome;
	
	//@Pattern(regexp="^(((0|1|2|3)\\d{1})-(0|1)\\d{1})-((19|20)\\d{2})", message="La data deve essere nel formato GG-MM-AAAA .")
	@Pattern(regexp="(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})", message="La data deve essere nel formato GG-MM-AAAA .")
	@NotNull(message = "Campo data mancante.")
    @NotBlank(message = "Campo data è vuoto.")
	@Size(min = 10, max = 10, message="Il campo data deve essere nel formato GG-MM-AAAA .")
	String dataNascita;
	
	@Pattern(regexp="^[MFmf]{1}$", message="Il sesso deve essere un carattere compreso tra M o F .")
	@NotNull(message = "Campo sesso mancante.")
    @NotBlank(message = "Campo sesso è vuoto.")
	String sesso;

	@NotNull(message = "Campo luogo di nascita mancante.")
    @NotBlank(message = "Campo luogo di nascita è vuoto.")
	@Size(min = 2, max = 150, message="Il campo luogo di nascita deve essere compreso tra 2 e 150 caratteri.")
	String luogoNascita;
	
	@NotNull(message = "Campo sigla è mancante.")
    @NotBlank(message = "Campo sigla è vuoto.")
	@Size(min = 2, max = 2, message="Il campo sigla deve essere di 2 carateeri, EE per gli stati esteri.")
	String siglaProvincia;

}
