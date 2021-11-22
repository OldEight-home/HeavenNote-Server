package com.oldeighthome.heavennote.service.impl;

import com.oldeighthome.heavennote.entity.Note;
import com.oldeighthome.heavennote.mapper.NoteMapper;
import com.oldeighthome.heavennote.service.INoteService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class NoteServiceImpl extends ServiceImpl<NoteMapper, Note> implements INoteService {

}
