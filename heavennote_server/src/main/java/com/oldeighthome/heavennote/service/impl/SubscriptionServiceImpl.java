package com.oldeighthome.heavennote.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oldeighthome.heavennote.common.api.ApiResult;
import com.oldeighthome.heavennote.entity.Subscription;
import com.oldeighthome.heavennote.mapper.SubscriptionMapper;
import com.oldeighthome.heavennote.service.ISubscriptionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oldeighthome.heavennote.util.UUIDUtil;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author YY
 * @since 2021-11-21
 */
@Service
public class SubscriptionServiceImpl extends ServiceImpl<SubscriptionMapper, Subscription> implements ISubscriptionService {

    @Override
    public ApiResult addSubscription(String userId, String noteId) {
        if(getOne(new QueryWrapper<Subscription>().eq("author_id",userId).eq("note_id",noteId))==null){
            Subscription subscription=new Subscription();
            String id= UUIDUtil.getUUID();
            subscription.setAuthorId(userId)
                    .setId(id)
                    .setNoteId(noteId);
            save(subscription);
            return ApiResult.success("订阅成功");
        }
        else {
            return ApiResult.error("已经订阅过了");
        }
    }
}
