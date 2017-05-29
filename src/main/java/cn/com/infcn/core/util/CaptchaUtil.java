package cn.com.infcn.core.util;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

/**
 * 验证码工具类
 * 
 * @author NOLY DAKE
 *
 */
public class CaptchaUtil {

    /**
     * 用户生成随机颜色的数组
     */
    private static final Color[] colorArray = new Color[] { Color.BLUE, Color.BLACK, Color.RED, new Color(180, 53, 62),
            new Color(94, 139, 54), new Color(20, 85, 140), new Color(233, 164, 28) };

    /**
     * 当前正在使用的验证码key
     */
    public static final String CURRENT_CAPTCHA_KEY = "CURRENT_CAPTCHA_KEY";

    /**
     * 验证当前验证码是否为一个合法的验证码<br>
     * 验证结束后，会清理session中已经存在的验证码
     * 
     * @return null时为正确的验证码，否则返回错误消息
     */
    public static String isValidate(String captcha, HttpSession session) {

        String sessionCaptcha = (String) session.getAttribute(CURRENT_CAPTCHA_KEY);
        if (StringUtils.isEmpty(sessionCaptcha)) {
            return "验证码已过期";
        }

        if (!sessionCaptcha.equalsIgnoreCase(captcha)) {
            return "验证码输入错误";
        }

        return null;
    }

    /**
     * 
     * 描述: 返回一个随机的颜色
     *
     * @return Color
     */
    public static Color randomColor() {

        Random random = new Random();
        int nextInt = random.nextInt(colorArray.length);
        return colorArray[nextInt];
    }
}
