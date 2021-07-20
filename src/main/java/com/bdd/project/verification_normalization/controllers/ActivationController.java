package com.bdd.project.verification_normalization.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/")
public class ActivationController {

    @Autowired
    private MainFlowManager mainFlowManager;

    @GetMapping("activate")
    public String startVerifyAndNormalise() {
        System.out.println("start verify");
        mainFlowManager.startFlow();
        return "done";
    }
}

