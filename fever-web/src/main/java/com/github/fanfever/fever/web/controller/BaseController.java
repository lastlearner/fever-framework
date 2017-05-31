package com.github.fanfever.fever.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * @author fanfever
 * @email fanfeveryahoo@gmail.com
 * @url https://github.com/fanfever
 * @date 2016年7月9日
 */
@Slf4j
public class BaseController {

    protected static final String APPLICATION_JSON = "application/json";

    protected String getRemoteHost() {
        String url = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
                .getHeader("referer");
        URI uri = null;
        try {
            uri = new URI(url);
        } catch (URISyntaxException e) {
            log.warn("getRemoteHost url:{}, e:{}", url, e);
            return null;
        }
        return uri.getHost();
    }

    protected HttpStatus foundHttpStatus(Object object) {
        boolean isHasContent;
        if (object instanceof List) {
            isHasContent = CollectionUtils.isNotEmpty((List) object);
        } else {
            isHasContent = null != object;
        }
        return isHasContent ? HttpStatus.OK : HttpStatus.OK;
    }

    public static String getRemoteAddr(HttpServletRequest request) {
        String remoteAddr = request.getHeader("X-Real-IP");
        if (StringUtils.isBlank(remoteAddr)) {
            remoteAddr = request.getHeader("X-Forwarded-For");
            if (StringUtils.isBlank(remoteAddr)) {
                remoteAddr = request.getHeader("Proxy-Client-IP");
                if (StringUtils.isBlank(remoteAddr)) {
                    remoteAddr = request.getHeader("WL-Proxy-Client-IP");
                }
            }
        }
        return remoteAddr;
    }
}
