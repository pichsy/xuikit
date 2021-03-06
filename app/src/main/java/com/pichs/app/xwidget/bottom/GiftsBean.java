package com.pichs.app.xwidget.bottom;

/**
 * @Description:
 * @Author: 吴波
 * @CreateDate: 3/6/21 11:16 AM
 * @UpdateUser: 吴波
 * @UpdateDate: 3/6/21 11:16 AM
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class GiftsBean {

    private String giftName;
    private String giftIcon;
    private String giftPrice;
    private boolean isSelected;

    public String getGiftName() {
        return giftName;
    }

    public GiftsBean setGiftName(String giftName) {
        this.giftName = giftName;
        return this;
    }

    public String getGiftIcon() {
        return giftIcon;
    }

    public GiftsBean setGiftIcon(String giftIcon) {
        this.giftIcon = giftIcon;
        return this;
    }

    public String getGiftPrice() {
        return giftPrice;
    }

    public GiftsBean setGiftPrice(String giftPrice) {
        this.giftPrice = giftPrice;
        return this;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public GiftsBean setSelected(boolean selected) {
        isSelected = selected;
        return this;
    }
}
