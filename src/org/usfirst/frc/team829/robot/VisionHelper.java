package org.usfirst.frc.team829.robot;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ImageType;

import edu.wpi.first.wpilibj.CameraServer;

public class VisionHelper {

	private boolean uploadingToServer = false;
	
	private int session;
	private int imaqError;
	private Image frame;
	private Image binaryFrame;
	
	private NIVision.Range HUE_RANGE;
	private NIVision.Range SAT_RANGE;
	private NIVision.Range VAL_RANGE;
	private double PARTICLE_AREA_MIN;
	
	private NIVision.ParticleFilterCriteria2 criteria[] = new NIVision.ParticleFilterCriteria2[1];
	NIVision.ParticleFilterOptions2 filterOptions = new NIVision.ParticleFilterOptions2(0,0,1,1);
	
	public VisionHelper(){
		session = NIVision.IMAQdxOpenCamera("cam0",
                NIVision.IMAQdxCameraControlMode.CameraControlModeController);
        NIVision.IMAQdxConfigureGrab(session);
		frame = NIVision.imaqCreateImage(ImageType.IMAGE_RGB, 0);
		binaryFrame = NIVision.imaqCreateImage(ImageType.IMAGE_U8, 0);
		criteria[0] = new NIVision.ParticleFilterCriteria2(NIVision.MeasurementType.MT_AREA_BY_IMAGE_AREA, PARTICLE_AREA_MIN, 100.0, 0, 0);
	}
	
	public VisionHelper(String cameraName){
		session = NIVision.IMAQdxOpenCamera(cameraName,
                NIVision.IMAQdxCameraControlMode.CameraControlModeController);
        NIVision.IMAQdxConfigureGrab(session);
		frame = NIVision.imaqCreateImage(ImageType.IMAGE_RGB, 0);
		binaryFrame = NIVision.imaqCreateImage(ImageType.IMAGE_U8, 0);
		criteria[0] = new NIVision.ParticleFilterCriteria2(NIVision.MeasurementType.MT_AREA_BY_IMAGE_AREA, PARTICLE_AREA_MIN, 100.0, 0, 0);
	}
	
	public boolean startAquisition(){
		try{
			NIVision.IMAQdxStartAcquisition(session);
		}
		catch(Exception e){
			System.out.println(e);
			return false;
			}
		return true;
	}
	
	public Image createBinaryImage(){
    	NIVision.IMAQdxGrab(session, frame, 1);		// grab the current frame on the camera
    	
    	//convert the current frame to a binary image and store it to binaryImage using the HSV thresholds
    	NIVision.imaqColorThreshold(binaryFrame, frame, 255, NIVision.ColorMode.HSV, HUE_RANGE, SAT_RANGE, VAL_RANGE);
    	
    	criteria[0].lower = (float)PARTICLE_AREA_MIN;
		//imaqError = NIVision.imaqParticleFilter4(binaryFrame, binaryFrame, criteria, filterOptions, null);
    	if(uploadingToServer)
    		CameraServer.getInstance().setImage(binaryFrame);
    	
    	return binaryFrame;
	}
	
	public void setUploadingToServer(boolean state){
		uploadingToServer = state;
	}
	
	public boolean stopAquisition(){
		try{
			NIVision.IMAQdxStopAcquisition(session);
		}
		catch(Exception e){
			System.out.println(e);
			return false;
			}
		return true;
	}
	
	public void setHueRange(int min, int max){
		HUE_RANGE = new NIVision.Range(min, max);
	}
	
	public void setSatRange(int min, int max){
		SAT_RANGE = new NIVision.Range(min, max);
	}
	
	public void setValRange(int min, int max){
		VAL_RANGE = new NIVision.Range(min, max);
	}
}
