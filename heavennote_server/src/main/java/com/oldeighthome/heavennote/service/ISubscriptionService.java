package com.oldeighthome.heavennote.service;

import com.oldeighthome.heavennote.common.api.ApiResult;
import com.oldeighthome.heavennote.entity.Subscription;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author YY
 * @since 2021-11-21
 */
public interface ISubscriptionService extends IService<Subscription> {
    public ApiResult addSubscription(String userId,String noteId);
}
