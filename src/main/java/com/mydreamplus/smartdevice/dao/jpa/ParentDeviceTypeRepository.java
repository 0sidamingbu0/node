package com.mydreamplus.smartdevice.dao.jpa;

import com.mydreamplus.smartdevice.entity.DeviceType;
import com.mydreamplus.smartdevice.entity.ParentDeviceType;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/11
 * Time: 下午7:59
 * To change this template use File | Settings | File Templates.
 */
public interface ParentDeviceTypeRepository extends PagingAndSortingRepository<ParentDeviceType, Long> {

    /**
     * Find by name device type.
     *
     * @param name the name
     * @return the device type
     */
    DeviceType findByName(String name);
}