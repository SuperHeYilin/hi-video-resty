package cn.diffpi.kit.video;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 等级分工具类
 */
public class ELOUtil {
	private static final int WIN = 1;
	private static final double SAME = 0.5;
	private static final int LOST = 0;
	private static final int K = 32;

	public static void main(String[] args) {
		countRange(1500, 1000, 0);
	}

	/**
	 * a对比的胜率期望值
	 *
	 * @param a 自己
	 * @param b 对手
	 * @return
	 */
	private static double winHope(int a, int b) {
		double hope = 1 / (1 + Math.pow(10, (b - a) / 400D));
		System.out.println(Math.pow(10, (b - a) / 400D));
		System.out.println(a + " 对 " + b + " 胜率期望：" + hope);
		return hope;
	}

	/**
	 * 改变等级分
	 *
	 * @param range     当前的等级分 默认1500
	 * @param trueScore 比赛中的真实得分
	 * @param hopeScore 期望值
	 * @return 调整后的等级分
	 */
	private static double changeRange(int range, double trueScore, double hopeScore) {
		double ran = range + K * (trueScore - hopeScore);
		return ran;
	}

	/**
	 * 根据传入两个对手的等级分 和比赛结果 返回改变后的数据
	 *
	 * @param a
	 * @param b
	 * @param trueScore 比赛结果是针对前者  后者为相反
	 * @return
	 */
	private static Map<String, Object> countRange(int a, int b, double trueScore) {

		Map<String, Object> map = new HashMap<String, Object>();

		double trueBScore = 0D;

		if (trueScore == SAME) {
			trueBScore = 0.5D;
		}
		if (trueScore == LOST) {
			trueBScore = 1.0D;
		}

		// a 改变后的等级分
//        BigDecimal aTempRange = changeRange(a, trueScore, winHope(a, b));
		double aTempRange = changeRange(a, trueScore, winHope(a, b));
		// b 改变后的等级分   注意结果和a相反
//        BigDecimal bTempRange = changeRange(b, trueBScore, winHope(b, a));
		double bTempRange = changeRange(b, trueBScore, winHope(b, a));

		int aRange = new BigDecimal(Double.toString(aTempRange)).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();

		int bRange = new BigDecimal(Double.toString(bTempRange)).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();

		map.put("a", aRange);
		map.put("b", bRange);

		System.out.println(aRange + " : " + bRange);
		return map;
	}


}
