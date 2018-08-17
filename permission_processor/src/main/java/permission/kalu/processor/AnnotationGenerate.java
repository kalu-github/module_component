package permission.kalu.processor;

import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * description: Java代码自动生成
 * created by kalu on 2017/12/16 15:39
 */
final class AnnotationGenerate {

    private final String NAME = "$$OnAnnotationChangeListener";

    private final String packageName;
    private final TypeElement element;
    private final String proxyName;

    final AnnotationModel[] succ = new AnnotationModel[1];
    final AnnotationModel[] fail = new AnnotationModel[1];
    final AnnotationModel[] again = new AnnotationModel[1];
    final AnnotationModel[] denied = new AnnotationModel[1];

    AnnotationGenerate(Elements elements, TypeElement typeElement) {
        this.element = typeElement;
        packageName = elements.getPackageOf(typeElement).getQualifiedName().toString();
        String className = getClassName(typeElement, packageName);
        proxyName = className + NAME;
    }

    String getProxyName() {
        return proxyName;
    }

    TypeElement getElement() {
        return element;
    }

    String generateClassName() {

        final StringBuilder builder = new StringBuilder();
        builder.append("package ").append(packageName).append(";\n\n")
                .append("import permission.kalu.core.PermissionManager;\n")
                .append("import permission.kalu.core.listener.OnAnnotationChangeListener;\n")
                .append("import java.util.List;\n")
                .append("import android.content.Intent;\n\n")
                .append("public class ").append(proxyName).append(" implements ").append
                ("OnAnnotationChangeListener").append
                ("<").append(element.getSimpleName()).append("> {\n\n");

        // 申请权限成功回调
        succMethod(builder);
        // 申请权限失败回调
        failMethod(builder);
        // 第二次提醒, 用户没有点击拒绝提示, 系统默认提示框
        againMethod(builder);
        // 第二次提醒, 用户已经点击拒绝提示, 默认调转权限设置页面, 国产机型, 需要做适配
        deniedMethod(builder);

        builder.append("}");
        return builder.toString();
    }

    private final void succMethod(StringBuilder builder) {

        if (null == builder) return;

        final AnnotationModel model = succ[0];
        if (null == model) return;

        builder.append("@Override\n").append("public void onSucc(").append(element.getSimpleName())
                .append(" object, int requestCode, List<String> list) {\n");

        final String method = model.getMethod();
        final int code1 = model.getCode();
        builder.append("object.").append(method).append("(").append(code1).append(", list);\n");

        builder.append("}\n");
    }

    private final void failMethod(StringBuilder builder) {

        if (null == builder) return;

        final AnnotationModel model = fail[0];
        if (null == model) return;

        builder.append("@Override\n").append("public void onFail(").append(element.getSimpleName())
                .append(" object, int requestCode, List<String> list) {\n");

        final String method = model.getMethod();
        final int code1 = model.getCode();
        builder.append("object.").append(method).append("(").append(code1).append(", list);\n");

        builder.append("}\n");
    }

    private final void againMethod(StringBuilder builder) {

        if (null == builder) return;

        final AnnotationModel model = again[0];
        if (null == model) return;

        builder.append("@Override\n").append("public void onAgain(").append(element.getSimpleName())
                .append(" object, int requestCode, List<String> list) {\n");

        final String method = model.getMethod();
        final int code1 = model.getCode();
        builder.append("object.").append(method).append("(").append(code1).append(", list);\n");

        builder.append("}\n");
    }

    private final void deniedMethod(StringBuilder builder) {

        if (null == builder) return;

        final AnnotationModel model = denied[0];
        if (null == model) return;

        builder.append("@Override\n").append("public void onDenied(").append(element.getSimpleName())
                .append(" object, int requestCode, List<String> list, Intent intent) {\n");

        final String method = model.getMethod();
        final int code1 = model.getCode();
        builder.append("object.").append(method).append("(").append(code1).append(", list").append(", intent);\n");

        builder.append("}\n");
    }

    private String getClassName(TypeElement element, String packageName) {
        int packageLen = packageName.length() + 1;
        return element.getQualifiedName().toString().substring(packageLen)
                .replace('.', '$');
    }
}
