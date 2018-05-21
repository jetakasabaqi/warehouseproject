package com.project.rsql;

import cz.jirutka.rsql.parser.ast.ComparisonOperator;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class GenericRsqlSpecification<T> implements Specification<T> {
    private String property;
    private ComparisonOperator operator;
    private List<String> arguments;

    public GenericRsqlSpecification(
        String property, ComparisonOperator operator, List<String> arguments) {
        super();
        this.property = property;
        this.operator = operator;
        this.arguments = arguments;
    }

    @Override
    public javax.persistence.criteria.Predicate toPredicate(
        Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        List<Object> args = castArguments(root);
        Object argument = args.get(0);
        switch (RsqlSearchOperation.getSimpleOperator(operator)) {

            case EQUAL: {
                if (argument instanceof String) {
                    return (javax.persistence.criteria.Predicate) builder.like(
                        root.<String> get(property), argument.toString().replace('*', '%'));
                } else if (argument == null) {
                    return (javax.persistence.criteria.Predicate) builder.isNull(root.get(property));
                } else {
                    return (javax.persistence.criteria.Predicate) builder.equal(root.get(property), argument);
                }
            }
            case NOT_EQUAL: {
                if (argument instanceof String) {
                    return (javax.persistence.criteria.Predicate) builder.notLike(
                        root.<String> get(property), argument.toString().replace('*', '%'));
                } else if (argument == null) {
                    return (javax.persistence.criteria.Predicate) builder.isNotNull(root.get(property));
                } else {
                    return (javax.persistence.criteria.Predicate) builder.notEqual(root.get(property), argument);
                }
            }
            case GREATER_THAN: {
                return (javax.persistence.criteria.Predicate) builder.greaterThan(root.<String> get(property), argument.toString());
            }
            case GREATER_THAN_OR_EQUAL: {
                return (javax.persistence.criteria.Predicate) builder.greaterThanOrEqualTo(
                    root.<String> get(property), argument.toString());
            }
            case LESS_THAN: {
                return (javax.persistence.criteria.Predicate) builder.lessThan(root.<String> get(property), argument.toString());
            }
            case LESS_THAN_OR_EQUAL: {
                return (javax.persistence.criteria.Predicate) builder.lessThanOrEqualTo(
                    root.<String> get(property), argument.toString());
            }
            case IN:
                return (javax.persistence.criteria.Predicate) root.get(property).in(args);
            case NOT_IN:
                return (javax.persistence.criteria.Predicate) builder.not(root.get(property).in(args));
        }

        return null;
    }

    private List<Object> castArguments(Root<T> root) {
        List<Object> args = new ArrayList<Object>();
        Class<? extends Object> type = root.get(property).getJavaType();

        for (String argument : arguments) {
            if (type.equals(Integer.class)) {
                args.add(Integer.parseInt(argument));
            } else if (type.equals(Long.class)) {
                args.add(Long.parseLong(argument));
            } else {
                args.add(argument);
            }
        }

        return args;
    }
}
