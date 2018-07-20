package cn.diffpi.resource.module.statistics;

import java.math.BigDecimal;

public class test {

	public static void main(String[] args) {
		toFormat(12345,0.01,2);

	}
	public static double toFormat(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("保留小数 参数出错");
        }
        BigDecimal b2 ;
        if (v2 == 0) {
        	b2 = new BigDecimal(Double.toString(1));
        }else{
        	b2 = new BigDecimal(Double.toString(v2));
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b3 = new BigDecimal(Double.toString(100.00));
        System.out.println("增长率：当前：" + b1 + " 对比：" + b2 + " 结果: " +
                (b1.subtract(v2==0?new BigDecimal(0):b2)).multiply(b3).divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue()
        );

        return (b1.subtract(b2)).multiply(b3).divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

}
