package com.gene.service.system.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.gene.dto.base.DataGrid;
import com.gene.dto.base.ResponseVo;
import com.gene.entity.system.LoginRecord;
import com.gene.mapper.system.LoginRecordMapper;
import com.gene.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class LoginRecordServiceImpl {
    @Autowired
    private LoginRecordMapper loginRecordMapper;

    public boolean add(LoginRecord loginRecord) {
        loginRecord.setId(UUIDUtil.randomUUID32());
        loginRecord.setCreateTime(new Date());
        return loginRecordMapper.insert(loginRecord) > 0;
    }

    public ResponseVo<DataGrid> list(int pageNum, int pageSize, String startDate, String endDate, String account) {
        Page<LoginRecord> page = new Page<>(pageNum, pageSize);
        page.setRecords(loginRecordMapper.listFull(page, startDate, endDate, account));
        DataGrid dataGrid = new DataGrid(page.getTotal(), page.getRecords());
        return ResponseVo.ofSuccess(dataGrid);
    }
}