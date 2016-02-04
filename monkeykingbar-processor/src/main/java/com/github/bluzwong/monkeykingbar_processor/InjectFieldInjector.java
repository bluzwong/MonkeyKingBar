package com.github.bluzwong.monkeykingbar_processor;


/**
 * Created by wangzhijie@wind-mobi.com on 2015/9/24.
 */
public class InjectFieldInjector {


    private String fieldName, fieldType;
    public static final String PREFIX = "MKB_";
    private String className = "";

    private boolean unSerializable = false;
    public boolean isUnSerializable() {
        return unSerializable;
    }
    public InjectFieldInjector(String fieldName, String fieldType, boolean unSerializable) {
        this.fieldName = fieldName;
        this.fieldType = fieldType;
        this.unSerializable = unSerializable;
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
        if (unSerializable) {

            builder.append("if (objs != null && objs.length > " + index +" && MKBUtils.maps.containsKey(objs["+index+"])) {\n");
            builder.append("generalObj = MKBUtils.maps.get(objs["+index+"]);\n");
            builder.append("if (generalObj != null) {\n");
            builder.append("target." + fieldName + " = (" + fieldType + ") generalObj;\n");
            builder.append("MKBUtils.maps.remove(objs["+index+"]);\n");
            builder.append("}\n}\n");


        } else {

            builder.append(" obj = MKBUtils.getExtra(intent, \"" + className + "\" +uuid);\n");

            builder.append(" if (obj != null) { ");
            builder.append("    target." + fieldName + " = (" + fieldType + ") obj;\n");
            builder.append(" }");
        }

        return builder.toString();
    }


    public String brewPutExtra() {
        StringBuilder builder = new StringBuilder();

        if (unSerializable) {
            String key = fieldName + "_key";
            builder.append("String " + key + " = UUID.randomUUID().toString();\n");
            builder.append("MKBUtils.maps.put(" + key +", " + fieldName + ");");
        } else {
            //builder.append("MKBUtils.removeKeyIfNotUuid(\"" + className +"\", uuid);\n");
            builder.append("MKBUtils.putExtra(intent,\"" + className + "\"+uuid, " + fieldName + ");\n");
        }

        return builder.toString();
    }
    public String brewDestroy() {
        return "MKBUtils.removeCache(\"" + className +");\n";
    }
    public static void main(String[] args) {
        //System.out.println(new InjectFieldInjector("ccf", "String").brewInjectExtrasJava());
    }

}
