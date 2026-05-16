package com.sve.workshops.hexagonal.exercice2.article.domain.model;

public record Article(
        String id,
        String name,
        String description,
        Double price
){}

