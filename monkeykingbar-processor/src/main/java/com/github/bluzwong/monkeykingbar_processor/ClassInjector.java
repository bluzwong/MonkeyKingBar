package com.github.bluzwong.monkeykingbar_processor;

import java.util.*;

/**
 * Created by geminiwen on 15/5/21.
 */
public class ClassInjector {

    private final String classPackage;
    private final String className;
    private final String originClassName;
    private final Set<InjectFieldInjector> injectFields;
    private final Set<KeepFieldInjector> keepFields;
    private boolean needInject = false, needKeep = false;
    private static final String SUFFIX = "_MKB";
    private String unserUUID = UUID.randomUUID().toString();

    public ClassInjector(String classPackage, String className) {
        // com.github.bluzwong.mycache
        this.classPackage = classPackage;
        // MainActivity
        this.originClassName = className;
        // MainActivity_MKB
        this.className = className + SUFFIX;

        this.injectFields = new LinkedHashSet<InjectFieldInjector>();
        this.keepFields = new LinkedHashSet<KeepFieldInjector>();
    }

    public void addInjectField(InjectFieldInjector e) {
        injectFields.add(e);
    }

    public void addKeepField(KeepFieldInjector e) {
        keepFields.add(e);
    }

    public String getFqcn() {
        return classPackage + "." + className;
    }


    public String brewJava() {
        needInject = injectFields.size() > 0;
        needKeep = keepFields.size() > 0;

        // package and imports
        StringBuilder builder = new StringBuilder("package " + this.classPackage + ";\n");
        // import com.github.bluzwong.monkeykingbar_lib.*;
        builder.append("import com.github.bluzwong.monkeykingbar_lib.*;\n");
        /*
        import android.app.Activity;
        import android.content.Intent;
        import android.os.Bundle;
        */
        builder.append("import android.app.Activity;\n");
        builder.append("import android.content.Intent;\n");
        builder.append("import android.os.Bundle;\n");
        builder.append("import java.util.UUID;\n");
        // class and implements
        builder.append("public class ").append(this.className);
        if (needInject || needKeep) {
            builder.append(" implements ");
        }

        if (needInject) {
            builder.append(" Inject ");
        }
        if (needKeep) {
            if (needInject) {
                builder.append(" , ");
            }
            builder.append("Keep");
        }

        // start class
        builder.append(" {\n");

        // static fields


        // injectFields
        if (needInject) {
            int index = 0;
            builder.append("@Override                                                                                   \n");
            builder.append("    public void injectExtras(Activity activity) {                                   \n");
            builder.append("        if (activity == null) {\n");
            builder.append("            throw new IllegalStateException();                              \n");
            builder.append("        }                                                               \n");
            builder.append(originClassName).append(" target = ( ").append(originClassName).append(" ) activity;\n");
            builder.append("Object obj = null;\n");
            builder.append("Intent intent = activity.getIntent();\n");

            if (isInjectHasUnSerial()) {
                builder.append("obj = MKBUtils.getExtra(intent, \"MKB@" + unserUUID +"\");\n");
                builder.append(" String[] objs = (String[]) obj; \n");
                builder.append("Object generalObj;\n");
            }

            for (InjectFieldInjector field : injectFields) {
                builder.append(field.brewInjectExtrasJava(index));
                if (field.isUnSerializable()) {
                    index++;
                }
            }
            builder.append("}\n");


            /*
            public static Intent getStartIntent(Activity context, int ccf_base, java.lang.String string_base) {
        Intent intent = new Intent(context, BaseActivity.class);
        putExtra(intent, ccf_base, string_base);
        return intent;
    }
            */

            builder.append("public static Intent getStartIntent(Activity context");
            builder.append(getInjectTypeAndParams());
            builder.append(") {\n");

            builder.append("Intent intent = new Intent(context, ").append(originClassName).append(".class);\n");
            builder.append("putExtra(intent").append(getInjectParams()).append(");\n");

            builder.append("return intent;\n");
            builder.append("}\n");


            /*
            public static Intent putExtra(Intent intent, int ccf_base, java.lang.String string_base) {
                MKBUtils.putExtra(intent, "MKB_ccf_base", ccf_base);
                MKBUtils.putExtra(intent, "MKB_string_base", string_base);
                return intent;
            }
            */

            builder.append("public static Intent putExtra(Intent intent").append(getInjectTypeAndParams()).append(") {\n");
            for (InjectFieldInjector field : injectFields) {
                builder.append(field.brewPutExtra());
            }

            if (isInjectHasUnSerial()) {
                builder.append("MKBUtils.putExtra(intent, \"MKB@"+unserUUID+"\", new String[] {");

                boolean init = true;
                for (InjectFieldInjector injectField : injectFields) {
                    if (injectField.isUnSerializable()) {
                        if (!init) {
                            builder.append(", ");
                        }
                        init = false;
                        String key = injectField.getFieldName() + "_key";
                        builder.append(key);
                    }
                }

                builder.append("} );\n");
            }

            builder.append("return intent;}\n");

            /*
            public static void startActivity(Activity activity, int ccf) {
                activity.startActivity(getStartIntent(activity, ccf));
            }
            */

            builder.append("public static void startActivity(Activity activity");
            builder.append(getInjectTypeAndParams());
            builder.append(") {\n");

            builder.append("activity.startActivity(getStartIntent(activity");
            builder.append(getInjectParams());
            builder.append("));");
            builder.append("}\n");
        }

        /*
        if (activity == null) {
            throw new IllegalStateException();
        }
        if (savedInstanceState == null) {
            return;
        }

        MainActivity target = (MainActivity) activity;
        Object obj;

        obj = MKBUtils.getExtra(savedInstanceState, "datas");
        if (obj != null) {
            target.datas = (ArrayList<String>) obj;
        }

        */


        if (needKeep) {
            builder.append("\n");

            builder.append("@Override\n");
            builder.append("public void onCreate(Object object, Bundle savedInstanceState) {\n");

            builder.append("if (object == null) {\n");
            builder.append("    throw new IllegalStateException();\n");
            builder.append("}\n");
            builder.append("if (savedInstanceState == null) {\n");
            builder.append("    return;\n");
            builder.append("}\n");

            builder.append(originClassName).append(" target = (").append(originClassName).append(") object;\n");
            builder.append("Object obj;\n");

            if (isKeepHasUnSerial()) {
                builder.append("obj = MKBUtils.getExtra(savedInstanceState, \"MKB@"+unserUUID+"\");");
                builder.append("String[] objs = (String[]) obj;\n");
                builder.append("Object generalObj;\n");

            }
            int index = 0;
            for (KeepFieldInjector field : keepFields) {
                builder.append(field.brewOnCreateJava(index));
                if (field.isUnSerializable()) {
                    index++;
                }
            }

            builder.append("}\n");


            /*
            @Override
            public void onSaveInstanceState(Activity activity, Bundle outState) {
                if (activity == null) {
                    throw new IllegalStateException();
                }
                MainActivity target = (MainActivity) activity;

                MKBUtils.putExtra(outState, "datas", target.datas);

            }
            */
            builder.append(" \n");

            builder.append("@Override \n");
            builder.append("public void onSaveInstanceState(Object object, Bundle outState) { \n");
            builder.append(" if (object == null) {\n");
            builder.append("    throw new IllegalStateException();\n");
            builder.append("} \n");
            builder.append(originClassName).append(" target = (").append(originClassName).append(") object;\n");

            for (KeepFieldInjector field : keepFields) {
                builder.append(field.brewSaveState());
            }

            if (isKeepHasUnSerial()) {
                builder.append("MKBUtils.putExtra(outState, \"MKB@"+unserUUID+"\", new String[] {");

                boolean init = true;
                for (KeepFieldInjector keepInject : keepFields) {
                    if (keepInject.isUnSerializable()) {
                        if (!init) {
                            builder.append(", ");
                        }
                        init = false;
                        String key = keepInject.getFieldName() + "_key";
                        builder.append(key);
                    }
                }

                builder.append("} );\n");
            }
            builder.append("} \n");
        }

        // destroy method start
        /*
        builder.append("@Override\n")
                .append("public void onDestroy() {\n");

        for (InjectFieldInjector injectField : injectFields) {
            builder.append(injectField.brewDestroy());
        }

        for (KeepFieldInjector keepField : keepFields) {
            builder.append(keepField.brewDestroy());
        }
        builder.append("}\n");*/
        // destroy method end
        // end of class
        builder.append("}\n"); // end of class
        return builder.toString();
    }

    private boolean isInjectHasUnSerial() {
        for (InjectFieldInjector injector : injectFields) {
            if (injector.isUnSerializable()) {
                return true;
            }
        }
        return false;
    }

    private boolean isKeepHasUnSerial() {
        for (KeepFieldInjector injector : keepFields) {
            if (injector.isUnSerializable()) {
                return true;
            }
        }
        return false;
    }

    private String getInjectParams() {
        StringBuilder builder = new StringBuilder();
        for (InjectFieldInjector field : injectFields) {
            builder.append(", ").append(field.getFieldName());
        }
        return builder.toString();
    }
    private String getInjectTypeAndParams() {
        StringBuilder builder = new StringBuilder();
        for (InjectFieldInjector field : injectFields) {
            builder.append(", ").append(field.getFieldType()).append(" ").append(field.getFieldName());
        }
        return builder.toString();
    }

    public static void main(String[] args) throws NoSuchMethodException {
        /*ClassInjector classInjector = new ClassInjector("com.github.bluzwong.mycache", "MainActivity");
        classInjector.addInjectField(new InjectFieldInjector("ccf", "String"));
        System.out.println(classInjector.brewJava());
        int[] ints = {1};
        System.out.println(ints.getClass().getCanonicalName());
        System.out.println(ints.getClass().getTypeName());

        System.out.println("String.class.isAssignableFrom(Object.class) => " + String.class.isAssignableFrom(Object.class));
        System.out.println("Object.class.isAssignableFrom(String.class) => " + Object.class.isAssignableFrom(String.class));

        List<String> testList = new ArrayList<>();
        testList.add("ccf");
        Object obj = testList;


        if (obj instanceof ArrayList) {
            if (((ArrayList) obj).size()>0) {
                Object o = ((ArrayList) obj).get(0);
                System.out.println("simple name => " + o.getClass().getSimpleName());
                if (o instanceof String) {
                    System.out.println(" o is string");
                }
            }
        }*/

    }
}
