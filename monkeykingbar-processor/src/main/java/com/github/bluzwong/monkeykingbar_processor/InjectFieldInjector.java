package com.github.bluzwong.monkeykingbar_processor;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangzhijie@wind-mobi.com on 2015/9/24.
 */
public class InjectFieldInjector {


    private String fieldName, fieldType;

    public static final String PREFIX = "MKB_";
    public InjectFieldInjector(String fieldName, String fieldType) {
        this.fieldName = fieldName;
        this.fieldType = fieldType;
    }


    public String getFieldName() {
        return fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public String brewInjectExtrasJava() {
        StringBuilder builder = new StringBuilder();
        builder.append("if ("+ fieldName +"_defaultType) {\n");
        builder.append(" obj = MKBUtils.getExtra(intent, \""+ PREFIX + fieldName +"\");\n");

        builder.append("} else {\n");
        builder.append(" obj = MKBUtils.getExtraViaByteArray(intent, \""+ PREFIX + fieldName +"\");\n");
        builder.append("}\n");
        builder.append(" if (obj != null) { ");
        builder.append("    target." + fieldName + " = ("+ fieldType +") obj;");
        builder.append(" }");

        return builder.toString();
    }

    public String brewGetStartIntent() {
        StringBuilder builder = new StringBuilder();

        builder.append(fieldName +"_defaultType = MKBUtils.isDefaultType("  +fieldName+");\n");
        builder.append("if ("+ fieldName +"_defaultType) {\n");
        builder.append("intent.putExtra(\""+ PREFIX + fieldName +"\", " + fieldName + ");\n");
        builder.append("} else {\n");
        builder.append("MKBUtils.putExtraViaByteArray(intent,").append( "\"" + PREFIX + fieldName +"\", " + fieldName).append(");");
        builder.append("}\n");

        return builder.toString();
    }

    public static void main(String[] args) {
        System.out.println(new InjectFieldInjector("ccf", "String").brewInjectExtrasJava());
        System.out.println(new InjectFieldInjector("ccf", "String").brewGetStartIntent());
    }

}
