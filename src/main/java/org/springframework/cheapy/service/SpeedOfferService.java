package org.springframework.cheapy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cheapy.model.SpeedOffer;
import org.springframework.cheapy.repository.SpeedOfferRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class SpeedOfferService {
	
	private SpeedOfferRepository speedOfferRepository;

	@Autowired
	public SpeedOfferService(final SpeedOfferRepository speedOfferRepository) {
		this.speedOfferRepository = speedOfferRepository;
	}

	public SpeedOffer findSpeedOfferById(final int id) {
		return this.speedOfferRepository.findById(id);
	}

	public void saveSpeedOffer(final SpeedOffer speedOffer) throws DataAccessException {
		this.speedOfferRepository.save(speedOffer);

	}
}