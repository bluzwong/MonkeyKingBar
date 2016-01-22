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
    private final List<String> fieldBool = new ArrayList<String>();
    private boolean needInject = false, needKeep = false;
    private static final String SUFFIX = "_MKB";

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

        for (InjectFieldInjector field : injectFields) {
            if (!fieldBool.contains(field.getFieldName())) {
                builder.append("private static boolean ").append(field.getFieldName()).append("_defaultType").append(";\n");
                fieldBool.add(field.getFieldName());
            }
        }

        for (KeepFieldInjector field : keepFields) {
            if (!fieldBool.contains(field.getFieldName())) {
                builder.append("private static boolean ").append(field.getFieldName()).append("_defaultType").append(";\n");
                fieldBool.add(field.getFieldName());
            }
        }
        // injectFields
        if (needInject) {
            builder.append("@Override                                                                                   \n");
            builder.append("    public void injectExtras(Activity activity) {                                   \n");
            builder.append("        if (activity == null) {\n");
            builder.append("            throw new IllegalStateException();                              \n");
            builder.append("        }                                                               \n");
            builder.append(originClassName).append(" target = ( ").append(originClassName).append(" ) activity;\n");
            builder.append("Object obj = null;\n");
            builder.append("Intent intent = activity.getIntent();\n");
            for (InjectFieldInjector field : injectFields) {
                builder.append(field.brewInjectExtrasJava());
            }
            builder.append("}\n");


            /*
            public static Intent getStartIntent(Activity context, int ccf) {
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("ccf", ccf);
                return intent;
            }
            */

            builder.append("public static Intent getStartIntent(Activity context");
            builder.append(getInjectTypeAndParams());
            builder.append(") {\n");

            builder.append("Intent intent = new Intent(context, ").append(originClassName).append(".class);\n");
            for (InjectFieldInjector field : injectFields) {
                builder.append(field.brewGetStartIntent());
            }
            builder.append("return intent;\n");
            builder.append("}\n");

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
            builder.append("public void onCreate(Activity activity, Bundle savedInstanceState) {\n");

            builder.append("if (activity == null) {\n");
            builder.append("    throw new IllegalStateException();\n");
            builder.append("}\n");
            builder.append("if (savedInstanceState == null) {\n");
            builder.append("    return;\n");
            builder.append("}\n");

            builder.append(originClassName).append(" target = (").append(originClassName).append(") activity;\n");
            builder.append("Object obj;\n");

            for (KeepFieldInjector field : keepFields) {
                builder.append(field.brewOnCreateJava());
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
            builder.append("public void onSaveInstanceState(Activity activity, Bundle outState) { \n");
            builder.append(" if (activity == null) {\n");
            builder.append("    throw new IllegalStateException();\n");
            builder.append("} \n");
            builder.append(originClassName).append(" target = (").append(originClassName).append(") activity;\n");

            for (KeepFieldInjector field : keepFields) {
                builder.append(field.brewGetStartIntent());
            }

            builder.append("} \n");
        }

        // end of class
        builder.append("}\n"); // end of class
        return builder.toString();
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

    public static void main(String[] args) {
        ClassInjector classInjector = new ClassInjector("com.github.bluzwong.mycache", "MainActivity");
        classInjector.addInjectField(new InjectFieldInjector("ccf", "String"));
        System.out.println(classInjector.brewJava());
        int[] ints = {1};
        System.out.println(ints.getClass().getCanonicalName());
        System.out.println(ints.getClass().getTypeName());
    }
}
