package com.syan.agora;

import android.view.SurfaceView;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;

import io.agora.rtc.RtcEngine;
import io.agora.rtc.video.VideoCanvas;

/**
 * Created by DB on 2017/6/23.
 */

public class AgoraViewManager extends SimpleViewManager<AgoraVideoView> {

    public static final String REACT_CLASS = "RCTAgoraVideoView";

    ReactApplicationContext mCallerContext;

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    public AgoraViewManager(ReactApplicationContext reactContext) {
        mCallerContext = reactContext;
    }
    @Override
    protected AgoraVideoView createViewInstance(ThemedReactContext reactContext) {
        return new AgoraVideoView(reactContext);
    }

    @ReactProp(name = "mode")
    public void setRenderMode(final AgoraVideoView agoraVideoView, Integer renderMode) {
        agoraVideoView.setRenderMode(renderMode);
    }

    @ReactProp(name = "showLocalVideo")
    public void setShowLocalVideo(final AgoraVideoView agoraVideoView, boolean showLocalVideo) {
        agoraVideoView.setShowLocalVideo(showLocalVideo);
        if (showLocalVideo) {
            SurfaceView surfaceView=RtcEngine.CreateRendererView(mCallerContext);
            VideoCanvas canvas=new VideoCanvas(surfaceView, agoraVideoView.getRenderMode(), AgoraManager.getInstance().mLocalUid);
            agoraVideoView.canvas=canvas;
            agoraVideoView.addView(surfaceView);
            AgoraManager.getInstance().setupLocalVideo(canvas);
            canvas.view.setZOrderMediaOverlay(agoraVideoView.getZOrderMediaOverlay());
        }
    }

    @ReactProp(name = "zOrderMediaOverlay")
    public void setZOrderMediaOverlay(final AgoraVideoView agoraVideoView, boolean zOrderMediaOverlay) {
        agoraVideoView.setZOrderMediaOverlay(zOrderMediaOverlay);
        VideoCanvas canvas=agoraVideoView.canvas;
        if(canvas!=null)
        {
            canvas.view.setZOrderMediaOverlay(zOrderMediaOverlay);
        }
    }

    @ReactProp(name = "remoteUid")
    public void setRemoteUid(final AgoraVideoView agoraVideoView, final int remoteUid) {
        agoraVideoView.setRemoteUid(remoteUid);
        if (remoteUid != 0) {
            SurfaceView surfaceView=RtcEngine.CreateRendererView(mCallerContext);
            VideoCanvas canvas=new VideoCanvas(surfaceView, agoraVideoView.getRenderMode(), remoteUid);
            agoraVideoView.canvas=canvas;
            agoraVideoView.addView(surfaceView);
            AgoraManager.getInstance().setupRemoteVideo(canvas);
            canvas.view.setZOrderMediaOverlay(agoraVideoView.getZOrderMediaOverlay());
        }
    }

}
