
package org.springframework.cheapy.web;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.cheapy.model.FoodOffer;
import org.springframework.cheapy.model.Municipio;
import org.springframework.cheapy.model.NuOffer;
import org.springframework.cheapy.model.Offer;
import org.springframework.cheapy.model.SpeedOffer;
import org.springframework.cheapy.model.TimeOffer;
import org.springframework.cheapy.service.ClientService;
import org.springframework.cheapy.service.FoodOfferService;
import org.springframework.cheapy.service.NuOfferService;
import org.springframework.cheapy.service.SpeedOfferService;
import org.springframework.cheapy.service.TimeOfferService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OfertaController {

	private final ClientService		clientService;

	private final FoodOfferService	foodOfferService;
	private final NuOfferService	nuOfferService;
	private final SpeedOfferService	speedOfferService;
	private final TimeOfferService	timeOfferService;


	public OfertaController(final FoodOfferService foodOfferService, final NuOfferService nuOfferService, final SpeedOfferService speedOfferService, final TimeOfferService timeOfferService, final ClientService clientService) {
		this.clientService = clientService;
		this.foodOfferService = foodOfferService;
		this.nuOfferService = nuOfferService;
		this.speedOfferService = speedOfferService;
		this.timeOfferService = timeOfferService;
	}

	@GetMapping("/offers")
	public String processFindForm(final Map<String, Object> model) {
		Pageable elements = PageRequest.of(0, 3);

		List<FoodOffer> foodOfferLs = this.foodOfferService.findActiveFoodOffer(elements);
		List<NuOffer> nuOfferLs = this.nuOfferService.findActiveNuOffer(elements);
		List<SpeedOffer> speedOfferLs = this.speedOfferService.findActiveSpeedOffer(elements);
		List<TimeOffer> timeOfferLs = this.timeOfferService.findActiveTimeOffer(elements);

		model.put("foodOfferLs", foodOfferLs);
		model.put("nuOfferLs", nuOfferLs);
		model.put("speedOfferLs", speedOfferLs);
		model.put("timeOfferLs", timeOfferLs);

		// Añade la lista de municipios al desplegable
		model.put("municipios", Municipio.values());

		//Se añade formateador de fecha al modelo
		model.put("localDateTimeFormat", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

		return "offers/offersList";

	}

	@GetMapping("/offersByName")
	public String processFindFormByName(final Map<String, Object> model, final String name) {

		List<FoodOffer> foodOfferLs = this.foodOfferService.findFoodOfferByClientName(name);
		List<NuOffer> nuOfferLs = this.nuOfferService.findNuOfferByClientName(name);
		List<SpeedOffer> speedOfferLs = this.speedOfferService.findSpeedOfferByClientName(name);
		List<TimeOffer> timeOfferLs = this.timeOfferService.findTimeOfferByClientName(name);
		model.put("foodOfferLs", foodOfferLs);
		model.put("nuOfferLs", nuOfferLs);
		model.put("speedOfferLs", speedOfferLs);
		model.put("timeOfferLs", timeOfferLs);

		// Añade la lista de municipios al desplegable
		model.put("municipios", Municipio.values());

		//Se añade formateador de fecha al modelo
		model.put("localDateTimeFormat", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

		return "offers/offersListSearch";

	}

	@GetMapping("/offersByFood")
	public String processFindFormByFood(final Map<String, Object> model, final String name) {

		List<FoodOffer> foodOfferLs = this.foodOfferService.findFoodOfferByClientFood(name);
		List<NuOffer> nuOfferLs = this.nuOfferService.findNuOfferByClientFood(name);
		List<SpeedOffer> speedOfferLs = this.speedOfferService.findSpeedOfferByClientFood(name);
		List<TimeOffer> timeOfferLs = this.timeOfferService.findTimeOfferByClientFood(name);
		model.put("foodOfferLs", foodOfferLs);
		model.put("nuOfferLs", nuOfferLs);
		model.put("speedOfferLs", speedOfferLs);
		model.put("timeOfferLs", timeOfferLs);

		// Añade la lista de municipios al desplegable
		model.put("municipios", Municipio.values());

		//Se añade formateador de fecha al modelo
		model.put("localDateTimeFormat", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

		return "offers/offersListSearch";

	}

	@GetMapping("/offersByPlace")
	public String processFindFormByPlace(final Map<String, Object> model, final HttpServletRequest request) {

		if (request.getParameter("municipio").equals("") || request.getParameter("municipio").equals(null)) {
			// Añade la lista de municipios al desplegable
			model.put("municipios", Municipio.values());

			//Se añade formateador de fecha al modelo
			model.put("localDateTimeFormat", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

			return "redirect:/offers/";
		}

		Municipio mun = Municipio.valueOf(request.getParameter("municipio"));

		List<FoodOffer> foodOfferLs = this.foodOfferService.findFoodOfferByClientPlace(mun);
		List<NuOffer> nuOfferLs = this.nuOfferService.findNuOfferByClientPlace(mun);
		List<SpeedOffer> speedOfferLs = this.speedOfferService.findSpeedOfferByClientPlace(mun);
		List<TimeOffer> timeOfferLs = this.timeOfferService.findTimeOfferByClientPlace(mun);
		model.put("foodOfferLs", foodOfferLs);
		model.put("nuOfferLs", nuOfferLs);
		model.put("speedOfferLs", speedOfferLs);
		model.put("timeOfferLs", timeOfferLs);

		// Añade la lista de municipios al desplegable
		model.put("municipios", Municipio.values());

		//Se añade formateador de fecha al modelo
		model.put("localDateTimeFormat", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

		return "offers/offersListSearch";

	}

	@GetMapping("/myOffers")
	public String processMyOffersForm(final Map<String, Object> model) {

		int actual = this.clientService.getCurrentClient().getId();

		List<FoodOffer> foodOfferLs = this.foodOfferService.findFoodOfferActOclByUserId(actual);
		List<NuOffer> nuOfferLs = this.nuOfferService.findNuOfferActOclByUserId(actual);
		List<SpeedOffer> speedOfferLs = this.speedOfferService.findSpeedOfferActOclByUserId(actual);
		List<TimeOffer> timeOfferLs = this.timeOfferService.findTimeOfferActOclByUserId(actual);

		model.put("foodOfferLs", foodOfferLs);
		model.put("nuOfferLs", nuOfferLs);
		model.put("speedOfferLs", speedOfferLs);
		model.put("timeOfferLs", timeOfferLs);

		//Se añade formateador de fecha al modelo
		model.put("localDateTimeFormat", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

		return "offers/myOffersList";
	}

	@GetMapping("/offersCreate")
	public String createOffers() {

		return "offers/offersCreate";
	}

	@GetMapping("/offersRecord")
	public String processOffersRecordForm(final Map<String, Object> model) {

		Pageable p = PageRequest.of(0, 3);

		Map<Offer, String> datos = new HashMap<Offer, String>();

		for (Offer of : this.foodOfferService.findAllFoodOffer(p)) {
			datos.put(of, "food");
		}

		for (Offer of : this.nuOfferService.findAllNuOffer(p)) {
			datos.put(of, "nu");
		}

		for (Offer of : this.speedOfferService.findAllSpeedOffer(p)) {
			datos.put(of, "speed");
		}

		for (Offer of : this.timeOfferService.findAllTimeOffer(p)) {
			datos.put(of, "time");
		}

		model.put("datos", datos);

		//Se añade formateador de fecha al modelo
		model.put("localDateTimeFormat", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

		return "offers/offersRecordList";
	}

	//	@GetMapping("/owners/{ownerId}/edit")
	//	public String initUpdateOwnerForm(@PathVariable("ownerId") int ownerId, Model model) {
	//		Owner owner = this.ownerService.findOwnerById(ownerId);
	//		model.addAttribute(owner);
	//		return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
	//	}
	//
	//	@PostMapping("/owners/{ownerId}/edit")
	//	public String processUpdateOwnerForm(@Valid Owner owner, BindingResult result,
	//			@PathVariable("ownerId") int ownerId) {
	//		if (result.hasErrors()) {
	//			return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
	//		}
	//		else {
	//			owner.setId(ownerId);
	//			this.ownerService.saveOwner(owner);
	//			return "redirect:/owners/{ownerId}";
	//		}
	//	}
	//	@GetMapping("/owners/{ownerId}")
	//	public ModelAndView showOwner(@PathVariable("ownerId") int ownerId) {
	//		ModelAndView mav = new ModelAndView("owners/ownerDetails");
	//		Owner owner = this.ownerService.findOwnerById(ownerId);
	//
	//		mav.addObject(owner);
	//		return mav;
	//	}

}
