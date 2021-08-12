package io.github.fernandoferreira.compasso.productms.repositories.impl;

import io.github.fernandoferreira.compasso.productms.models.Product;
import io.github.fernandoferreira.compasso.productms.repositories.ProductCriteriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class ProductCriteriaRepositoryImpl implements ProductCriteriaRepository {

    private final EntityManager entityManager;

    @Override
    public List<Product> search(String nameOrDescription, Double minPrice, Double maxPrice) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();

        CriteriaQuery<Product> cq = cb.createQuery(Product.class);

        Root<Product> productRoot = cq.from(Product.class);

        Predicate pricePredicate = this.checkPrice(minPrice, maxPrice, cb, productRoot);
        Predicate nameOrDescriptionPredicate = null;

        if (Objects.nonNull(nameOrDescription) && !nameOrDescription.isBlank()) {
            Predicate namePredicate = cb.like(cb.upper(productRoot.get("name")), "%" + nameOrDescription.toUpperCase() + "%");
            Predicate descriptionPredicate = cb.like(cb.upper(productRoot.get("description")), "%" + nameOrDescription.toUpperCase() + "%");
            nameOrDescriptionPredicate = cb.or(namePredicate, descriptionPredicate);
        }

        if (Objects.nonNull(pricePredicate) && Objects.nonNull(nameOrDescriptionPredicate)) {
            Predicate finalPredicate = cb.and(pricePredicate, nameOrDescriptionPredicate);
            cq.where(finalPredicate);
        }

        if (Objects.isNull(pricePredicate) && Objects.nonNull(nameOrDescriptionPredicate)) {
            cq.where(nameOrDescriptionPredicate);
        }

        if (Objects.nonNull(pricePredicate) && Objects.isNull(nameOrDescriptionPredicate)) {
            cq.where(pricePredicate);
        }

        if (Objects.isNull(pricePredicate) && Objects.isNull(nameOrDescriptionPredicate)) {
            return new ArrayList<>();
        }

        TypedQuery<Product> query = this.entityManager.createQuery(cq);

        return query.getResultList();
    }

    private Predicate checkPrice(Double minPrice, Double maxPrice, CriteriaBuilder cb, Root<Product> root) {
        if (Objects.nonNull(minPrice) && Objects.nonNull(maxPrice)) {
            return cb.between(root.get("price"), minPrice, maxPrice);
        }

        if (Objects.nonNull(minPrice)) {
            return cb.greaterThanOrEqualTo(root.get("price"), minPrice);
        }

        if (Objects.nonNull(maxPrice)) {
            return cb.lessThanOrEqualTo(root.get("price"), maxPrice);
        }

        return null;
    }
}
