package cn.diffpi.resource.platform.file.model;

import cn.dreampie.orm.Model;
import cn.dreampie.orm.annotation.Table;

@Table(name="pt_media",cached=true)
public class PtMedia extends Model<PtMedia>{
	public static final PtMedia dao = new PtMedia();
	
	
}
