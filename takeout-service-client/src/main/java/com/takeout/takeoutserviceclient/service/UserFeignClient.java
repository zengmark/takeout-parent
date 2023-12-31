package com.takeout.takeoutserviceclient.service;

import cn.hutool.core.bean.BeanUtil;
import com.takeout.takeoutcommon.common.ErrorCode;
import com.takeout.takeoutcommon.constant.UserConstant;
import com.takeout.takeoutcommon.exception.BusinessException;
import com.takeout.takeoutmodel.dto.UserDto;
import com.takeout.takeoutmodel.entity.AddressInfo;
import com.takeout.takeoutmodel.entity.User;
import com.takeout.takeoutmodel.vo.AddressInfoVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@FeignClient(name = "takeout-user-service", path = "/api/user/inner")
public interface UserFeignClient {
    // PS：Feign 可以有多个 @RequestParam 参数，但是只能有一个 @RequestBody 参数
    @PostMapping("/changeRole")
    int changeRole(@RequestBody UserDto userDto);

    @GetMapping("/get/id")
    User getUserById(@RequestParam("userId") Long userId);

    @GetMapping("/get/address")
    AddressInfo getAddressByUserId(@RequestParam("userId") Long userId);

    @GetMapping("/get/addressByAddressId")
    AddressInfo getAddressByAddressId(@RequestParam("addressInfoId") Long addressInfoId);

    @PutMapping("/updateBalance")
    int updateBalance(@RequestParam("userId") Long userId, @RequestParam("shopUserId") Long shopUserId, @RequestParam("price") Integer price);

    @GetMapping("/test")
    int test();

    default User getLoginUser(HttpServletRequest request) {
        User loginUser = (User) request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        if (loginUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        return loginUser;
    }

    default AddressInfoVO getAddressInfoVO(AddressInfo addressInfo){
        AddressInfoVO addressInfoVO = new AddressInfoVO();
        BeanUtil.copyProperties(addressInfo, addressInfoVO);
        return addressInfoVO;
    }

    default User getSafetyUser(User originUser){
        if (originUser == null) {
            return null;
        }
        User user = new User();
        user.setId(originUser.getId());
        user.setUserAccount(originUser.getUserAccount());
        user.setUserName(originUser.getUserName());
        user.setUserAvatar(originUser.getUserAvatar());
        user.setUserRole(originUser.getUserRole());
        user.setBalance(originUser.getBalance());
        user.setShopId(originUser.getShopId());
        user.setCreateTime(originUser.getCreateTime());
        user.setUpdateTime(originUser.getUpdateTime());
        user.setIsDelete(originUser.getIsDelete());

        return user;
    }

}
