package cordova.plugin.equil.smart.pen;

import java.util.Date;

import com.pnf.bt.lib.PNFPenController;

import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;

public class MainDefine {
	public static PNFPenController penController = null;
	
	public static int iDisGetWidth = 0;
	public static int iDisGetHeight = 0;
	
	public static long GetCurrentSec()
	{
		Date date = new Date();
		long retVal = date.getTime();
		retVal = (retVal/1000);
		return retVal;
	}
	
	public static String getLangString(Context con ,int id){
		if(con == null) return "";
		return con.getResources().getString(id);
	}
	
	public static Point changeResolution(){
		Point rtnPoint = new Point();
		
		float calcW = 0.0f;
	    float calcH = 0.0f;
	    
	    float ww = 0;
        float hh = 0;
        RectF calibRect = null;
        
        calibRect = MainDefine.penController.getStationCalRect();
		
		ww = calibRect.width();
        hh = calibRect.height();
        
        calcW = MainDefine.iDisGetWidth;
        calcH = (int)((hh*MainDefine.iDisGetWidth)/ww);
        
        if(calcW < 100) calcW = 100;
        if(calcH < 100) calcH = 100;
        
        if (calcH > MainDefine.iDisGetHeight) {
            calcH = MainDefine.iDisGetHeight;
            calcW = (int)((ww*MainDefine.iDisGetHeight)/hh);
        }
        
    	rtnPoint.x = (int) (calcW);
		rtnPoint.y = (int) (calcH);
		
    	PointF[] calScreenPoint = new PointF[4];
		calScreenPoint[0] = new PointF(0.0f ,0.0f);
        calScreenPoint[1] = new PointF(calcW ,0.0f);
        calScreenPoint[2] = new PointF(calcW ,calcH);
        calScreenPoint[3] = new PointF(0.0f ,calcH);
        
        PointF[] calResultPoint = new PointF[4];
    	calResultPoint[0] = new PointF(calibRect.left, calibRect.top);
        calResultPoint[1] = new PointF(calibRect.right, calibRect.top);
        calResultPoint[2] = new PointF(calibRect.right ,calibRect.bottom);
        calResultPoint[3] = new PointF(calibRect.left ,calibRect.bottom);
        
        MainDefine.penController.setCalibrationData(calScreenPoint, 0, calResultPoint);
        
		return rtnPoint;
	}
}
