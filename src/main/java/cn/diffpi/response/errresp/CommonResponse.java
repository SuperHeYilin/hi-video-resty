package cn.diffpi.response.errresp;

/**
 * <pre>
 *    通用的响应对象
 * </pre>
 */
public class CommonResponse{

    private boolean successful = false;

    public static final CommonResponse SUCCESSFUL_RESPONSE = new CommonResponse(true);
    public static final CommonResponse FAILURE_RESPONSE = new CommonResponse(false);

    public CommonResponse() {
    }

    private CommonResponse(boolean successful) {
        this.successful = successful;
    }

    public boolean isSuccessful() {
        return successful;
    }
}

