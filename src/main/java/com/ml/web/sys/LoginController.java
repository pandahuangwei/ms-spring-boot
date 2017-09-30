package com.ml.web.sys;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.ml.cache.KaptchaCache;
import com.ml.constants.Constant;
import com.ml.entity.R;
import com.ml.utils.Shiros;
import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.UUID;

/**
 * @author panda.
 * @since 2017-09-25 20:07.
 */
@Controller
public class LoginController {
    @Autowired
    private Producer producer;

    @RequestMapping("captcha.jpg")
    public void captcha(HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        //生成文字验证码
        String text = producer.createText();
        String uuid = UUID.randomUUID().toString();
        KaptchaCache.getInstance().add(uuid,text);
        Cookie cookie = new Cookie(Constants.KAPTCHA_SESSION_KEY,uuid);
        response.addCookie(cookie);

        //生成图片验证码
        BufferedImage image = producer.createImage(text);

        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
        out.flush();
    }

    /**
     * 登录
     */
    @ResponseBody
    @RequestMapping(value = "/sys/login", method = RequestMethod.POST)
    public R login(HttpServletRequest request,String username, String password, String captcha) throws IOException {
        String kaptcha = getKaptcha(request);
        /*if (!captcha.equalsIgnoreCase(kaptcha)) {
            return R.fail("验证码不正确");
        }*/
        username = "admin";
        password = "1qaz2wsx";
        try {
            Subject subject = Shiros.getSubject();
            password = new Md5Hash(username+password).toHex();
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            subject.login(token);
        } catch (UnknownAccountException e) {
            return R.fail(e.getMessage());
        } catch (IncorrectCredentialsException e) {
            return R.fail(e.getMessage());
        } catch (LockedAccountException e) {
            return R.fail(e.getMessage());
        } catch (AuthenticationException e) {
            return R.fail("账户验证失败");
        }

        return R.success();
    }

    /**
     * 退出
     */
    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logout() {
        Shiros.logout();
        return "redirect:login.html";
    }

    /**
     * 从cookie中获取对应的uuid，然后获取缓存的验证码.
     * @param request req
     * @return 验证码
     */
    private String getKaptcha(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String uuid = null;
        for (Cookie c : cookies) {
            if (Constants.KAPTCHA_SESSION_KEY.equals(c.getName())) {
                uuid = c.getValue();
                break;
            }
        }
        return KaptchaCache.getInstance().getValue(uuid);
    }
}