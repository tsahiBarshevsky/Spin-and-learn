package tsahi.and.kostia.spinandlearn;

import android.app.Application;
import android.content.res.Resources;

public class GlobalVar extends Application {
    private Resources gRes;

    public Resources getgRes() {
        return gRes;
    }

    public void setgRes(Resources gRes) {
        this.gRes = gRes;
    }
}
