package com.yupi.yupao.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class PageRequest implements Serializable {
    private static final long serialVersionUID = 4874479552841511870L;
    //页面大小
    protected int PageSize = 10;
    //第几页
    protected int PageNum = 1;
}
