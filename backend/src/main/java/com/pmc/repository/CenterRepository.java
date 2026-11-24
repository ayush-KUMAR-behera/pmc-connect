package com.pmc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pmc.entity.Center;

public interface CenterRepository extends JpaRepository<Center,Long> {

	List<Center> findByActiveTrue();
	boolean existsByNameIgnoreCase(String name);
}
