package com.cornelius.educenter.service;

import com.cornelius.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cornelius.educenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author cornelius
 * @since 2020-10-11
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(UcenterMember member);

    void register(RegisterVo registerVo);

    UcenterMember getOpenIdMember(String openid);
}
