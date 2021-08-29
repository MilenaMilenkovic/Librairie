package com.bookstore.interactor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bookstore.model.User;
import com.bookstore.repository.UserRepository;

@Service
public class UserInteractor extends Interactor<User, UserRepository> {

	@Autowired
    private PasswordEncoder passwordEncoder;

	public boolean create(User user) {
		subject = new User();
		subject.setFirst_name(user.getFirst_name());
		subject.setLast_name(user.getLast_name());
		subject.setEmail(user.getEmail());
		subject.setPassword(passwordEncoder.encode(user.getPassword()));

		return this.save();
	}
}
