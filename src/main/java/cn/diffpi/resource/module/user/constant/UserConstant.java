package cn.diffpi.resource.module.user.constant;

/***
 * 用户管理一些常量
 */
public class UserConstant {
    /***
     * 充值支付回调类
     */
    public static final String RRCHARGE_PAY_TARGET = "recharge";

    /***
     * 钱包明细使用状态
     */
    public static enum WalletState{
        /**
         * 等待，有后续操作的时候用到
         */
        WAIT(0),
        /***
         * 及时, 会马上执行到钱包上，并修改余额
         */
        TIMELY(1);

        private final int value;

        WalletState(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /***
     * 是加是减
     */
    public static enum PlusMinus{
        /**
         * 加
         */
        PLUS(1),
        /***
         * 减
         */
        MINUS(0);


        private final int value;

        PlusMinus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /***
     * 消费类型
     */
    public static enum WalletType{
        /**
         * 充值
         */
        RECHARGE(0),
        /***
         * 提现
         */
        WITHDRAWALS(1),
        /***
         * 收益
         */
        PROFIT(2),
        /***
         * 下注
         */
        CONSUMPTION(3),

        /***
         * 变更
         */
        CHANGE(4);

        private final int value;

        WalletType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
