package georeduy.client.maps;


import java.util.List;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class SiteRadiusOverlay extends Overlay {

public List<GeoPoint> points;
private Paint paint1, paint2;
public List<Float> radius; //in meters

public SiteRadiusOverlay(List<GeoPoint> points, List<Float> radius) {
    this.points = points;

    paint1 = new Paint();
    paint1.setARGB(128, 187, 255, 157);
    paint1.setStrokeWidth(2);
    paint1.setStrokeCap(Paint.Cap.ROUND);
    paint1.setAntiAlias(true);
    paint1.setDither(false);
    paint1.setStyle(Paint.Style.STROKE);

    paint2 = new Paint();
    paint2.setARGB(64, 187, 255, 157);

    this.radius = radius;
}

@Override
public void draw(Canvas canvas, MapView mapView, boolean shadow) {
	int i = 0;
	for (GeoPoint point : this.points)
	{
		float siteRadius = radius.get(i);
		int lat = point.getLatitudeE6();
		int longitud = point.getLongitudeE6();
		GeoPoint correccion = new GeoPoint(lat, longitud);
	    Point pt = mapView.getProjection().toPixels(correccion, null);
	    float projectedRadius = mapView.getProjection().metersToEquatorPixels(siteRadius);
	
	    canvas.drawCircle(pt.x, pt.y, projectedRadius, paint2);
	    canvas.drawCircle(pt.x, pt.y, projectedRadius, paint1);
	    i++;
	}

}

}