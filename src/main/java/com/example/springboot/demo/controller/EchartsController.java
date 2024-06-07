package com.example.springboot.demo.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.Month;
import com.example.springboot.demo.common.R;
import com.example.springboot.demo.pojo.User;
import com.example.springboot.demo.pojo.dto.UserDTO;
import com.example.springboot.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;



@RestController
@RequestMapping("/echarts")
public class EchartsController {
    @Autowired
    private UserService userService;
    @GetMapping("/addressCount")
    public R addressCount(){

       List<UserDTO> users = userService.addressCount();
       List<String> list1 = new ArrayList<>();
       List<String> list2 = new ArrayList<>();
        Map<Object,Object> map =new HashMap<>();

        for (UserDTO userAddress:users
             ) {
           list1.add(userAddress.getAddress());
           list2.add(userAddress.getAddressCount());

        }
        map.put("x",list1);
        map.put("y",list2);
        return R.success(map);
    }

    @GetMapping("/example")
    public R get(){
        Map<String,Object> map =new HashMap<>();
        map.put("x", CollUtil.newArrayList("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"));
        map.put("y",CollUtil.newArrayList(150, 230, 224, 218, 135, 147, 260));
        return R.success(map);
    }

    @GetMapping("/members")
    public R getNumber(){
        List<User> list =userService.list();
        int m1 =0;//一月
        int m2 =0;//二月
        int m3 =0;//三月
        int m4 =0;//四月
        int m5 =0;//五月
        int m6 =0;//六月
        int m7 =0;//七月
        int m8 =0;//八月
        int m9 =0;//九月
        int m10 =0;//十月
        int m11 =0;//十一月
        int m12 =0;//十二月
        for (User user: list
             ) {
            Date createTime = user.getCreateTime();
            Month month = DateUtil.monthEnum(createTime);
            switch (month){
                case JANUARY: m1+=1;
                    break;
                case FEBRUARY: m2+=1;
                    break;
                case MARCH: m3+=1;
                    break;
                case APRIL: m4+=1;
                    break;
                case MAY: m5+=1;
                    break;
                case JUNE: m6+=1;
                    break;
                case JULY: m7+=1;
                    break;
                case AUGUST: m8+=1;
                    break;
                case SEPTEMBER: m9+=1;
                    break;
                case OCTOBER: m10+=1;
                    break;
                case NOVEMBER: m11+=1;
                    break;
                case DECEMBER: m12+=1;
                    break;
                default: break;
            }
        }
        return R.success(CollUtil.newArrayList(m1,m2,m3,m4,m5,m6,m7,m8,m9,m10,m11,m12));

    }
}
