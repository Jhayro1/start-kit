package com.winkstec.service.email;

import java.util.Map;

public interface TemplateEngineService {
    String render(String templateName, Map<String, Object> variables);
}

