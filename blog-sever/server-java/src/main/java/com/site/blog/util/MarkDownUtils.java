package com.site.blog.util;

import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

public class MarkDownUtils {
    /**
     * 转换md格式为html
     *
     * @param markdownString
     * @return
     */
    public static String mdToHtml(String markdownString) {
        if (!StringUtils.hasText(markdownString)) {
            return "";
        }
        List<Extension> extensions = Arrays.asList(TablesExtension.create());
        Parser parser = Parser.builder().extensions(extensions).build();
        Node document = parser.parse(markdownString);
        HtmlRenderer renderer = HtmlRenderer.builder().extensions(extensions).build();
        return renderer.render(document);
    }
}
