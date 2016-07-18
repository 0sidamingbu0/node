package com.mydreamplus.smartdevice.dao.jpa;

import com.mydreamplus.smartdevice.entity.Device;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/12
 * Time: 下午7:54
 * To change this template use File | Settings | File Templates.
 */
public interface DeviceRepository extends PagingAndSortingRepository<Device, Long>, JpaSpecificationExecutor {

    /**
     * Find by mac address device.
     *
     * @param macAddress the mac address
     * @return the device
     */
    Device findByMacAddress(String macAddress);


    /**
     * Find by symbol device.
     *
     * @param symbol the symbol
     * @return the device
     */
    Device findBySymbol(String symbol);

    /**
     * Find all by mac address and name iterable.
     *
     * @param macAddress the mac address
     * @param name       the name
     * @return the iterable
     */
    Iterable<Device> findAllByMacAddressAndAliases(String macAddress, String name);


    /**
     * Find all by spec iterable.
     *
     * @param device the device
     * @return the iterable
     */
//    Iterable<Device> findAllBySpec(Specification<Device> spec);

    List<Device> search(Device device);


//    Page<T> findAll(Specification<T> spec, Pageable pageable);


}
