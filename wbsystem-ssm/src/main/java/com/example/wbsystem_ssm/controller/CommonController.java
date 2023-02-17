package com.example.wbsystem_ssm.controller;

import com.example.wbsystem_ssm.entity.Card;
import com.example.wbsystem_ssm.entity.ResultBean;
import com.example.wbsystem_ssm.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CommonController {
    @Autowired
    private CardService cardService;


}
