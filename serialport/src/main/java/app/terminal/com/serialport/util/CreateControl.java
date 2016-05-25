package app.terminal.com.serialport.util;

/**
 * Created by liu.yao on 2016/5/25.
 */
public class CreateControl {
    private static CreateControl createControl;
    private String newctrl;
    private String oldctrl;

    private CreateControl() {
    }

    public static CreateControl getInstance() {
        if (createControl == null) {
            createControl = new CreateControl();
        }
        return createControl;
    }

    public String getNewctrl() {
        return newctrl;
    }

    public void setNewctrl(String newctrl) {
        this.newctrl = newctrl;
    }

    public String getOldctrl() {
        return oldctrl;
    }

    public void setOldctrl(String oldctrl) {
        this.oldctrl = oldctrl;
    }
}
