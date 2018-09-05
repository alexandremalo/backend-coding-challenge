package coveo.backend.challenge.util;

import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GeodeticCurve;
import org.gavaghan.geodesy.GlobalCoordinates;

//Library used:
//https://mvnrepository.com/artifact/org.gavaghan/geodesy/1.1.3

//Theoretical Formulae
//https://en.wikipedia.org/wiki/Vincenty%27s_formulae

public class CoordinatesDistanceUtil {

    private static GeodeticCalculator geodeticCalculator = new GeodeticCalculator();
    private static Ellipsoid ellipsoid = Ellipsoid.WGS84;

    public static double getDistanceBetweenTwoCoordinates(double longitude1, double latitude1, double longitude2, double latitude2) {
        GeodeticCurve geodeticCurve = geodeticCalculator.calculateGeodeticCurve(ellipsoid, new GlobalCoordinates(latitude1, longitude1), new GlobalCoordinates(latitude2, longitude2));
        return geodeticCurve.getEllipsoidalDistance() / 1000.0;
    }
}
