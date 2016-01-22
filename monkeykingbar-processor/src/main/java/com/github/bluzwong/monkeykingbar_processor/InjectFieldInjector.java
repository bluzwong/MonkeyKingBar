package com.github.bluzwong.monkeykingbar_processor;


import java.util.UUID;

/**
 * Created by wangzhijie@wind-mobi.com on 2015/9/24.
 */
public class InjectFieldInjector {


    private String fieldName, fieldType;
    public static final String PREFIX = "MKB_";
    private String key = "";
    public InjectFieldInjector(String fieldName, String fieldType) {
        this.fieldName = fieldName;
        this.fieldType = fieldType;
        this.key = PREFIX + fieldName + "@" + UUID.randomUUID();
    }


    public String getFieldName() {
        return fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public String brewInjectExtrasJava() {
        StringBuilder builder = new StringBuilder();
        builder.append(" obj = MKBUtils.getExtra(intent, \""+ key +"\");\n");

        builder.append(" if (obj != null) { ");
        builder.append("    target." + fieldName + " = ("+ fieldType +") obj;");
        builder.append(" }");

        return builder.toString();
    }


    public String brewPutExtra() {
        StringBuilder builder = new StringBuilder();

        builder.append("MKBUtils.putExtra(intent,\""+ key +"\", " + fieldName + ");\n");

        return builder.toString();
    }

    public static void main(String[] args) {
        System.out.println(new InjectFieldInjector("ccf", "String").brewInjectExtrasJava());
    }

}
