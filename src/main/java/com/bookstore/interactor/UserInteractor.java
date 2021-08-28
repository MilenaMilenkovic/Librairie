package com.bookstore.interactor;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bookstore.model.User;
import com.bookstore.repository.UserRepository;

@Service
public class UserInteractor {
	@Autowired
	private UserRepository repository;

	@Autowired
    private PasswordEncoder passwordEncoder;

	private User subject;

	public boolean create(User user) {
		subject = new User();
		subject.setFirst_name(user.getFirst_name());
		subject.setLast_name(user.getLast_name());
		subject.setEmail(user.getEmail());
		subject.setPassword_digest(passwordEncoder.encode(user.getPassword()));

		if (!subject.isValid())
			return false;

		repository.save(subject);
		return true;
	}

	public Map<String, List<String>> getErrors() {
		return subject.getErrors();
	}
}
