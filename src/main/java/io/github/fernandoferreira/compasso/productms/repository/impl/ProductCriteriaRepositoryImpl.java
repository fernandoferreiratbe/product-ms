package io.github.fernandoferreira.compasso.productms.repository.impl;

import io.github.fernandoferreira.compasso.productms.model.Product;
import io.github.fernandoferreira.compasso.productms.repository.ProductCriteriaRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;


import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Locale;
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

        if (Objects.nonNull(nameOrDescription) && !nameOrDescription.isBlank()) {
            Predicate namePredicate = cb.like(cb.upper(productRoot.get("name")), "%" + nameOrDescription.toUpperCase() + "%");
            Predicate descriptionPredicate = cb.like(cb.upper(productRoot.get("description")), "%" + nameOrDescription.toUpperCase() + "%");
            Predicate predicate = cb.or(namePredicate, descriptionPredicate);
            cq.where(predicate);
        }

        if (Objects.nonNull(pricePredicate)) {
            cq.where(pricePredicate);
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
