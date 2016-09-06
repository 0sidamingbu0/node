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
            if (deviceState == DeviceStateEnum.REGISTERED) {
                dataSql += " and t.deviceState != 'UNREGISTERED'";
                countSql += " and t.deviceState != 'UNREGISTERED'";
            } else {
                dataSql += " and t.deviceState = ?3";
                countSql += " and t.deviceState = ?3";
            }
        }
        dataSql += " order by updateTime desc";
        Query dataQuery = em.createQuery(dataSql).setFirstResult(pageable.getPageNumber() * pageable.getPageSize()).setMaxResults(pageable.getPageSize());
        Query countQuery = em.createQuery(countSql);

        if (null != deviceState) {
            if (deviceState != DeviceStateEnum.REGISTERED) {
                countQuery.setParameter(3, deviceState);
                dataQuery.setParameter(3, deviceState);
            }
        }
        return new PageImpl(dataQuery.getResultList(), pageable, (long) countQuery.getSingleResult());
    }


    public Page<Device> search(Device device, Pageable pageable) {

        String dataSql = "select t from Device t where 1 = 1";
        String countSql = "select count(t) from Device t where 1 = 1";
        if (device != null && !StringUtils.isEmpty(device.getName())) {
            dataSql += " and t.name = ?1";
            countSql += " and t.name = ?1";
        }
        if (device != null && !StringUtils.isEmpty(device.getAliases())) {
            dataSql += " and t.aliases = ?2";
            countSql += " and t.aliases = ?2";
        }
        if (device != null && !StringUtils.isEmpty(device.getDeviceState())) {
            if (device.getDeviceState() == DeviceStateEnum.REGISTERED) {
                dataSql += " and t.deviceState != 'UNREGISTERED'";
                countSql += " and t.deviceState != 'UNREGISTERED'";
            } else {
                dataSql += " and t.deviceState = ?3";
                countSql += " and t.deviceState = ?3";
            }
        }

        dataSql += " order by updateTime desc";
        Query dataQuery = em.createQuery(dataSql).setFirstResult(pageable.getPageNumber() * pageable.getPageSize()).setMaxResults(pageable.getPageSize());
        Query countQuery = em.createQuery(countSql);

        if (device != null && !StringUtils.isEmpty(device.getName())) {
            dataQuery.setParameter(1, device.getName());
            countQuery.setParameter(1, device.getName());
        }
        if (device != null && !StringUtils.isEmpty(device.getAliases())) {
            dataQuery.setParameter(2, device.getAliases());
            countQuery.setParameter(2, device.getAliases());
        }
        if (device != null && !StringUtils.isEmpty(device.getDeviceState())) {
            if (device.getDeviceState() == DeviceStateEnum.REGISTERED) {

            } else {
                countQuery.setParameter(3, device.getDeviceState());
                dataQuery.setParameter(3, device.getDeviceState());
            }
        }
        return new PageImpl(dataQuery.getResultList(), pageable, (long) countQuery.getSingleResult());
    }


    /**
     * 查询在线设备
     *
     * @param isRegistered
     * @param deviceTypeName
     * @param deviceStateEnum
     * @param pageable
     * @return
     */
    public Page<Device> search(boolean isRegistered, String deviceTypeName, DeviceStateEnum deviceStateEnum, Pageable pageable, String searchKey) {

        String dataSql = "select t from Device t where 1 = 1";
        String countSql = "select count(t) from Device t where 1 = 1";
        if (deviceTypeName != null && !StringUtils.isEmpty(deviceTypeName)) {
            dataSql += " and t.name = ?1";
            countSql += " and t.name = ?1";
        }
        if (deviceStateEnum != null) {
            dataSql += " and t.deviceState = ?2";
            countSql += " and t.deviceState = ?2";
        }
        if (isRegistered) {
            dataSql += " and t.isRegistered = true";
            countSql += " and t.isRegistered = true";
        } else {
            dataSql += " and t.isRegistered = false";
            countSql += " and t.isRegistered = false";
        }
        // 模糊查询
        if (!StringUtils.isEmpty(searchKey)) {
            dataSql += " and t.symbol like  ?3 or t.name like ?3 or t.aliases like ?3 or t.macAddress like ?3 or t.factory like ?3";
            countSql += " and t.symbol like  ?3 or t.name like ?3 or t.aliases like ?3 or t.macAddress like ?3 or t.factory like ?3";
        }

        dataSql += " order by updateTime desc";
        Query dataQuery = em.createQuery(dataSql).setFirstResult(pageable.getPageNumber() * pageable.getPageSize()).setMaxResults(pageable.getPageSize());
        Query countQuery = em.createQuery(countSql);

        if (deviceTypeName != null && !StringUtils.isEmpty(deviceTypeName)) {
            dataQuery.setParameter(1, deviceTypeName);
            countQuery.setParameter(1, deviceTypeName);
        }
        if (deviceStateEnum != null) {
            dataQuery.setParameter(2, deviceStateEnum);
            countQuery.setParameter(2, deviceStateEnum);
        }
        if (!StringUtils.isEmpty(searchKey)) {
            searchKey = "%" + searchKey + "%";
            dataQuery.setParameter(3, searchKey);
            countQuery.setParameter(3, searchKey);
        }

        return new PageImpl(dataQuery.getResultList(), pageable, (long) countQuery.getSingleResult());
    }
}
