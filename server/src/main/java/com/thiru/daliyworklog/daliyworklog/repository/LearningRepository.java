package com.thiru.daliyworklog.daliyworklog.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thiru.daliyworklog.daliyworklog.model.LearningEntry;

public interface LearningRepository extends JpaRepository<LearningEntry, UUID> {}
