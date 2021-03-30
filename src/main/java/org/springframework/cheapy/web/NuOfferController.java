package org.springframework.cheapy.web;

import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.cheapy.model.NuOffer;
import org.springframework.cheapy.model.StatusOffer;
import org.springframework.cheapy.model.Client;
import org.springframework.cheapy.service.ClientService;
import org.springframework.cheapy.service.NuOfferService;
import org.springframework.stereotype.Controller;

import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class NuOfferController {

	private static final String VIEWS_NU_OFFER_CREATE_OR_UPDATE_FORM = "offers/nu/createOrUpdateNuOfferForm";

	private final NuOfferService nuOfferService;
	private final ClientService clientService;

	public NuOfferController(final NuOfferService nuOfferService, final ClientService clientService) {
		this.nuOfferService = nuOfferService;
		this.clientService = clientService;
	}

	@GetMapping("/offers/nu/new")
	public String initCreationForm(Map<String, Object> model) {
		NuOffer nuOffer = new NuOffer();
		model.put("nuOffer", nuOffer);
		return VIEWS_NU_OFFER_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping("/offers/nu/new")
	public String processCreationForm(@Valid NuOffer nuOffer, BindingResult result) {
		if (result.hasErrors()) {
			return VIEWS_NU_OFFER_CREATE_OR_UPDATE_FORM;
		} else {
			nuOffer.setStatus(StatusOffer.hidden);

			Client client = this.clientService.getCurrentClient();

			nuOffer.setClient(client);

			this.nuOfferService.saveNuOffer(nuOffer);
			return "redirect:/offers/nu/"+nuOffer.getId();
		}
	}

	@GetMapping(value ="/offers/nu/{nuOfferId}/activate")
	public String activateNuOffer(@PathVariable("nuOfferId") final int nuOfferId, final ModelMap modelMap) {
		Client client = this.clientService.getCurrentClient();
		NuOffer nuOffer = this.nuOfferService.findNuOfferById(nuOfferId);
		if (nuOffer.getClient().equals(client)) {
			nuOffer.setStatus(StatusOffer.active);
			nuOffer.setCode("NU-" + nuOfferId);
			this.nuOfferService.saveNuOffer(nuOffer);
			
		} else {
			modelMap.addAttribute("message", "You don't have access to this number offer");
		}
		return "redirect:/offers/nu/"+ nuOffer.getId();

	}

	@GetMapping("/offers/nu/{nuOfferId}")
	public String processShowForm(@PathVariable("nuOfferId") int nuOfferId, Map<String, Object> model) {
		NuOffer nuOffer = this.nuOfferService.findNuOfferById(nuOfferId);
		model.put("nuOffer", nuOffer);

		model.put("localDateTimeFormat", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
		return "offers/nu/nuOffersShow";

	}

	@GetMapping(value = "/offers/nu/{nuOfferId}/edit")
	public String updateNuOffer(@PathVariable("nuOfferId") final int nuOfferId, final ModelMap model) {


		NuOffer nuOffer = this.nuOfferService.findNuOfferById(nuOfferId);
		model.addAttribute("nuOffer", nuOffer);
		return NuOfferController.VIEWS_NU_OFFER_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/offers/nu/{nuOfferId}/edit")
	public String updateNuOffer(@Valid final NuOffer nuOfferEdit, final BindingResult result, final ModelMap model) {

		if (result.hasErrors()) {
			model.addAttribute("nuOffer", nuOfferEdit);
			return NuOfferController.VIEWS_NU_OFFER_CREATE_OR_UPDATE_FORM;

		} else {
			this.nuOfferService.saveNuOffer(nuOfferEdit);
			return "redirect:/offers/nu/" + nuOfferEdit.getId();
		}
	}

	@GetMapping(value = "/offers/nu/{nuOfferId}/disable")
	public String disableNuOffer(@PathVariable("nuOfferId") final int nuOfferId, final Principal principal,
			final ModelMap model) {


		NuOffer nuOffer = this.nuOfferService.findNuOfferById(nuOfferId);
		model.put("nuOffer", nuOffer);
		return "nuOffers/nuOffersDisable";
	}

	@PostMapping(value = "/offers/nu/{nuOfferId}/disable")
	public String disableNuOfferForm(@PathVariable("nuOfferId") final int nuOfferId, final Principal principal,
			final ModelMap model) {


		NuOffer nuOffer = this.nuOfferService.findNuOfferById(nuOfferId);
		nuOffer.setStatus(StatusOffer.inactive);
		this.nuOfferService.saveNuOffer(nuOffer);
		return "redirect:/offers";

	}

}
