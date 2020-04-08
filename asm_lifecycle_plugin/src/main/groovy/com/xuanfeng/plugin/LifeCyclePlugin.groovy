package com.xuanfeng.plugin

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * gradle自定义插件
 */
public class LifeCyclePlugin implements Plugin<Project> {


    @Override
    void apply(Project project) {
        System.out.println("======LifeCyclePlugin gradle plugin===")

        def android = project.extensions.getByType(AppExtension)
        println '--------------registering AutoTrackTransform------------'
        LifeCycleTransform transform = new LifeCycleTransform()
        android.registerTransform(transform)
    }
}