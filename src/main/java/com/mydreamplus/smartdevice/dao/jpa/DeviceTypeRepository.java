package com.mydreamplus.smartdevice.dao.jpa;

import com.mydreamplus.smartdevice.entity.DeviceType;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/11
 * Time: 下午7:59
 * To change this template use File | Settings | File Templates.
 */
public interface DeviceTypeRepository extends PagingAndSortingRepository<DeviceType, Long> {

    /**
     * Find by name device type.
     *
     * @param name the name
     * @return the device type
     */
    DeviceType findByName(String name);
}
