package yp.e3mall.search.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author RickYinPeng
 * @ClassName GlobalExceptionResolver
 * @Description 全局异常处理
 * @date 2018/11/28/11:47
 */
public class GlobalExceptionResolver implements HandlerExceptionResolver{

    private static  Logger logger = LoggerFactory.getLogger(GlobalExceptionResolver.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {

        //打印控制台
        e.printStackTrace();
        //写日志
        logger.debug("测试输出的日志.......");
        logger.info("系统发生异常了......");
        logger.error("系统发生异常",e);

        //发邮件、发短信
        //使用jmail工具包。发短信使用第三方的WebService。

        //显示错误页面
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error/exception");

        return modelAndView;
    }


}
