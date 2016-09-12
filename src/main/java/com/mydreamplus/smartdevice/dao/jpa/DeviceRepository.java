package com.mydreamplus.smartdevice.dao.jpa;

import com.mydreamplus.smartdevice.domain.DeviceFunctionTypeEnum;
import com.mydreamplus.smartdevice.domain.DeviceStateEnum;
import com.mydreamplus.smartdevice.entity.Device;
import com.mydreamplus.smartdevice.entity.DeviceGroup;
import com.mydreamplus.smartdevice.entity.DeviceType;
import com.mydreamplus.smartdevice.entity.PI;
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
     * @param name       the name
     * @return the device
     */
    Device findByMacAddressAndName(String macAddress, String name);


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
     * Search page.
     *
     * @param isRegistered    the is registered
     * @param deviceTypeName  the device type name
     * @param deviceStateEnum the device state enum
     * @param pageable        the pageable
     * @param searchKey       the search key
     * @return the page
     */
    Page<Device> search(boolean isRegistered, String deviceTypeName, DeviceStateEnum deviceStateEnum, Pageable pageable, String searchKey);


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
    @Query("select u from Device u where u.aliases like %?1% and (u.deviceType.deviceFunctionType = ?2 or u.deviceType.deviceFunctionType = ?3 or u.deviceType.deviceFunctionType = 'SWITCH_CONTROLLED')")
    List<Device> findAllMasterByAliasesContainingAndFunctionType(String aliases, DeviceFunctionTypeEnum deviceFunctionTypeEnum1, DeviceFunctionTypeEnum deviceFunctionTypeEnum2);


    /**
     * Find all master by function type list.
     *
     * @param deviceFunctionTypeEnum1 the device function type enum 1
     * @param deviceFunctionTypeEnum2 the device function type enum 2
     * @return the list
     */
    @Query("select u from Device u where (u.deviceType.deviceFunctionType = ?1 or u.deviceType.deviceFunctionType = ?2 or u.deviceType.deviceFunctionType = 'SWITCH_CONTROLLED')")
    List<Device> findAllMasterByFunctionType(DeviceFunctionTypeEnum deviceFunctionTypeEnum1, DeviceFunctionTypeEnum deviceFunctionTypeEnum2);

    /**
     * Find all master by function type and pi list.
     *
     * @param deviceFunctionTypeEnum1 the device function type enum 1
     * @param deviceFunctionTypeEnum2 the device function type enum 2
     * @param pi                      the pi
     * @return the list
     */
    @Query("select u from Device u where (u.deviceType.deviceFunctionType = ?1 or u.deviceType.deviceFunctionType = ?2 or u.deviceType.deviceFunctionType = 'SWITCH_CONTROLLED') and u.pi =?3")
    List<Device> findAllMasterByFunctionTypeAndPI(DeviceFunctionTypeEnum deviceFunctionTypeEnum1, DeviceFunctionTypeEnum deviceFunctionTypeEnum2, PI pi);


    /**
     * Find all master by function type and group list.
     *
     * @param deviceFunctionTypeEnum1 the device function type enum 1
     * @param deviceFunctionTypeEnum2 the device function type enum 2
     * @param group                   the group
     * @return the list
     */
    @Query("select u from Device u where (u.deviceType.deviceFunctionType = ?1 or u.deviceType.deviceFunctionType = ?2 or u.deviceType.deviceFunctionType = 'SWITCH_CONTROLLED') and u.pi.deviceGroup =?3")
    List<Device> findAllMasterByFunctionTypeAndGroup(DeviceFunctionTypeEnum deviceFunctionTypeEnum1, DeviceFunctionTypeEnum deviceFunctionTypeEnum2, DeviceGroup group);


    /**
     * Find all by aliases containing and pi list.
     * 根据别名、设备职能类型(主控、被控)、PI 查询设备
     *
     * @param aliases                the aliases
     * @param deviceFunctionTypeEnum the device function type enum
     * @param piMacAddress           the piid
     * @return the list
     */
    @Query("select u from Device u where u.aliases like %?1% and (u.deviceType.deviceFunctionType = ?2 or u.deviceType.deviceFunctionType = 'SWITCH_CONTROLLED') and u.pi.macAddress = ?3")
    List<Device> findAllByAliasesContainingAndPi(String aliases, DeviceFunctionTypeEnum deviceFunctionTypeEnum, String piMacAddress);


    /**
     * Find all by pi and device function type list.
     *
     * @param deviceFunctionTypeEnum the device function type enum
     * @param piMacAddress           the pi mac address
     * @return the list
     */
    @Query("select u from Device u where (u.deviceType.deviceFunctionType = ?1 or u.deviceType.deviceFunctionType = 'SWITCH_CONTROLLED') and u.pi.macAddress = ?2")
    List<Device> findAllByPiAndDeviceFunctionType(DeviceFunctionTypeEnum deviceFunctionTypeEnum, String piMacAddress);


    /**
     * Find all by pi and device function type list.
     *
     * @param deviceFunctionTypeEnum the device function type enum
     * @param groupId                the group id
     * @return the list
     */
    @Query("select u from Device u where (u.deviceType.deviceFunctionType = ?1 or u.deviceType.deviceFunctionType = 'SWITCH_CONTROLLED') and u.pi.deviceGroup.id = ?2")
    List<Device> findAllByPiAndDeviceFunctionType(DeviceFunctionTypeEnum deviceFunctionTypeEnum, long groupId);


    /**
     * Find all by device function type list.
     *
     * @param deviceFunctionTypeEnum the device function type enum
     * @return the list
     */
    @Query("select u from Device u where (u.deviceType.deviceFunctionType = ?1 or u.deviceType.deviceFunctionType = 'SWITCH_CONTROLLED')")
    List<Device> findAllByDeviceFunctionType(DeviceFunctionTypeEnum deviceFunctionTypeEnum);

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
    @Query("update Device u set u.deviceState =?1 where u.updateTime < ?2 and u.pi is not null")
    void updateOfflineState(DeviceStateEnum deviceState, Date updateTime);

    /**
     * Find by device type list.
     *
     * @param deviceType the device type
     * @return the list
     */
    List<Device> findByDeviceType(DeviceType deviceType);


    /**
     * Reset device offline list.
     *
     * @param deviceState the device state enum
     * @return the list
     */
    @Modifying
    @Transactional
    @Query("update Device u set u.deviceState =?1 where u.deviceState = 'OFFLINE'")
    List<Device> resetDeviceOffline(DeviceStateEnum deviceState);

}
