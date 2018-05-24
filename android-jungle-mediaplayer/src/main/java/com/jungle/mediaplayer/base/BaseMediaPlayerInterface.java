/**
 * Android Jungle-MediaPlayer framework project.
 *
 * Copyright 2016 Arno Zhang <zyfgood12@163.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jungle.mediaplayer.base;

public interface BaseMediaPlayerInterface {

    /**
     * 暂停
     */
    void pause();

    /**
     * 恢复播放
     */
    void resume();

    /**
     * 停止播放
     */
    void stop();

    /**
     * 销毁播放器，释放系统资源
     */
    void destroy();

    /**
     * 跳转到某个进度播放
     * @param millSeconds
     */
    void seekTo(int millSeconds);

    /**
     * 调节音量
     * @param volume
     */
    void setVolume(float volume);

    /**
     * 获取整个视频的时间，返回ms
     * @return
     */
    int getDuration();

    /**
     * 获取视频当前播放的进度，返回ms
     * @return
     */
    int getCurrentPosition();

    /**
     * 获取视频缓冲百分比
     * @return
     */
    int getBufferPercent();

    /**
     * 是否正在播放
     * @return
     */
    boolean isPlaying();

    /**
     * 是否正在加载视频
     * @return
     */
    boolean isPaused();

    /**
     * 是否正在加载视频
     * @return
     */
    boolean isLoading();

    /**
     *
     * @return
     */
    boolean isLoadingFailed();

    /**
     * 是否播放完成
     * @return
     */
    boolean isPlayCompleted();
}
