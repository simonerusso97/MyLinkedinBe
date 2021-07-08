package it.unisalento.mylinkedin.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import it.unisalento.mylinkedin.domain.entity.JsonDocument;

public interface JsonDocumentRepository extends JpaRepository<JsonDocument, String>{

}
