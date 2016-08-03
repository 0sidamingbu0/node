package com.mydreamplus.smartdevice.dao.jpa;

import com.mydreamplus.smartdevice.entity.Policy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/11
 * Time: 下午7:59
 * To change this template use File | Settings | File Templates.
 */
public interface PolicyRepository extends PagingAndSortingRepository<Policy, Long> {

    /**
     * Find by name device type.
     *
     * @param name the name
     * @return the device type
     */
    Policy findByName(String name);

    /**
     * Find by master event policy.
     *
     * @param masterEvent the master event
     * @return the policy
     */
    Policy findByMasterEvent(String masterEvent);


    /**
     * Search page.
     *
     * @param policy   the policy
     * @param pageable the pageable
     * @return the page
     */
    Page<Policy> search(Policy policy, Pageable pageable);


    /**
     * Find all policy by update time greater than list.
     *
     * @param updateTime the update time
     * @return the list
     */
    List<Policy> findAllByUpdateTimeGreaterThan(Date updateTime);
}
