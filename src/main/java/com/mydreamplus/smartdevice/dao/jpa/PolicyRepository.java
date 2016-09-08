package com.mydreamplus.smartdevice.dao.jpa;

import com.mydreamplus.smartdevice.entity.PI;
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
     * @param name    the name
     * @param deleted the deleted
     * @return the device type
     */
    Policy findByNameAndDeleted(String name, boolean deleted);

    /**
     * Find by master event and deleted policy.
     *
     * @param masterEvent the master event
     * @param deleted     the deleted
     * @return the policy
     */
    Policy findByMasterEventAndDeleted(String masterEvent, boolean deleted);


    /**
     * Search page.
     *
     * @param policy   the policy
     * @param pageable the pageable
     * @return the page
     */
    Page<Policy> search(Policy policy, Pageable pageable);

    /**
     * Search page.
     *
     * @param policy    the policy
     * @param pageable  the pageable
     * @param searchKey the search key
     * @return the page
     */
    Page<Policy> search(Policy policy, Pageable pageable, String searchKey);


    /**
     * Find all policy by update time greater than list.
     *
     * @param pi           the pi
     * @param updateTime   the update time
     * @param isRootPolicy the is root policy
     * @param isDeleted    the is deleted
     * @param isDisabled   the is disabled
     * @return the list
     */
    List<Policy> findAllByPiAndUpdateTimeGreaterThanAndIsRootPolicyAndDeletedAndIsDisabled(PI pi, Date updateTime, boolean isRootPolicy, boolean isDeleted, boolean isDisabled);

    /**
     * Find all policy by update time greater than list.
     *
     * @param pi           the pi
     * @param updateTime   the update time
     * @param isRootPolicy the is root policy
     * @return the list
     */
    List<Policy> findAllByPiAndUpdateTimeGreaterThanAndIsRootPolicy(PI pi, Date updateTime, boolean isRootPolicy);


    /**
     * Find all by pi and update time list.
     *
     * @param pi         the pi
     * @param updateTime the update time
     * @return the list
     */
    List<Policy> findAllByPiAndUpdateTimeGreaterThan(PI pi, Date updateTime);


    /**
     * Find all policy by like name list.
     *
     * @param name    the name
     * @param disable the is deleted
     * @return the list
     */
    List<Policy> findAllPolicyByLikeName(String name, boolean disable);
}
