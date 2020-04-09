package com.xuanfeng.plugin

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * gradle自定义插件
 */
class LifeCyclePlugin implements Plugin<Project> {


    @Override
    void apply(Project project) {

        def android = project.extensions.getByType(AppExtension)
        LifeCycleTransform transform = new LifeCycleTransform()
        android.registerTransform(transform)
        println '--------------registered LifeCycleTransform------------'
    }
}