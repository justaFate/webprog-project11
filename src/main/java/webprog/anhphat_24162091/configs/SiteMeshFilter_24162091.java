package webprog.anhphat_24162091.configs;

import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;

import jakarta.servlet.annotation.WebFilter;

@WebFilter(filterName = "sitemesh", urlPatterns = "/*")
public class SiteMeshFilter_24162091 extends ConfigurableSiteMeshFilter {

    @Override
    protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
        // Vai trò Admin áp dụng decorator admin.jsp
        builder.addDecoratorPath("/admin/*", "/decorators/admin.jsp")
               .addDecoratorPath("/admin", "/decorators/admin.jsp")
               // Vai trò User / Khách áp dụng decorator web.jsp
               .addDecoratorPath("/*", "/decorators/web.jsp")
               // Các tài nguyên tĩnh loại trừ khỏi Decorator
               .addExcludedPath("/static/*")
               .addExcludedPath("/assets/*")
               .addExcludedPath("/uploads/*")
               .addExcludedPath("/image*");
    }
}

