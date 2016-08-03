package com.mydreamplus.smartdevice.dao.jpa;

import com.mydreamplus.smartdevice.entity.DeviceGroup;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/11
 * Time: 下午5:19
 * To change this template use File | Settings | File Templates.
 */
public interface GroupRepository extends PagingAndSortingRepository<DeviceGroup, Long> {


    /**
     * Find by mac address device group.
     * @return the device group
     */
    DeviceGroup findByName(String name);
}
