package com.mydreamplus.smartdevice.dao.jpa;

import com.mydreamplus.smartdevice.domain.EventLogDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/17
 * Time: 上午12:19
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class LogRepository {


    private final Logger log = LoggerFactory.getLogger(LogRepository.class);

    @Async(value = "logExecutor")
    public void saveEventLog(EventLogDto eventLogDto) {
        log.info(String.format(":::::: async save EventLog create time : %tc ", eventLogDto.getCreateAt()));
    }

}
