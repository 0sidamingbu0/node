package com.mydreamplus.smartdevice.dao.jpa;

import com.mydreamplus.smartdevice.domain.DeviceFunctionTypeEnum;
import com.mydreamplus.smartdevice.domain.DeviceStateEnum;
import com.mydreamplus.smartdevice.entity.Device;
import com.mydreamplus.smartdevice.entity.DeviceType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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
     * Find by symbol device.
     *
     * @param symbol the symbol
     * @return the device
     */
    Device findBySymbol(String symbol);

    /**
     * Find by mac address device.
     *
     * @param macAddress the mac address
     * @return the device
     */
    Device findByMacAddress(String macAddress);


    /**
     * Find all by mac address list.
     *
     * @param macAddress the mac address
     * @return the list
     */
    List<Device> findAllByMacAddress(String macAddress);

    /**
     * Delete by mac address.
     *
     * @param macAddress the mac address
     */
    @Modifying
    @Transactional
    void deleteByMacAddress(String macAddress);


    /**
     * Find all by spec iterable.
     *
     * @param deviceState the device state
     * @param pageable    the page dto
     * @return the iterable
     */
    Page<Device> search(DeviceStateEnum deviceState, Pageable pageable);


    /**
     * Search page.
     *
     * @param device   the device
     * @param pageable the pageable
     * @return the page
     */
    Page<Device> search(Device device, Pageable pageable);


    /**
     * Find all devices by name list.
     *
     * @param name the name
     * @return the list
     */
    List<Device> findAllByNameContaining(String name);


    /**
     * Find all by name containing and function type list.
     *
     * @param aliases                 the name
     * @param deviceFunctionTypeEnum1 the device function type enum 1
     * @param deviceFunctionTypeEnum2 the device function type enum 2
     * @return the list
     */
    @Query("select u from Device u where u.aliases like %?1% and (u.deviceType.deviceFunctionType = ?2 or u.deviceType.deviceFunctionType = ?3)")
    List<Device> findAllMasterByAliasesContainingAndFunctionType(String aliases, DeviceFunctionTypeEnum deviceFunctionTypeEnum1, DeviceFunctionTypeEnum deviceFunctionTypeEnum2);


    /**
     * Find all master by function type list.
     *
     * @param deviceFunctionTypeEnum1 the device function type enum 1
     * @param deviceFunctionTypeEnum2 the device function type enum 2
     * @return the list
     */
    @Query("select u from Device u where (u.deviceType.deviceFunctionType = ?1 or u.deviceType.deviceFunctionType = ?2)")
    List<Device> findAllMasterByFunctionType(DeviceFunctionTypeEnum deviceFunctionTypeEnum1, DeviceFunctionTypeEnum deviceFunctionTypeEnum2);


    /**
     * Find all by aliases containing and pi list.
     * 根据别名、设备职能类型(主控、被控)、PI 查询设备
     *
     * @param aliases                the aliases
     * @param deviceFunctionTypeEnum the device function type enum
     * @param piMacAddress           the piid
     * @return the list
     */
    @Query("select u from Device u where u.aliases like %?1% and (u.deviceType.deviceFunctionType = ?2) and u.pi.macAddress = ?3")
    List<Device> findAllByAliasesContainingAndPi(String aliases, DeviceFunctionTypeEnum deviceFunctionTypeEnum, String piMacAddress);


    /**
     * Find all by pi and device function type list.
     *
     * @param deviceFunctionTypeEnum the device function type enum
     * @param piMacAddress           the pi mac address
     * @return the list
     */
    @Query("select u from Device u where (u.deviceType.deviceFunctionType = ?1) and u.pi.macAddress = ?2")
    List<Device> findAllByPiAndDeviceFunctionType(DeviceFunctionTypeEnum deviceFunctionTypeEnum, String piMacAddress);

    /**
     * Find all by update time less than list.
     *
     * @param updateTime the update time
     * @return the list
     */
    List<Device> findAllByUpdateTimeLessThan(Date updateTime);


    /**
     * Update update time by symbol.
     *
     * @param updateTime  the update time
     * @param deviceState the device state
     * @param symbol      the symbol
     */
    @Modifying
    @Transactional
    @Query("update Device u set u.updateTime = ?1, u.deviceState =?2 where u.symbol = ?3")
    void updateUpdateTimeBySymbol(Date updateTime, DeviceStateEnum deviceState, String symbol);

    /**
     * Update update time by mac address.
     *
     * @param updateTime  the update time
     * @param deviceState the device state
     * @param macAddress  the mac address
     */
    @Modifying
    @Transactional
    @Query("update Device u set u.updateTime = ?1, u.deviceState =?2 where u.macAddress = ?3")
    void updateUpdateTimeByMacAddress(Date updateTime, DeviceStateEnum deviceState, String macAddress);

    /**
     * Update offline state.
     *
     * @param deviceState the device state
     * @param updateTime  the update time
     */
    @Modifying
    @Transactional
    @Query("update Device u set u.deviceState =?1 where u.updateTime < ?2")
    void updateOfflineState(DeviceStateEnum deviceState, Date updateTime);

    List<Device> findByDeviceType(DeviceType deviceType);
}
