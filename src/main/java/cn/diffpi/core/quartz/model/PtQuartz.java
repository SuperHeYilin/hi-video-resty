package cn.diffpi.core.quartz.model;

import cn.diffpi.resource.BaseModel;
import cn.dreampie.orm.annotation.Table;

@Table(name = "pt_task_quartz", cached = true)
public class PtQuartz extends BaseModel<PtQuartz> {
    public static final PtQuartz dao = new PtQuartz();
}
