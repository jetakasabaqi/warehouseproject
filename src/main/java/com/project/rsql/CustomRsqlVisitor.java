package com.project.rsql;

import cz.jirutka.rsql.parser.ast.AndNode;
import cz.jirutka.rsql.parser.ast.ComparisonNode;
import cz.jirutka.rsql.parser.ast.OrNode;
import cz.jirutka.rsql.parser.ast.RSQLVisitor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.From;

public class CustomRsqlVisitor<T> implements RSQLVisitor<Specification<T>, EntityManager> {

    private GenericRsqlSpecBuilder<T> builder;

    public CustomRsqlVisitor() {
        builder = new GenericRsqlSpecBuilder<T>();
    }

    @Override
    public Specification<T> visit(AndNode andNode, EntityManager entityManager) {
        return builder.createSpecification(andNode);
    }

    @Override
    public Specification<T> visit(OrNode orNode, EntityManager entityManager) {
    return builder.createSpecification(orNode);
    }

    @Override
    public Specification<T> visit(ComparisonNode comparisonNode, EntityManager entityManager) {
        return builder.createSpecification(comparisonNode);
    }

    /* (non-Javadoc)
     * @see cz.jirutka.rsql.parser.ast.RSQLVisitor#visit(cz.jirutka.rsql.parser.ast.AndNode, java.lang.Object)
     */

}
