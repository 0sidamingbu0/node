package com.mydreamplus.smartdevice.dao.jpa;

import com.mydreamplus.smartdevice.entity.PI;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/13
 * Time: 上午9:48
 * To change this template use File | Settings | File Templates.
 */
public interface PIRespository extends PagingAndSortingRepository<PI, Long> {

    PI findByMacAddress(String macAddress);
}
