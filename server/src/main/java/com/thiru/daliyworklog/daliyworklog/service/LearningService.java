package com.thiru.daliyworklog.daliyworklog.service;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thiru.daliyworklog.daliyworklog.model.LearningEntry;
import com.thiru.daliyworklog.daliyworklog.repository.LearningRepository;

@Service
public class LearningService {

	@Autowired
	private LearningRepository repo;

	public List<LearningEntry> getAll() {
		return repo.findAll();
	}

	public LearningEntry getById(UUID id) {
		return repo.findById(id).orElse(null);
	}

	public LearningEntry save(LearningEntry entry) {
		return repo.save(entry);
	}

	public void delete(UUID id) {
		repo.deleteById(id);
	}
}
