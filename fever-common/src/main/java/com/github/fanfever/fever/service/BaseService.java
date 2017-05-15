package com.github.fanfever.fever.service;

import java.awt.*;
import java.util.List;

/**
 * @author fanfever
 * @email fanfeveryahoo@gmail.com
 * @url https://github.com/fanfever
 * @date 2016年7月9日
 */
public interface BaseService<PK, E> {

	E findById(final PK id);

	default boolean isExist(final PK id){
		return null != findById(id);
	};

	E save(final E e);

	E update(final E e);

	void delete(final PK id);

//	default List<E> coverLanguage(List<E> eList){
//		if(eList != null && eList.size() > 0){
//			eList.stream().forEach(e -> {
//				coverLanguage(e);
//			});
//		}
//		return eList;
//	}
//
//	default E coverLanguage(E e){
//		return e;
//	}
}
