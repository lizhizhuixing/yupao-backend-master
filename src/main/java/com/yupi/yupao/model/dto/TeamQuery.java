package com.yupi.yupao.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.yupi.yupao.common.PageRequest;
import lombok.Data;

import java.util.List;

@Data
public class TeamQuery extends PageRequest {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     *
     */
    List<Long> IdList;
    /**
     * 队伍名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;
    /**
     * 关键词
     */
    private String SearchText;

    /**
     * 最大人数
     */
    private Integer maxNum;

    /**
     * 用户id（队长 id）
     */
    private Long userId ;

    /**
     * 0 - 公开，1 - 私有，2 - 加密
     */
    private Integer status;



}
