package com.github.bluzwong.monkeykingbar_processor;


/**
 * Created by wangzhijie@wind-mobi.com on 2015/9/24.
 */
public class KeepFieldInjector {


    private String fieldName, fieldType;

    public static final String PREFIX = "MKB_";
    public KeepFieldInjector(String fieldName, String fieldType) {
        this.fieldName = fieldName;
        this.fieldType = fieldType;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }
    public String brewOnCreateJava() {
        StringBuilder builder = new StringBuilder();

        //builder.append(" obj = MKBUtils.getExtra(savedInstanceState, \""+ PREFIX + fieldName +"\");\n");

        builder.append("if ("+ fieldName +"_defaultType_keep) {\n");
        builder.append(" obj = MKBUtils.getExtra(savedInstanceState, \""+ PREFIX + fieldName +"\");\n");

        builder.append("} else {\n");
        builder.append(" obj = MKBUtils.getExtraViaByteArray(savedInstanceState, \""+ PREFIX + fieldName +"\");\n");
        builder.append("}\n");


        builder.append(" if (obj != null) { ");
        builder.append("    target." + fieldName + " = ("+ fieldType +") obj;");
        builder.append(" }");

        return builder.toString();
    }

    public String brewGetStartIntent() {
        StringBuilder builder = new StringBuilder();

        builder.append(fieldName +"_defaultType_keep = MKBUtils.isDefaultType(target."  +fieldName+");\n");
        builder.append("if ("+ fieldName +"_defaultType_keep) {\n");
        builder.append("MKBUtils.putExtra(outState,").append( "\"" + PREFIX + fieldName +"\", target. " + fieldName).append(");");
        builder.append("} else {\n");
        builder.append("MKBUtils.putExtraViaByteArray(outState,").append( "\"" + PREFIX + fieldName +"\", target. " + fieldName).append(");");
        builder.append("}\n");


        return builder.toString();
    }

    public static void main(String[] args) {
        System.out.println(new KeepFieldInjector("datas", "ArrayList<String>").brewOnCreateJava());
        System.out.println(new KeepFieldInjector("datas", "ArrayList<String>").brewGetStartIntent());
    }

}
