package pers.zhentao.bpr;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class LambdaFunctionHandler implements RequestHandler<String, String> {

    @Override
    public String handleRequest(String input, Context context) {
        context.getLogger().log("Input: " + input);
        BPRService service = new BPRService();
        if (input.equals("1")) {
            return service.getConnectEverytime();
        } else {
            return service.getConnectFirstTime();
        }
    }

}
