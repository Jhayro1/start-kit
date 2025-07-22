package com.winkstec.service.email;

import java.util.Map;

public interface EmailComposerService {
    String compose(String templateName, Map<String, Object> data);
}