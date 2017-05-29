package cn.com.infcn.core.init;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.ServletContextAware;

/**
 * 
 * Spring启动时加载数据<br>
 * 初始化系统参数等数据<br>
 * 系统启动时，需要预先处理的操作可以放置在这里。本操作在spring启动之后运行，因此我们可以使用例如加载配置文件等操作。<br>
 * 我们再初始化的时候，同时获取到了ServletContext，您也可以使用ServletContext完成例如缓存等操作。<br>
 * 例：配置文件中的hibernate.dialect，可以在这里被使用。
 * 
 * <br>
 * 在成员变量中定义一个属性后，可以通过@value方式引用，如
 * 
 * <pre>
 * 
 * &#64;Value("${hibernate.dialect}")
 * private String hibernateDialect;
 * 
 * </pre>
 * 
 * @author 杨彪
 */
public class SysInitializingBean implements InitializingBean, ServletContextAware {

    /** 系统日志 */
    private static final Logger LOG = Logger.getLogger(SysInitializingBean.class);

    /** 全局变量 */
    private ServletContext servletContext;

    @Value("${hibernate.dialect}")
    private String hibernateDialect;

    /**
     * 初始化方法
     */
    public void init() {

        // TODO:请根据各个项目的要求，自行修改本方法
        LOG.info("系统初始化方法开始...");
        LOG.info("系统启动时，需要预先处理的操作可以放置在这里。本操作在spring启动之后运行，因此我们可以使用例如加载配置文件等操作。");
        LOG.info("我们再初始化的时候，同时获取到了ServletContext，您也可以使用ServletContext完成例如缓存等操作。");
        LOG.info("例：配置文件中的hibernate.dialect，可以在这里被使用。hibernate.dialect=" + hibernateDialect);
        LOG.info("例：servletContext.getRealPath(\".\")=" + servletContext.getRealPath("."));
        LOG.info("系统初始化方法结束");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
}
