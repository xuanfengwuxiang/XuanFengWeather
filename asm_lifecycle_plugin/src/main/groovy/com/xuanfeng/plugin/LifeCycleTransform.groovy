package com.xuanfeng.plugin

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import groovy.io.FileType

/**
 * 自定义task
 *
 * 将编译后的类文件转换为 dex 文件之前对其进行操作
 */
public class LifeCycleTransform extends Transform {


    //自定义的 Transform 名称为 LifeCycleTransform
    @Override
    String getName() {
        return "LifeCycleTransform"
    }

    //检索项目中 .class 类型的目录或者文件
    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    //设置当前 Transform 检索范围为当前项目
    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.PROJECT_ONLY
    }

    //是否支持增量编译
    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    void transform(Context context, Collection<TransformInput> inputs, Collection<TransformInput> referencedInputs, TransformOutputProvider outputProvider, boolean isIncremental) throws IOException, TransformException, InterruptedException {
        super.transform(context, inputs, referencedInputs, outputProvider, isIncremental)
    }

    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {

        //拿到所有class文件
        Collection<TransformInput> transformInputs = transformInvocation.inputs

        transformInputs.each {

            TransformInput transformInput ->
                transformInput.directoryInputs.each {

                    DirectoryInput directoryInput ->
                        File dir = directoryInput.file
                        if (dir) {
                            dir.traverse(type: FileType.FILES, nameFilter: ~/.*\.class/) {
                                File file ->
                                    System.out.println("find class=====" + file.name)
                            }
                        }
                }
        }
    }
}
