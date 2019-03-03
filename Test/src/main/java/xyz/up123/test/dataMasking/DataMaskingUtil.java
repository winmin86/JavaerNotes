package xyz.up123.test.dataMasking;

import java.util.HashMap;
import java.util.Map;

/**
 * @ProjectName: JavaerNotes
 * @Package: xyz.up123.test.dataMasking
 * @ClassName: DataMaskingUtil
 * @Description: java类作用描述
 * @Author: zhuwenming
 * @CreateDate: 2019/3/1 14:30
 * @Version: 1.0
 */
public class DataMaskingUtil {
    private Map<String, DataMasking> dataMaskings;
    private DataMaskingUtil() {
        dataMaskings = new HashMap<>();
        DataMasking dm = new DataMasking();
        dm.setName("useName");
        dm.setType(MaskingType.LEFT_RIGHT);
        dm.setLeft(1);
        dm.setRight(1);
        dataMaskings.put("useName", dm);

        DataMasking dm1 = new DataMasking();
        dm1.setName("useNo");
        dm1.setType(MaskingType.LEFT_RIGHT);
        dm1.setLeft(4);
        dm1.setRight(4);
        dataMaskings.put("useNo", dm1);
    }

    static class DM {
        private static DataMaskingUtil instance = new DataMaskingUtil();
    }

    public static DataMaskingUtil getInstance() {
        return DM.instance;
    }

    public DataMasking get(String name) {
        if (dataMaskings == null) {
            return null;
        }
        return dataMaskings.get(name);
    }
}
