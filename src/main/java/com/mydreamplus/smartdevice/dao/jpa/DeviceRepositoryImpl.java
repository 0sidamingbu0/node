package com.mydreamplus.smartdevice.dao.jpa;

import com.mydreamplus.smartdevice.entity.Device;
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
public class DeviceRepositoryImpl {

    @PersistenceContext
    private EntityManager em;


    public List<Device> search(Device device){
        String dataSql = "select t from Device t where 1 = 1";
        String countSql = "select count(t) from Device t where 1 = 1";
        if(null != device && !StringUtils.isEmpty(device.getAliases())) {
            dataSql += " and t.aliases = ?1";
            countSql += " and t.aliases = ?1";
        }
        if(null != device && !StringUtils.isEmpty(device.getDeviceState())) {
            dataSql += " and t.deviceState = ?2";
            countSql += " and t.deviceState = ?2";
        }
        Query dataQuery = em.createQuery(dataSql);
        Query countQuery = em.createQuery(countSql);
        if(null != device && !StringUtils.isEmpty(device.getAliases())) {
            dataQuery.setParameter(1, device.getAliases());
            countQuery.setParameter(1, device.getAliases());
        }
        if(null != device && !StringUtils.isEmpty(device.getDeviceState())) {
            dataQuery.setParameter(2, device.getDeviceState());
            countQuery.setParameter(2, device.getDeviceState());
        }
        long totalSize = (long) countQuery.getSingleResult();
        List<Device> data = dataQuery.getResultList();
        return data;
    }
}
