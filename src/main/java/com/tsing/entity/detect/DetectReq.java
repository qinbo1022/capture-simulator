/**
  * Copyright 2023 json.cn 
  */
package com.tsing.entity.detect;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * Auto-generated: 2023-07-25 13:33:59
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DetectReq {

    private ImageReq image;
    private int ImageDownloadTimeout;
    //private JSONArray rois_polygon;
    private List<Interested_objects> interested_objects;

//    public void setImage(ImageReq image) {
//         this.image = image;
//     }
//     public ImageReq getImage() {
//         return image;
//     }
//    public void  getSetroisPolygin(JSONArray rois_polygin){
//        this.rois_polygin = rois_polygin;
//    }
//
//    public void setRoisPolygin(JSONArray rois_polygin){
//        this.rois_polygin = rois_polygin;
//    }
//
////    public String  getrois_polygin(){
////        return rois_polygin;
////    }
//    public void setImageDownloadTimeout(int ImageDownloadTimeout) {
//         this.ImageDownloadTimeout = ImageDownloadTimeout;
//     }
//     public int getImageDownloadTimeout() {
//         return ImageDownloadTimeout;
//     }
//
//    public void setInterested_objects(List<Interested_objects> interested_objects) {
//         this.interested_objects = interested_objects;
//     }
//     public List<Interested_objects> getInterested_objects() {
//         return interested_objects;
//     }

}