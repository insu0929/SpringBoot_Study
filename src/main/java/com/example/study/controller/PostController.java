package com.example.study.controller;


import com.example.study.model.SearchParam;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PostController {

    //HTML <Form>
    //ajax 검색
    //http post body -> data
    // json, xml, multipart-form / text-plain


    @PostMapping("/postMethod")
    public String postMethod(@RequestBody SearchParam searchParam){



        return "OK";

    }


}
