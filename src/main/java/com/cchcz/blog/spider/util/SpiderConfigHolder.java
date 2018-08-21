package com.cchcz.blog.spider.util;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.cchcz.blog.model.entity.SpiderConfig;
import com.cchcz.blog.service.SysSpiderConfigService;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * <Description> 爬虫配置Holder</Description>
 * <ClassName> SpiderConfigHolder</ClassName>
 *
 * @author cchcz
 * @date 2018年03月09日 10:48
 */
public class SpiderConfigHolder {
    private static Map<String, Map<String, List<String>>> spiderConfigCache = new HashMap<>();

    private static SysSpiderConfigService sysSpiderConfigService;

    private static CharMatcher RN_CHAR = new CharMatcher() {
        @Override
        public boolean matches(char c) {
            return c == '\r' || c == '\n';
        }
    };

    public static List<String> get(String spiderType, String module) {
        Map<String, List<String>> stringListMap = spiderConfigCache.get(spiderType);
        return Optional.ofNullable(stringListMap != null ? stringListMap.get(module) : null).orElse(new ArrayList<>());
    }

    public static void loadSpiderConfig() {
        List<SpiderConfig> spiderVoList = null;
            spiderVoList = sysSpiderConfigService.listAll();
        Map<String, Map<String, List<String>>> spiderConfig_ = new HashMap<>();
        spiderVoList.forEach(vo -> {
            Map<String, List<String>> spiderConfig = new HashMap<>();
            String key = vo.getNetEng().toLowerCase();
            String parseTitle = vo.getParseTitle();
            spiderConfig.put("title", Splitter.on("|").trimResults(RN_CHAR).splitToList(parseTitle));
            String parseKeywords = vo.getParseKeywords();
            if (StringUtils.isNotEmpty(parseKeywords)) {
                spiderConfig.put("keyword", Splitter.on("|").trimResults(RN_CHAR).splitToList(parseKeywords));
            }
            String parseContent = vo.getParseContent();
            spiderConfig.put("content", Splitter.on("|").trimResults(RN_CHAR).splitToList(parseContent));
            spiderConfig_.put(key, spiderConfig);
        });
        spiderConfigCache.clear();
        spiderConfigCache.putAll(spiderConfig_);
    }

    public static void setSysSpiderConfigService(SysSpiderConfigService sysSpiderConfigService) {
        SpiderConfigHolder.sysSpiderConfigService = sysSpiderConfigService;
    }
}