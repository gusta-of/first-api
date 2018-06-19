package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
	
}
