package georeduy.server.util;

import georeduy.server.logic.model.MapRect;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class Util {
	public static int BROADCAST_RANGE = 480; // m
	
	public static int distanceHaversine(double lon1, double lat1, double lon2, double lat2) {

		double earthRadius = 6371; // km

		lat1 = Math.toRadians(lat1);
		lon1 = Math.toRadians(lon1);
		lat2 = Math.toRadians(lat2);
		lon2 = Math.toRadians(lon2);

		double dlon = (lon2 - lon1);
		double dlat = (lat2 - lat1);

		double sinlat = Math.sin(dlat / 2);
		double sinlon = Math.sin(dlon / 2);

		double a = (sinlat * sinlat) + Math.cos(lat1)*Math.cos(lat2)*(sinlon*sinlon);
		double c = 2 * Math.asin (Math.min(1.0, Math.sqrt(a)));

		double distanceInMeters = earthRadius * c * 1000;

		return (int)distanceInMeters;
	}
	
	public static boolean within(double longitude, double latitude, MapRect mapRect) {
		return mapRect != null && 
				longitude > mapRect.bottomLeftLongitude &&
				longitude < mapRect.topRightLongitude &&
				latitude > mapRect.bottomLeftLatitude &&
				latitude < mapRect.topRightLatitude;
	}
	
    public static BufferedImage resize(BufferedImage image, int width, int height) {
        int w = image.getWidth(), h = image.getHeight();
        int x = 0, y = 0;
        float factor;
        if (width < w || height < h)
            factor = (w > h) ? width/(float)w : height/(float)h;
        else
            factor = 1;

        int x0 = (int) (w * factor);
        int y0 = (int) (h * factor);

        BufferedImage scaledImg = new BufferedImage(x0, y0, BufferedImage.TYPE_INT_ARGB);
        
        Graphics2D g = scaledImg.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g.drawImage(image, 0, 0, x0, y0, null);
        g.dispose();
        
        return scaledImg;
    }

    public static BufferedImage byteToBufferImage(byte[] img)
    {
        BufferedImage imagen = null;
        if (img != null) {
            InputStream in = new ByteArrayInputStream(img);
            try {
                imagen = ImageIO.read(in);
            }
            catch (IOException ex) {}
        }

        return imagen;
    }
    
    public static byte[] toByte(BufferedImage image) throws IOException {
    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
    	ImageIO.write(image, "png", baos);
    	baos.flush();
    	return baos.toByteArray();
    }
}
