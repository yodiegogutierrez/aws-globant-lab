/**
 * This is a lab for Unison Team
 * for new developers.
 * Author: Diego Gutierrez
 * 2021
 **/

package com.project.starter.controller;

import com.project.starter.service.SNSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/sns/send")
public class SNSController {

    private SNSService snsService;

    public SNSController(@Autowired SNSService snsSendMessage) {
        this.snsService = snsSendMessage;
    }

    @PostMapping
    public String sendMessage(@RequestBody String message) {
        return snsService.send(message);
    }
}
