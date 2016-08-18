package com.mydreamplus.smartdevice.dao.jpa;

import com.mydreamplus.smartdevice.entity.DeviceEvent;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/11
 * Time: 下午4:41
 * To change this template use File | Settings | File Templates.
 * 设 事件 储
 */
public interface DeviceEventRepository extends PagingAndSortingRepository<DeviceEvent, Long> {


}
