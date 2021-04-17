
package org.springframework.cheapy.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.cheapy.model.Client;
import org.springframework.cheapy.model.FoodOffer;
import org.springframework.cheapy.model.NuOffer;
import org.springframework.cheapy.model.SpeedOffer;
import org.springframework.cheapy.model.StatusOffer;
import org.springframework.cheapy.model.TimeOffer;
import org.springframework.cheapy.model.Usuario;
import org.springframework.cheapy.service.ClientService;
import org.springframework.cheapy.service.FoodOfferService;
import org.springframework.cheapy.service.NuOfferService;
import org.springframework.cheapy.service.SpeedOfferService;
import org.springframework.cheapy.service.TimeOfferService;
import org.springframework.cheapy.service.UsuarioService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdministratorController {

	private static final String		VIEWS_USUARIO_CREATE_OR_UPDATE_FORM	= "usuarios/createOrUpdateUsuarioForm";

	private final UsuarioService	usuarioService;
	private final ClientService		clientService;

	private final FoodOfferService	foodOfferService;
	private final SpeedOfferService	speedOfferService;
	private final NuOfferService	nuOfferService;
	private final TimeOfferService	timeOfferService;


	public AdministratorController(final UsuarioService usuarioService, final ClientService clientService, final FoodOfferService foodOfferService, final SpeedOfferService speedOfferService, final NuOfferService nuOfferService,
		final TimeOfferService timeOfferService) {
		this.usuarioService = usuarioService;
		this.clientService = clientService;
		this.foodOfferService = foodOfferService;
		this.speedOfferService = speedOfferService;
		this.nuOfferService = nuOfferService;
		this.timeOfferService = timeOfferService;
	}

	@GetMapping("/administrators/usuarios{page}")
	public String processFindUsuariosForm(@PathVariable("page") final int page, final Map<String, Object> model) {
		Pageable elements = PageRequest.of(page, 10);
		Pageable nextPage = PageRequest.of(page + 1, 10);

		List<Usuario> usuarioLs = this.usuarioService.findUsuarioEnabled(elements);
		Integer next = this.usuarioService.findUsuarioEnabled(nextPage).size();
		model.put("usuarioLs", usuarioLs);
		model.put("nextPage", next);
		return "usuarios/usuariosList";
	}

	@GetMapping("/administrators/clients{page}")
	public String processFindClientesForm(@PathVariable("page") final int page, final Map<String, Object> model) {
		Pageable elements = PageRequest.of(page, 10);
		Pageable nextPage = PageRequest.of(page + 1, 10);

		List<Client> clientLs = this.clientService.findAllClient(elements);
		Integer next = this.clientService.findAllClient(nextPage).size();
		model.put("clientLs", clientLs);
		model.put("nextPage", next);
		return "clients/clientsList";
	}
	@GetMapping("/administrators/usuarios/{username}")
	public String processUsuarioShowForm(@PathVariable("username") final String username, final Map<String, Object> model) {
		Usuario usuario = this.usuarioService.findByUsername(username);
		model.put("usuario", usuario);
		return "usuarios/usuariosShow";
	}

	@GetMapping("/administrators/clients/{username}")
	public String processClientShowForm(@PathVariable("username") final String username, final Map<String, Object> model) {
		Client client = this.clientService.findByUsername(username);
		model.put("client", client);
		return "clients/clientShow";
	}

	@GetMapping(value = "/administrators/usuarios/{username}/disable")
	public String disableUsuario(@PathVariable("username") final String username, final ModelMap model) {

		Usuario usuario = this.usuarioService.findByUsername(username);
		model.put("usuario", usuario);
		return "usuarios/usuariosDisable";
	}

	@PostMapping(value = "/administrators/usuarios/{username}/disable")
	public String disableUsuarioForm(@PathVariable("username") final String username, final ModelMap model, final HttpServletRequest request) {

		Usuario usuario = this.usuarioService.findByUsername(username);
		usuario.getUsuar().setEnabled(false);
		this.usuarioService.saveUsuario(usuario);
		return "redirect:/administrators/usuarios";
	}

	@GetMapping(value = "/administrators/clients/{username}/disable")
	public String disableClient(@PathVariable("username") final String username, final ModelMap model) {

		Client client = this.clientService.findByUsername(username);
		model.put("client", client);
		return "clients/clientDisable";
	}
	@PostMapping(value = "/administrators/clients/{username}/disable")
	public String disableClientForm(@PathVariable("username") final String username, final ModelMap model, final HttpServletRequest request) {

		Client client = this.clientService.findByUsername(username);
		client.getUsuar().setEnabled(false);

		List<FoodOffer> foodOffers = this.foodOfferService.findFoodOfferByUserId(client.getId());
		List<SpeedOffer> speedOffers = this.speedOfferService.findSpeedOfferByUserId(client.getId());
		List<NuOffer> nuOffers = this.nuOfferService.findNuOfferByUserId(client.getId());
		List<TimeOffer> timeOffers = this.timeOfferService.findTimeOfferByUserId(client.getId());

		foodOffers.stream().forEach(f -> f.setStatus(StatusOffer.inactive));

		speedOffers.stream().forEach(s -> s.setStatus(StatusOffer.inactive));

		nuOffers.stream().forEach(n -> n.setStatus(StatusOffer.inactive));

		timeOffers.stream().forEach(t -> t.setStatus(StatusOffer.inactive));

		this.clientService.saveClient(client);
		return "redirect:/administrators/clients";
	}

	@GetMapping(value = "/administrators/clients/{username}/activate")
	public String activateClient(@PathVariable("username") final String username, final ModelMap model) {

		Client client = this.clientService.findByUsername(username);
		model.put("client", client);
		return "clients/clientActivate";
	}
	@PostMapping(value = "/administrators/clients/{username}/activate")
	public String activateClientForm(@PathVariable("username") final String username, final ModelMap model, final HttpServletRequest request) {

		Client client = this.clientService.findByUsername(username);
		client.getUsuar().setEnabled(true);
		this.clientService.saveClient(client);
		return "redirect:/administrators/clients";
	}
}
