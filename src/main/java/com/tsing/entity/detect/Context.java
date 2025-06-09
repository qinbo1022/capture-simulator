/**
  * Copyright 2023 json.cn 
  */
package com.tsing.entity.detect;

/**
 * Auto-generated: 2023-07-25 13:31:45
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
public class Context {

    private String SessionId;
    private String Status;
    private String Message;
    private RequestTs RequestTs;
    private ResponseTs ResponseTs;
    public void setSessionId(String SessionId) {
         this.SessionId = SessionId;
     }
     public String getSessionId() {
         return SessionId;
     }

    public void setStatus(String Status) {
         this.Status = Status;
     }
     public String getStatus() {
         return Status;
     }

    public void setMessage(String Message) {
         this.Message = Message;
     }
     public String getMessage() {
         return Message;
     }

    public void setRequestTs(RequestTs RequestTs) {
         this.RequestTs = RequestTs;
     }
     public RequestTs getRequestTs() {
         return RequestTs;
     }

    public void setResponseTs(ResponseTs ResponseTs) {
         this.ResponseTs = ResponseTs;
     }
     public ResponseTs getResponseTs() {
         return ResponseTs;
     }

}