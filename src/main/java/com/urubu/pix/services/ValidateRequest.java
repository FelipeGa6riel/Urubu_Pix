package com.urubu.pix.services;

import com.urubu.pix.dtos.DataDeposit;
import jakarta.servlet.http.HttpServletRequest;

public class ValidateRequest {
    public static void requestValidate(Long senderId, HttpServletRequest request) {
        var id = request.getAttribute("validateId");
        if(!senderId.equals((Long) id)) {
            throw new RuntimeException("Permissão negada");
        }
    }
}
