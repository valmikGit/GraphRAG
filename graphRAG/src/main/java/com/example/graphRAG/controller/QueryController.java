package com.example.graphRAG.controller;

import com.example.graphRAG.service.NLPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/auth/query")
@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class QueryController {
    @Autowired
    private NLPService nlpService;
}
