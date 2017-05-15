package com.github.fanfever.fever.repository;

import java.io.Serializable;

/**
 * @author fanfever
 * @email fanfeveryahoo@gmail.com
 * @url https://github.com/fanfever
 * @date 2016年7月9日
 */
public interface BaseCommandRepository<PK extends Serializable, M extends Serializable> extends BaseRepository<PK, M> {

    void save(M m);

    void update(M m);

    void deleteById(PK id);

}
