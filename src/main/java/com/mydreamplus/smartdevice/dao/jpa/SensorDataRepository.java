package com.mydreamplus.smartdevice.dao.jpa;

import com.mydreamplus.smartdevice.entity.SensorData;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/11
 * Time: 下午7:59
 * To change this template use File | Settings | File Templates.
 */
public interface SensorDataRepository extends PagingAndSortingRepository<SensorData, Long> {


    /**
     * Find all by symbol list.
     *
     * @param symbol the symbol
     * @return the list
     */
    List<SensorData> findFirst10BySymbolOrderByCreateTimeDesc(String symbol);
}
