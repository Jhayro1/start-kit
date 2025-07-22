package com.winkstec.service.email.impl;

import com.winkstec.service.email.EmailComposerService;
import com.winkstec.service.email.TemplateEngineService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailComposerServiceImpl implements EmailComposerService {

    private final TemplateEngineService templateEngine;

    @Override
    public String compose(String templateName, Map<String, Object> data) {
        return templateEngine.render(templateName, data);
    }
}

