package com.github.bluzwong.monkeykingbar_processor;


/**
 * Created by wangzhijie@wind-mobi.com on 2015/9/24.
 */
public class InjectFieldInjector {


    private String fieldName, fieldType;
    public static final String PREFIX = "MKB_";
    private String className = "";

    private boolean asProperty = false;
    public boolean isAsProperty() {
        return asProperty;
    }
    public InjectFieldInjector(String fieldName, String fieldType, boolean asProperty) {
        this.fieldName = fieldName;
        this.fieldType = fieldType;
        this.asProperty = asProperty;
    }

    public void setClassName(String fieldKey) {
        this.className = Utils.getMD5(fieldKey + "." + fieldName+": " + fieldType) + "@";
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public String brewInjectExtrasJava(int index) {
        StringBuilder builder = new StringBuilder();
        builder.append(" obj = MKBUtils.getExtra(intent, \"" + className + "\" +uuid);\n");

        builder.append(" if (obj != null) { ");

        if (asProperty) {
            builder.append("    target.set" + captureName(fieldName) + "((" + fieldType + ") obj);\n");
        } else {
            builder.append("    target." + fieldName + " = (" + fieldType + ") obj;\n");
        }
        builder.append(" }");
        return builder.toString();
    }

    public static String captureName(String name) {
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        return  name;

    }
    public String brewPutExtra() {
        StringBuilder builder = new StringBuilder();
        builder.append("MKBUtils.removeKeyIfNotUuid(\"" + className +"\", uuid);\n");
        builder.append("MKBUtils.putExtra(intent,\"" + className + "\"+uuid, " + fieldName + ");\n");
        return builder.toString();
    }
    public String brewDestroy() {
        return "MKBUtils.removeCache(\"" + className +");\n";
    }
    public static void main(String[] args) {
        //System.out.println(new InjectFieldInjector("ccf", "String").brewInjectExtrasJava());
    }

}
