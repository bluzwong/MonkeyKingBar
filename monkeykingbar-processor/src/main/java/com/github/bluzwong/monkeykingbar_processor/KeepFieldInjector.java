package com.github.bluzwong.monkeykingbar_processor;


/**
 * Created by wangzhijie@wind-mobi.com on 2015/9/24.
 */
public class KeepFieldInjector {


    private String fieldName, fieldType;

    public static final String PREFIX = "MKB_";
    private boolean asProperty = false;
    public boolean isAsProperty() {
        return asProperty;
    }

    public KeepFieldInjector(String fieldName, String fieldType, boolean asProperty) {
        this.fieldName = fieldName;
        this.fieldType = fieldType;
        this.asProperty = asProperty;
    }
    private String className;
    public void setClassName(String fieldKey) {
        this.className = Utils.getMD5(fieldKey + "." + fieldName+": " + fieldType) + "@";
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }
    public String brewOnCreateJava() {
        StringBuilder builder = new StringBuilder();
        builder.append(" obj = MKBUtils.getExtra(savedInstanceState, \"" + className + "\" +uuid);\n");
        builder.append(" if (obj != null) { ");
        if (isAsProperty()) {

            builder.append("    target.set" + captureName(fieldName) + "((" + fieldType + ") obj);\n");
        } else {
            builder.append("    target." + fieldName + " = (" + fieldType + ") obj;\n");
        }
        builder.append(" }");
        return builder.toString();
    }

    public String brewOnCreateJavaFragment() {
        StringBuilder builder = new StringBuilder();
        builder.append(" obj = keepStateFragment.get(\"" + className + "\" +uuid);\n");
        builder.append(" if (obj != null) { ");
        if (isAsProperty()) {

            builder.append("    target.set" + captureName(fieldName) + "((" + fieldType + ") obj);\n");
        } else {
            builder.append("    target." + fieldName + " = (" + fieldType + ") obj;\n");
        }
        builder.append(" }");
        return builder.toString();
    }

    public String brewSaveState() {
        StringBuilder builder = new StringBuilder();

        if (isAsProperty()) {
            builder.append("MKBUtils.putExtra(outState,").append("\"" + className + "\"+uuid, target.get" + captureName(fieldName)).append("());\n");
        } else {
            //builder.append("MKBUtils.removeKeyIfNotUuid(\"" + className +"\", uuid);\n");
            builder.append("MKBUtils.putExtra(outState,").append("\"" + className + "\"+uuid, target. " + fieldName).append(");\n");
        }
        return builder.toString();
    }

    public String brewSaveStateFragment() {
        StringBuilder builder = new StringBuilder();

        if (isAsProperty()) {
            builder.append("keepStateFragment.put(").append("\"" + className + "\"+uuid, target.get" + captureName(fieldName)).append("());\n");
        } else {
            //builder.append("MKBUtils.removeKeyIfNotUuid(\"" + className +"\", uuid);\n");
            builder.append("keepStateFragment.put(").append("\"" + className + "\"+uuid, target. " + fieldName).append(");\n");
        }
        return builder.toString();
    }

    public String brewDestroy() {
        return "MKBUtils.removeCache(\"" + className +");\n";
    }

    public static void main(String[] args) {
        //System.out.println(new KeepFieldInjector("datas", "ArrayList<String>").brewOnCreateJava());
        //System.out.println(new KeepFieldInjector("datas", "ArrayList<String>").brewSaveState());
    }
    public static String captureName(String name) {
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        return  name;

    }
}
