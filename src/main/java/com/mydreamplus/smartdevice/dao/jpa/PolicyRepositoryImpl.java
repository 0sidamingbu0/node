package com.mydreamplus.smartdevice.dao.jpa;

import com.mydreamplus.smartdevice.entity.Policy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

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
        dataSql += " order by createTime desc";
        Query dataQuery = em.createQuery(dataSql).setFirstResult(pageable.getPageNumber() * pageable.getPageSize()).setMaxResults(pageable.getPageSize());
        Query countQuery = em.createQuery(countSql);

        /*if (null != deviceState) {
            dataQuery.setParameter(1, deviceState);
            countQuery.setParameter(1, deviceState);
        }*/
        return new PageImpl(dataQuery.getResultList(), pageable, (long) countQuery.getSingleResult());
    }


    public Page<Policy> search(Policy policy, Pageable pageable, String searchKey) {

        String dataSql = "select t from Policy t where deleted = false";
        String countSql = "select count(t) from Policy t where deleted = false";
        if (!StringUtils.isEmpty(searchKey)) {
            dataSql += " and t.policyConfig like ?1 or t.name like ?1 or t.description like ?1";
            countSql += " and t.policyConfig like ?1 or t.name like ?1 or t.description like ?1";
        }
        dataSql += " order by createTime desc";
        Query dataQuery = em.createQuery(dataSql).setFirstResult(pageable.getPageNumber() * pageable.getPageSize()).setMaxResults(pageable.getPageSize());
        Query countQuery = em.createQuery(countSql);

        if (!StringUtils.isEmpty(searchKey)) {
            searchKey = "%" + searchKey + "%";
            dataQuery.setParameter(1, searchKey);
            countQuery.setParameter(1, searchKey);
        }
        return new PageImpl(dataQuery.getResultList(), pageable, (long) countQuery.getSingleResult());
    }


    public List<Policy> findAllPolicyByLikeName(String name, boolean isDisabled) {
        String dataSql = "select t from Policy t where isDisabled = " + isDisabled;
        if (!StringUtils.isEmpty(name)) {
            dataSql += " and t.name like ?1";
        }
        dataSql += " order by createTime desc";
        Query dataQuery = em.createQuery(dataSql);
        if (!StringUtils.isEmpty(name)) {
            name = "%" + name + "%";
            dataQuery.setParameter(1, name);
        }
        return dataQuery.getResultList();
    }


    public Policy findAllPolicyByLikeMasterSymbol(String masterSymbol, boolean isDisabled) {
        String dataSql = "select t from Policy t where 1=1";
        if (!StringUtils.isEmpty(masterSymbol)) {
            dataSql += " and t.masterEvent like ?1";
        }
        Query dataQuery = em.createQuery(dataSql);
        if (!StringUtils.isEmpty(masterSymbol)) {
            masterSymbol = "%" + masterSymbol + "%";
            dataQuery.setParameter(1, masterSymbol);
        }
        List<Policy> policies = dataQuery.getResultList();
        if (policies != null && policies.size() > 0) {
            return policies.iterator().next();
        } else {
            return null;
        }
    }
}
