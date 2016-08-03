package com.mydreamplus.smartdevice.dao.jpa;

import com.mydreamplus.smartdevice.domain.DeviceStateEnum;
import com.mydreamplus.smartdevice.entity.Device;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

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
public class DeviceRepositoryImpl {


    private final Logger log = LoggerFactory.getLogger(DeviceRepositoryImpl.class);

    @PersistenceContext
    private EntityManager em;


    /**
     * Search page.
     *
     * @param deviceState the device state
     * @param pageable    the pageable
     * @return the page
     */
    public Page<Device> search(DeviceStateEnum deviceState, Pageable pageable) {

        String dataSql = "select t from Device t where 1 = 1";
        String countSql = "select count(t) from Device t where 1 = 1";
        if (null != deviceState) {
            dataSql += " and t.deviceState = ?1";
            countSql += " and t.deviceState = ?1";
        }
        dataSql += " order by updateTime desc";
        Query dataQuery = em.createQuery(dataSql).setFirstResult(pageable.getPageNumber() * pageable.getPageSize()).setMaxResults(pageable.getPageSize());
        Query countQuery = em.createQuery(countSql);

        if (null != deviceState) {
            dataQuery.setParameter(1, deviceState);
            countQuery.setParameter(1, deviceState);
        }
        return new PageImpl<>(dataQuery.getResultList(), pageable, (long)countQuery.getSingleResult());
    }


    public Page<Device> search(Device device, Pageable pageable) {

        String dataSql = "select t from Device t where 1 = 1";
        String countSql = "select count(t) from Device t where 1 = 1";
        if(device != null && !StringUtils.isEmpty(device.getName())){
            dataSql += " and t.name = ?1";
            countSql += " and t.name = ?1";
        }
        if(device != null && !StringUtils.isEmpty(device.getAliases())){
            dataSql += " and t.aliases = ?2";
            countSql += " and t.aliases = ?2";
        }
        if(device != null && !StringUtils.isEmpty(device.getDeviceState())){
            dataSql += " and t.deviceState = ?3";
            countSql += " and t.deviceState = ?3";
        }
        dataSql += " order by updateTime desc";
        Query dataQuery = em.createQuery(dataSql).setFirstResult(pageable.getPageNumber() * pageable.getPageSize()).setMaxResults(pageable.getPageSize());
        Query countQuery = em.createQuery(countSql);

        if(device != null && !StringUtils.isEmpty(device.getName())){
            dataQuery.setParameter(1, device.getName());
            countQuery.setParameter(1, device.getName());
        }
        if(device != null && !StringUtils.isEmpty(device.getAliases())){
            dataQuery.setParameter(2, device.getAliases());
            countQuery.setParameter(2, device.getAliases());
        }
        if(device != null && !StringUtils.isEmpty(device.getDeviceState())){
            dataQuery.setParameter(3, device.getDeviceState());
            countQuery.setParameter(3, device.getDeviceState());
        }
        log.info("总数:" + countQuery.getSingleResult());
        return new PageImpl<>(dataQuery.getResultList(), pageable, (long)countQuery.getSingleResult());
    }
}
