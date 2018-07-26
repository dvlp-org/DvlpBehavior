package news.dvlp.testbehavior;

import android.app.Application;

import news.dvlp.testbehavior.utils.Utils;

/**
 * Created by liubaigang on 2018/7/26.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
    }
}
