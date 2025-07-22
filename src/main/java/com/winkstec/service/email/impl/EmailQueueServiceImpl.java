package com.winkstec.service.email.impl;

import com.winkstec.dto.request.email.EmailRequest;
import com.winkstec.mapper.EmailMapper;
import com.winkstec.repository.jpa.email.BandejaEmailRepository;
import com.winkstec.service.email.EmailQueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailQueueServiceImpl implements EmailQueueService {

    private final BandejaEmailRepository bandejaEmailRepository;
    private final EmailMapper emailMapper;

    @Override
    public void registrarEmail(EmailRequest request) {
        var entity = emailMapper.toEntity(request);

        System.out.println("[registrarEmail] Intentando guardar correo en bandeja:");
        System.out.println("  Para: " + request.getTo());
        System.out.println("  Asunto: " + request.getSubject());
        System.out.println("  Es HTML: " + request.isHtml());

        var saved = bandejaEmailRepository.save(entity);

        System.out.println("[registrarEmail] Correo guardado exitosamente:");
        System.out.println(saved);
    }

}
