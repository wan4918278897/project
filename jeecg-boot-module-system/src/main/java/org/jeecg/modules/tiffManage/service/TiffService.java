package org.jeecg.modules.tiffManage.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author yzx
 */
@Service
@DS("multi-datasource1")
public class TiffService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> selectAll() {
        long TimeNow = System.currentTimeMillis();
        //获得系统时间，一般用long类型的，得到此事件，currentTimeMillis(）方法精确到毫秒
        System.out.println("此刻时间"+TimeNow);
        //输出为long类型的语言，机器能读懂的语言
        Date date=new Date();
        //用Date类来转化这个时间
        System.out.println("转化的时间"+date.getTime());
        Calendar calendar=Calendar.getInstance();
        //获得此刻的时间
        System.out.println("Calendar获得的时间"+calendar.getTime());

        return  jdbcTemplate.queryForList("select title,sender1,recvtime,faxfile from aofax_recv_task");
    }


}
