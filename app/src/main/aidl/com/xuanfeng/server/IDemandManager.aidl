// IDemandManager.aidl
package com.xuanfeng.server;
import com.xuanfeng.server.MessageBean;
// Declare any non-default types here with import statements
//接口里写服务器、客户端交互的方法
interface IDemandManager {
   MessageBean getDemand();

   void setDemandIn(in MessageBean msg);//客户端->服务端

   //out和inout都需要重写MessageBean的readFromParcel方法
   void setDemandOut(out MessageBean msg);//服务端->客户端

   void setDemandInOut(inout MessageBean msg);//客户端<->服务端
}
