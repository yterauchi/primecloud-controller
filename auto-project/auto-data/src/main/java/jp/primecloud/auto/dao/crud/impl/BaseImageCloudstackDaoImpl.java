/*
 * Copyright 2014 by SCSK Corporation.
 * 
 * This file is part of PrimeCloud Controller(TM).
 * 
 * PrimeCloud Controller(TM) is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 * 
 * PrimeCloud Controller(TM) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with PrimeCloud Controller(TM). If not, see <http://www.gnu.org/licenses/>.
 */
package jp.primecloud.auto.dao.crud.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import jp.primecloud.auto.dao.crud.BaseImageCloudstackDao;
import jp.primecloud.auto.entity.crud.ImageCloudstack;

/**
 * <p>
 * {@link BaseImageCloudstackDao}の実装クラスです。
 * </p>
 *
 */
public abstract class BaseImageCloudstackDaoImpl extends SqlMapClientDaoSupport implements BaseImageCloudstackDao {

    protected String namespace = "ImageCloudstack";

    /**
     * {@inheritDoc}
     */
    @Override
    public ImageCloudstack read(
            Long imageNo
        ) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("imageNo", imageNo);
        return (ImageCloudstack) getSqlMapClientTemplate().queryForObject(getSqlMapId("read"), paramMap);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<ImageCloudstack> readAll() {
        return (List<ImageCloudstack>) getSqlMapClientTemplate().queryForList(getSqlMapId("readAll"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<ImageCloudstack> readInImageNos(
            Collection<Long> imageNos
        ) {
        if (imageNos.isEmpty()) {
            return new ArrayList<ImageCloudstack>();
        }
        Map<String, Object> paramMap = new HashMap<String, Object>();
        if (imageNos instanceof List) {
            paramMap.put("imageNos", imageNos);
        } else {
            paramMap.put("imageNos", new ArrayList<Long>(imageNos));
        }
        paramMap.put("orderBys", new String[0]);
        return (List<ImageCloudstack>) getSqlMapClientTemplate().queryForList(getSqlMapId("readInImageNos"), paramMap);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void create(ImageCloudstack entity) {
        String id = "create";
        getSqlMapClientTemplate().insert(getSqlMapId(id), entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(ImageCloudstack entity) {
        getSqlMapClientTemplate().insert(getSqlMapId("update"), entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(ImageCloudstack entity) {
        getSqlMapClientTemplate().insert(getSqlMapId("delete"), entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteAll() {
        getSqlMapClientTemplate().delete(getSqlMapId("deleteAll"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteByImageNo(
            Long imageNo
        ) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("imageNo", imageNo);
        getSqlMapClientTemplate().delete(getSqlMapId("deleteByImageNo"), paramMap);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long countAll() {
        return (Long) getSqlMapClientTemplate().queryForObject(getSqlMapId("countAll"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long countByImageNo(
            Long imageNo
        ) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("imageNo", imageNo);
        return (Long) getSqlMapClientTemplate().queryForObject(getSqlMapId("countByImageNo"), paramMap);
    }

    protected String getSqlMapId(String id) {
        if (namespace == null || namespace.length() == 0) {
            return id;
        }
        return namespace + "." + id;
    }

}
