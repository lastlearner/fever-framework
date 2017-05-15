package com.github.fanfever.fever.repository;

import java.io.Serializable;
import java.util.List;

/**
 * @author fanfever
 * @email fanfeveryahoo@gmail.com
 * @url https://github.com/fanfever
 * @date 2016年7月9日
 */
public interface BaseQueryRepository<PK extends Serializable, M extends Serializable> extends BaseRepository<PK, M> {

	M findById(final PK id);

	M findByIdWithLanguage(final M m);

	List<M> findAll();

	List<M> findAllWithLanguage(final M m);

	List<M> findByCondition(final M m);

	List<M> findByConditionWithLanguage(final M m);


}
