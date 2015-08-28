package build.builder.app;

import java.util.HashMap;

/**
 * Created by Ivan Nikerin [mail:fenderbas666@gmail.com]
 */
public class ConfigDataParam {

    private HashMap confParam;
    private static ConfigDataParam instance = new ConfigDataParam();
    private ConfigDataParam() {
    }
    public static ConfigDataParam getInstance(){
        return instance;
    }
    public HashMap getParamConfig() {
        return confParam;
    }
    public void setParamConfig(HashMap<String,String> confParam) {
        this.confParam = confParam;
    }

}
