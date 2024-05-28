package com.shop.item.repository;

import static com.shop.item.entity.QItem.*;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shop.item.entity.Item;

public class ItemRepositoryCustomImpl implements ItemRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	public ItemRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	@Override
	public Page<Item> findAll(String keyword, Long categoryId, Pageable pageable) {
		List<Item> fetch = queryFactory.select(item)
			.from(item)
			.where(keywordContain(keyword), categoryEq(categoryId))
			.join(item.category)
			.fetchJoin()
			.join(item.user)
			.fetchJoin()
			.orderBy(item.createDate.desc())
			.offset((long)pageable.getPageSize() * pageable.getPageNumber())
			.limit(pageable.getPageSize())
			.fetch();

		return PageableExecutionUtils.getPage(fetch, pageable, () -> queryFactory
			.selectFrom(item)
			.where(keywordContain(keyword), categoryEq(categoryId))
			.fetch().size());
	}

	private BooleanExpression categoryEq(Long categoryId) {
		return categoryId == null ? null : item.category.id.eq(categoryId);
	}

	private BooleanExpression keywordContain(String keyword) {
		return keyword == null ? null : item.name.containsIgnoreCase(keyword);
	}
}
