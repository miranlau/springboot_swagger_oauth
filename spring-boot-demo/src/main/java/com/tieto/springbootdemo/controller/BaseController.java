package com.tieto.springbootdemo.controller;

import com.tieto.springbootdemo.entity.vo.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

/**
 * @ClassName: RestApiBase
 * @Description: Base calss for all Sub contorller. Defined some public functions and variables.
 * @Copyright: 2017 www.tieto.com Inc. All rights reserved.
 */
public class BaseController {

    protected final Response createResponse(HttpStatus status, Object entity) {
        return Response.newInstance().status(status.value()).type(MediaType.APPLICATION_JSON.toString()).entity(entity).build();
    }

    protected final Response createResponse(HttpStatus status) {
        //No message body to be included. Thus no media type either.
        return Response.newInstance().status(status.value()).build();
    }

}
