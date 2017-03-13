package com.technonet.staticData;

import com.technonet.model.WeekDay;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 * Created by kaxa on 3/8/17.
 */
public class Variables {
    //public static String appDir="/Users/kaxa/Documents/Code/teacherportalweb/build/app";
    public static String appDir="D:/app";

    public static BufferedImage getScaledInstance(BufferedImage img, int targetWidth, int targetHeight, Object hint,
                                                   boolean higherQuality) {
        int type = (img.getTransparency() == Transparency.OPAQUE) ? BufferedImage.TYPE_INT_RGB
                : BufferedImage.TYPE_INT_ARGB;
        BufferedImage ret = (BufferedImage) img;
        if (ret.getHeight() < targetHeight || ret.getWidth() < targetWidth) {
            higherQuality = false;
        }
        int w, h;
        if (higherQuality) {
            // Use multi-step technique: start with original size, then
            // scale down in multiple passes with drawImage()
            // until the target size is reached
            w = img.getWidth();
            h = img.getHeight();
        } else {
            // Use one-step technique: scale directly from original
            // size to target size with a single drawImage() call
            w = targetWidth;
            h = targetHeight;
        }

        do {
            if (higherQuality && w > targetWidth) {
                w /= 2;
                if (w < targetWidth) {
                    w = targetWidth;
                }
            }

            if (higherQuality && h > targetHeight) {
                h /= 2;
                if (h < targetHeight) {
                    h = targetHeight;
                }
            }

            BufferedImage tmp = new BufferedImage(w, h, type);
            Graphics2D g2 = tmp.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
            g2.drawImage(ret, 0, 0, w, h, null);
            g2.dispose();

            ret = tmp;
        } while (w != targetWidth || h != targetHeight);

        return ret;
    }
    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }

    public static HashMap<Integer,WeekDay> getWeekDays(){
        HashMap<Integer,WeekDay> hashMap=new HashMap<>();
        hashMap.put(0,new WeekDay(0,"ორშაბათი"));
        hashMap.put(1,new WeekDay(1,"სამშაბათი"));
        hashMap.put(2,new WeekDay(2,"ოთხშაბათი"));
        hashMap.put(3,new WeekDay(3,"ხუთშაბათი"));
        hashMap.put(4,new WeekDay(4,"პარასკევი"));
        hashMap.put(5,new WeekDay(5,"შაბათი"));
        hashMap.put(6,new WeekDay(6,"კვირა"));

        return hashMap;
    }

}
