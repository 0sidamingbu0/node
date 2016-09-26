package com.mydreamplus.smartdevice.dao.jpa;

import com.mydreamplus.smartdevice.domain.APIUrlTypeEnum;
import com.mydreamplus.smartdevice.entity.APIUrl;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/11
 * Time: 下午5:19
 * To change this template use File | Settings | File Templates.
 */
public interface APIUrlRepository extends PagingAndSortingRepository<APIUrl, Long> {

    List<APIUrl> findAllByType(APIUrlTypeEnum type);

    APIUrl findByUrl(String url);
}
