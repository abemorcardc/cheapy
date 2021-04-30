
package org.springframework.cheapy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cheapy.model.Client;
import org.springframework.cheapy.model.ReviewClient;
import org.springframework.cheapy.repository.ClientRepository;
import org.springframework.cheapy.repository.ReviewClientRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientService {

	private ClientRepository		clientRepository;
	private ReviewClientRepository	reviewRepositoy;


	@Autowired
	public ClientService(final ClientRepository clientRepository, final ReviewClientRepository reviewRepositoy) {
		this.clientRepository = clientRepository;
		this.reviewRepositoy = reviewRepositoy;
	}

	@Transactional
	public Client getCurrentClient() throws DataAccessException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		return this.clientRepository.findByUsername(username);
	}

	public void saveClient(final Client client) throws DataAccessException {
		this.clientRepository.save(client);
	}

	@Transactional
	public Client findByUsername(final String username) throws DataAccessException {
		return this.clientRepository.findByUsername(username);
	}

	@Transactional
	public Client findById(final Integer id) throws DataAccessException {
		return this.clientRepository.findById(id).get();
	}

	@Transactional
	public List<Client> findAllClient(final Pageable page) throws DataAccessException {
		return this.clientRepository.findAllClient(page);
	}

	public Integer mediaValoraciones(final Client client) {
		List<ReviewClient> valoraciones = this.reviewRepositoy.findAllReviewClientByBar(client);
		if (valoraciones.size() != 0) {
			return Integer.valueOf((int) valoraciones.stream().mapToInt(x -> x.getMedia()).average().getAsDouble());
		} else {
			return 0;
		}
	}
}
