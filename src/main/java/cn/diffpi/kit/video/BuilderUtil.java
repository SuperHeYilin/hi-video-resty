package cn.diffpi.kit.video;

/**
 * 打开文件的工具类
 */
public class BuilderUtil {
    private BuilderUtil() {
    }

    public static ProcessBuilder getInstance() {
        return BuilderSingleton.INSTANCE.getInstance();
    }

    private static enum BuilderSingleton {
        INSTANCE;

        private ProcessBuilder processBuilder;

        private BuilderSingleton() {
            processBuilder = new ProcessBuilder();
        }

        public ProcessBuilder getInstance() {
            return processBuilder;
        }
    }
}
