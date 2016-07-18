package com.mydreamplus.smartdevice.aop;

import com.mydreamplus.smartdevice.config.Constant;
import com.mydreamplus.smartdevice.entity.BaseEntity;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: liji
 * Date: 16/7/11
 * Time: 下午5:47
 * To change this template use File | Settings | File Templates.
 */
@Aspect
@Component(value = "repositoryAspect")
public class RepositoryAspect {

    @Before("execution(public * com.mydreamplus.smartdevice.service.*Service.save*(..)) &&" + "args(entity,..)")
    public void fillCreateInformation(BaseEntity entity) throws Throwable {

        if(entity != null){
            entity.setCreateTime(new Date(System.currentTimeMillis()));
            entity.setUpdateTime(entity.getCreateTime());
            entity.setCreateBy(Constant.DEFAULT_CREATE_USER);
        }
        System.out.println(":::::AOP Before Service save call:::::" + entity);
    }

    @Before("execution(public * com.mydreamplus.smartdevice.service.*Service.update*(..)) &&" + "args(entity,..)")
    public void fillUpdateInformation(BaseEntity entity) throws Throwable {

        if(entity != null){
            entity.setUpdateTime(entity.getCreateTime());
        }
        System.out.println(":::::AOP Before Service update call:::::" + entity);
    }
}