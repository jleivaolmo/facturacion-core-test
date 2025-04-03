package com.echevarne.sap.cloud.facturacion.util;

import java.util.HashMap;
import java.util.Map;

public class InterlocutoresUtil {

    public static Map<String, String> codeByRol = new HashMap<>();
    public static Map<String, Boolean> validToCreate = new HashMap<>();
    public static Map<String, Boolean> validToSave = new HashMap<>();

    static {
        // TODO: load this values from a table like MasDataTipoPosicion
        codeByRol.put("ZC", "CIA");
        codeByRol.put("ZR", "REM");
        codeByRol.put("ZH", "PA");
        codeByRol.put("ZX", "");
        codeByRol.put("ZV", "VIS");
        codeByRol.put("ZL", "");
        codeByRol.put("ZE", "EMP");
        codeByRol.put("ZP", "PR");
    }
    
    static {
    	validToCreate.put("ZC", true);
    	validToCreate.put("ZR", true);
    	validToCreate.put("ZH", true);
    	validToCreate.put("ZE", true);
    	validToCreate.put("ZP", true);
    	validToCreate.put("ZM", true);
    	validToCreate.put("ZV", true);
    }
    
    static {
    	validToSave.put("ZC", true);
    	validToSave.put("ZR", true);
    	validToSave.put("ZH", true);
    	validToSave.put("ZE", true);
    	validToSave.put("ZP", true);
    	validToSave.put("ZV", true);
    	validToSave.put("ZL", true);
    	validToSave.put("ZX", true);
    }

    public static String createCustomerKey(String code, String rol) {
        if (!codeByRol.containsKey(rol) || code.length() > 10) return "INVALID KEY";
        String newCode = code.replaceFirst("^0+(?!$)", "");
        String prefix = codeByRol.get(rol);
        String zeros = "0000000000";
        String fillers = zeros.substring(prefix.length(), 10 - newCode.length());
        return prefix + fillers + newCode;
    }
}