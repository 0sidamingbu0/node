package com.mydreamplus.smartdevice.dao.jpa;

import com.mydreamplus.smartdevice.entity.Policy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/13
 * Time: 下午9:14
 * To change this template use File | Settings | File Templates.
 */
public class PolicyRepositoryImpl {


    private final Logger log = LoggerFactory.getLogger(PolicyRepositoryImpl.class);

    @PersistenceContext
    private EntityManager em;

    public Page<Policy> search(Policy policy, Pageable pageable) {

        String dataSql = "select t from Policy t where deleted = false";
        String countSql = "select count(t) from Policy t where deleted = false";
        /*if (null != deviceState) {
            dataSql += " and t.deviceState = ?1";
            countSql += " and t.deviceState = ?1";
        }*/
        dataSql += " order by updateTime desc";
        Query dataQuery = em.createQuery(dataSql).setFirstResult(pageable.getPageNumber() * pageable.getPageSize()).setMaxResults(pageable.getPageSize());
        Query countQuery = em.createQuery(countSql);

        /*if (null != deviceState) {
            dataQuery.setParameter(1, deviceState);
            countQuery.setParameter(1, deviceState);
        }*/
        return new PageImpl(dataQuery.getResultList(), pageable, (long) countQuery.getSingleResult());
    }

}
