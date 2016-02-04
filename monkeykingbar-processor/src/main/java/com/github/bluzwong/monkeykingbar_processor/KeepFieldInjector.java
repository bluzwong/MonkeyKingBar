package com.github.bluzwong.monkeykingbar_processor;


/**
 * Created by wangzhijie@wind-mobi.com on 2015/9/24.
 */
public class KeepFieldInjector {


    private String fieldName, fieldType;

    public static final String PREFIX = "MKB_";
    private boolean unSerializable = false;
    public boolean isUnSerializable() {
        return unSerializable;
    }

    public KeepFieldInjector(String fieldName, String fieldType, boolean unSerializable) {
        this.fieldName = fieldName;
        this.fieldType = fieldType;
        this.unSerializable = unSerializable;
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
    public String brewOnCreateJava(int index) {
        StringBuilder builder = new StringBuilder();

        if (isUnSerializable()) {
            builder.append("if (objs != null && objs.length > " + index +" && MKBUtils.maps.containsKey(objs["+index+"])) {\n");
            builder.append("generalObj = MKBUtils.maps.get(objs["+index+"]);\n");
            builder.append("if (generalObj != null) {\n");
            builder.append("target." + fieldName + " = (" + fieldType + ") generalObj;\n");
            builder.append("MKBUtils.maps.remove(objs["+index+"]);\n");
            builder.append("}\n}\n");

        } else {
            builder.append(" obj = MKBUtils.getExtra(savedInstanceState, \"" + className + "\" +uuid);\n");
            builder.append(" if (obj != null) { ");
            builder.append("    target." + fieldName + " = (" + fieldType + ") obj;\n");
            builder.append(" }");

        }
        return builder.toString();
    }

    public String brewSaveState() {
        StringBuilder builder = new StringBuilder();

        if (isUnSerializable()) {
            String key = fieldName + "_key";
            builder.append("String " + key + " = UUID.randomUUID().toString();\n");
            builder.append("MKBUtils.maps.put(" + key +", target." + fieldName + ");\n");
        } else {
            //builder.append("MKBUtils.removeKeyIfNotUuid(\"" + className +"\", uuid);\n");
            builder.append("MKBUtils.putExtra(outState,").append("\"" + className + "\"+uuid, target. " + fieldName).append(");\n");
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

}
