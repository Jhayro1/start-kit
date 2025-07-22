package com.winkstec.service.email.impl;

import com.winkstec.service.email.TemplateEngineService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class FreeMarkerTemplateEngineServiceImpl implements TemplateEngineService {

    private final Configuration freemarkerConfig;

    @Override
    public String render(String templateName, Map<String, Object> variables) {
        try {
            Template template = freemarkerConfig.getTemplate("email/" + templateName + ".ftl");
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, variables);
        } catch (Exception e) {
            throw new RuntimeException("Error al renderizar plantilla: " + templateName, e);
        }
    }
}
