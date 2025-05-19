package com.thiru.daliyworklog.daliyworklog.controller;

import com.thiru.daliyworklog.daliyworklog.ApiResponse;
import com.thiru.daliyworklog.daliyworklog.model.LearningEntry;
import com.thiru.daliyworklog.daliyworklog.service.LearningService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/learn")
@CrossOrigin(origins = "*")
public class LearningController {

	@Autowired
	private LearningService service;

	@GetMapping
	public ResponseEntity<ApiResponse<List<LearningEntry>>> list() {
		List<LearningEntry> entries = service.getAll();
		return ResponseEntity.ok(new ApiResponse<>(true, "Fetched entries", entries));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<LearningEntry>> get(@PathVariable UUID id) {
		LearningEntry entry = service.getById(id);
		return ResponseEntity.ok(new ApiResponse<>(true, "Fetched entry", entry));
	}

	@PostMapping
	public ResponseEntity<ApiResponse<LearningEntry>> create(@RequestBody LearningEntry e) {
		LearningEntry saved = service.save(e);
		return ResponseEntity.ok(new ApiResponse<>(true, "Entry created", saved));
	}

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<LearningEntry>> update(@PathVariable UUID id, @RequestBody LearningEntry e) {
		e.setId(id);
		LearningEntry updated = service.save(e);
		return ResponseEntity.ok(new ApiResponse<>(true, "Entry updated", updated));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<String>> delete(@PathVariable UUID id) {
		service.delete(id);
		return ResponseEntity.ok(new ApiResponse<>(true, "Entry deleted", null));
	}

	@PostMapping("/upload")
	public ResponseEntity<ApiResponse<String>> upload(@RequestParam("file") MultipartFile file) throws IOException {
		String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
		Path uploadPath = Paths.get("uploads");

		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}

		Files.copy(file.getInputStream(), uploadPath.resolve(filename), StandardCopyOption.REPLACE_EXISTING);

		return ResponseEntity.ok(new ApiResponse<>(true, "File uploaded", "/uploads/" + filename));
	}
}
